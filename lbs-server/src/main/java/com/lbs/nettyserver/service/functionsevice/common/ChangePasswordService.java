package com.lbs.nettyserver.service.functionsevice.common;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbs.nettyserver.dao.ChangePasswordDAO;
import com.lbs.nettyserver.dao.UserDAO;
import com.lbs.nettyserver.model.request.common.ChangePasswordRequest;
import com.lbs.nettyserver.service.impl.CommonBizService;
import com.lbs.nettyserver.utils.functionutils.ResponseBodyResultUtil;
import com.lbs.nettyserver.utils.sysutils.Md5Util;
import com.lbs.nettyserver.utils.sysutils.RSAUtil;


import net.sf.json.JSONObject;

/**
 * 修改密码方法类
 * @author visual
 *
 */
@Service("changePasswordService")
public class ChangePasswordService implements CommonBizService {
	
	private static final Log logger = LogFactory.getLog(ChangePasswordService.class);
	
	/**
	 * 修改密码类
	 */
	private ChangePasswordRequest changePasswordRequest;
	
	@Autowired
	private ChangePasswordDAO changePasswordDAO;
	
	@Autowired
	private UserDAO userDAO;

	@Override
	public boolean checkBizData(JSONObject data) {
		boolean resultflag = false;
		try {
			
			if(data != null){
				this.changePasswordRequest = (ChangePasswordRequest)JSONObject.toBean(data, ChangePasswordRequest.class);
				resultflag = true;
				
				//非空校验
				for (Field field : this.changePasswordRequest.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					if(field.get(this.changePasswordRequest)==null || "".equals(field.get(this.changePasswordRequest))){
						resultflag = false;
						break;
					}
				}	
			}
		} catch (Exception e) {
			logger.error("密码修改接口checkBizData错误："+e.getMessage());
			resultflag = false;
		}
		return resultflag;
	}

	@Override
	public JSONObject handleBiz(JSONObject data) {
		
		JSONObject result = new JSONObject();
		ResponseBodyResultUtil responseBodyResultUtil=new ResponseBodyResultUtil();
		result=responseBodyResultUtil.getParam_error_result("");
		
		if(checkBizData(data)){
			
			try{
				//查询出旧密码
				String oldPws=userDAO.getUserPwsByUserId(this.changePasswordRequest.getUserId());
				
				if(oldPws==null || "".equals(oldPws)){
					return result=responseBodyResultUtil.getParam_error_result("");
				}else{
					//密码解密RSAUtil.serverDecryptSign(changePasswordRequest.getNewPws()))
					if(oldPws.equals(Md5Util.getEncryptedPwd(this.changePasswordRequest.getOldPws()))){
						
						int updateRow=0;
						//密码解密RSAUtil.serverDecryptSign(changePasswordRequest.getNewPws()))
						updateRow=changePasswordDAO.updatePasswordByUserId(this.changePasswordRequest.getUserId(), Md5Util.getEncryptedPwd(changePasswordRequest.getNewPws()));
						if(updateRow>0){
							result=responseBodyResultUtil.getSuccess_result(null, "密码修改成功");
						}
						
					}else{//老密码输入不正确
						result=responseBodyResultUtil.getParam_error_result("旧密码不正确");
					}
				}
			}catch(Exception e){
				result=responseBodyResultUtil.getSys_error_default_result();
				e.printStackTrace();
				logger.error("密码修改接口handleBiz错误："+e.getMessage());
			}
		}
		return result;
	}

}
