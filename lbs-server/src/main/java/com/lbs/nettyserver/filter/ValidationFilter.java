package com.lbs.nettyserver.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import io.netty.util.internal.StringUtil;

import com.lbs.nettyserver.factory.AbstractManagerFacotry;
import com.lbs.nettyserver.factory.ManagerFactoryImpl;
import com.lbs.nettyserver.factory.NormalFactory;
import com.lbs.nettyserver.factory.SessionFactoryImpl;
import com.lbs.nettyserver.model.common.ReqHead;
import com.lbs.nettyserver.model.common.RespHead;
import com.lbs.nettyserver.model.pojo.SystemCaseMethod;
import com.lbs.nettyserver.model.sys.CodeAndMsgConstants;
import com.lbs.nettyserver.model.sys.MethodCodeConstants;
import com.lbs.nettyserver.model.sys.SysCharConstants;
import com.lbs.nettyserver.session.Manager;
import com.lbs.nettyserver.session.ManagerBase;
import com.lbs.nettyserver.session.Session;
import com.lbs.nettyserver.session.StandardSession;
import com.lbs.nettyserver.utils.sysutils.Md5Util;
import com.lbs.nettyserver.utils.sysutils.RSAUtil;
import com.lbs.nettyserver.utils.sysutils.SpringContextUtil;

public class ValidationFilter {
	
	private static final Log logger = LogFactory.getLog(ValidationFilter.class);
	
	public static  List<SystemCaseMethod> methodList = new ArrayList<SystemCaseMethod>(); 
	
	public static boolean checkSign(ReqHead head){
		String source = head.getTransactionId();
		String sign = head.getSign();
		boolean resultFlag = false;
		if(null != sign){
			try {
				// 先排除例外请求接口
				if(checkCaseMethod(head)){
					return true;
				}
				// RSA解密
				String decycoteString = RSAUtil.serverDecryptSign(sign);
				logger.info(decycoteString);
				if(source.equals(decycoteString)){
					resultFlag = true;
				}else{
					resultFlag = sign.equals(Md5Util.getEncryptedPwd(source));
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
		}
		return resultFlag;
	}
	
	
	public static boolean checkSessionCode(ReqHead head ){
		
		boolean resultFlag = false;
		String sessionCode = head.getSessionCode();
		if(null != sessionCode){
			// 排除例外接口
			if(checkCaseMethod(head)){
				return true;
			}
			
			try {
				ManagerFactoryImpl factory =(ManagerFactoryImpl) SpringContextUtil.getBean("managerFactory");
				//判断session是否存在，不存在直接返回失效
				List<Manager> managers = factory.getContainer();
				Session session = null;
				for(Manager m : managers){
					session = m.findSession(sessionCode);
					if(null != session){
						break;
					}
				}
				if(null != session){
					//判断session是否失效，失效直接返回
					if(session.isValid()){
						resultFlag = true;
						//更新session时间最后访问时间，返回有效
						session.access();
					}else{
						resultFlag = false;
					}
				}
			} catch (Exception e) {
				resultFlag = false;
				e.printStackTrace();
				logger.error(e);
			}
		}
		return resultFlag;
	}
	
	/**
	 * 业务处理成功的后续处理
	 * @param head
	 * @param body
	 * @param response
	 */
	public static void tailHandle(ReqHead head,JSONObject body ,RespHead response){
		
		//登录接口 成功后需要设置sessionCode
		if((MethodCodeConstants.LOGINER.equals(head.getMethod())
		||MethodCodeConstants.LOGININFO_UPDATE.equals(head.getMethod()))
				&&body.getString(SysCharConstants.codeChar).equals(CodeAndMsgConstants.success_code)){
			setSessionCode(response);
		}else if(MethodCodeConstants.outLogin.equals(head.getMethod())){
			//退出登录删除session信息
			clearSessionCode(head);
		}else{
			response.setSessionCode(head.getSessionCode());

		}
	}
	
	/**
	 * 为登录成功的用户设置sessionCode
	 * @param head
	 * @param body
	 * @param response
	 */
	private static void setSessionCode(RespHead response){
		
		Manager manager = null;
		ManagerFactoryImpl factory =(ManagerFactoryImpl) SpringContextUtil.getBean("managerFactory");
		List<Manager> managers = factory.getContainer();
		// 每个manager存1000session
		for(Manager m: managers){
			if(m.getActiveSessions() < Manager.MANAGER_SIZE ){
				manager = m ;
			}
		}
		if(null == manager){
			manager = factory.createManager(ManagerBase.class);
			factory.getContainer().add(manager);
		}
		
		Session session = manager.createSession();
		session.setManager(manager);
		manager.add(session);
		
		//设置返回的sessionCode
		response.setSessionCode(session.getId());
	}
	
	public static boolean checkReqireParams(ReqHead head){
		boolean resultFlag = false;
		
		if(StringUtil.isNullOrEmpty(head.getAppKey())
				||StringUtil.isNullOrEmpty(head.getAttach())
				||StringUtil.isNullOrEmpty(head.getMethod())
				||StringUtil.isNullOrEmpty(head.getReqTime())
				||StringUtil.isNullOrEmpty(head.getSessionCode())
				||StringUtil.isNullOrEmpty(head.getSign())
				||StringUtil.isNullOrEmpty(head.getTransactionId())
				||StringUtil.isNullOrEmpty(head.getVersion())
				){
			
		}else{
			resultFlag = true;
		}
		
		return resultFlag;
	}
	
	/**
	 * 有些接口需要例外情况
	 * @param head
	 * @return
	 */
	private static boolean checkCaseMethod(ReqHead head){
		boolean resultFlag = false;
		
		if(null != methodList&& methodList.size()>0){
			for(SystemCaseMethod scm:methodList ){
				if(scm.getMethodCode().equals(head.getMethod())){
					resultFlag = true;
				}
			}
		}
		return resultFlag;
	}

	/**
	 * 用户退出登录删除session信息
	 * @param head
	 */
	private static void clearSessionCode(ReqHead head){
		Manager manager = null;
		ManagerFactoryImpl factory =(ManagerFactoryImpl) SpringContextUtil.getBean("managerFactory");
		List<Manager> managers = factory.getContainer();
		// 每个manager存1000session
		try{
			for(Manager m: managers){
				Session session = (Session)m.findSession(head.getSessionCode());
				if(null != session){
					m.remove(session);
					break;
				}
			}
		}catch (IOException e){
			logger.error(e.getMessage());

		}


	}

}
