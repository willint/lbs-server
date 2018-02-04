package com.lbs.nettyserver.service.functionsevice.common;


import java.lang.reflect.Field;
import java.math.BigDecimal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbs.nettyserver.dao.LoginUserDAO;
import com.lbs.nettyserver.dao.UserDAO;
import com.lbs.nettyserver.model.pojo.LoginUser;
import com.lbs.nettyserver.model.pojo.User;
import com.lbs.nettyserver.model.request.common.UserLoginRequest;
import com.lbs.nettyserver.model.response.common.UserLoginResponse;
import com.lbs.nettyserver.model.sys.UserInfoDefaultValueConstants;
import com.lbs.nettyserver.service.impl.CommonBizService;
import com.lbs.nettyserver.utils.functionutils.ResponseBodyResultUtil;
import com.lbs.nettyserver.utils.sysutils.Md5Util;
import com.lbs.nettyserver.utils.sysutils.RSAUtil;
import com.lbs.nettyserver.utils.sysutils.TimeUtil;
import com.lbs.nettyserver.utils.sysutils.UUIDUtil;

import net.sf.json.JSONObject;

/**
 * 用户登录方法类
 * @author visual
 *
 */
@Service("userLoginService")
public class UserLoginService implements CommonBizService {
	
	private static final Log logger = LogFactory.getLog(UserLoginService.class);
	
	@Autowired
	private UserDAO userDao;
	
	@Autowired
	private LoginUserDAO loginUserDao;
	
	private User user;
	
	/**
	 * 用户登录请求类
	 */
	private UserLoginRequest userLoginRequest;
	/**
	 * 用户登录结果响应类
	 */
	private UserLoginResponse userLoginResponse;
	
	/**
	 * 根据电话号码和密码查询用户信息
	 * @param phone
	 * @param password
	 * @return boolean
	 */
	private boolean checkLogin(String phone,String password){
		
		boolean result=true;
		user=userDao.getUserByPhoneAndPassword(phone, Md5Util.getEncryptedPwd(password));
		
		if(user==null){
			result=false;
		}
		return result;
	}
	
	/**
	 * 请求数据校验
	 */
	@Override
	public boolean checkBizData(JSONObject data) {
		boolean resultflag = false;
		try {
			
			if(data != null){
				this.userLoginRequest = (UserLoginRequest)JSONObject.toBean(data, UserLoginRequest.class);
				resultflag = true;
				
				//非空校验
				for (Field field : this.userLoginRequest.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					if(field.get(this.userLoginRequest)==null || "".equals(field.get(this.userLoginRequest))){
						resultflag = false;
						break;
					}
				}	
			}
		} catch (Exception e) {
			logger.error("用户登录接口checkBizData错误："+e.getMessage());
			resultflag = false;
		}
		return resultflag;
	}

	/**
	 * 客户端调用登录方法
	 */
	@Override
	public JSONObject handleBiz(JSONObject data) {
       
		JSONObject result = new JSONObject();
		ResponseBodyResultUtil responseBodyResultUtil=new ResponseBodyResultUtil();
		result=responseBodyResultUtil.getParam_error_result("");
		if(checkBizData(data)){
			//密码解密checkLogin(userLoginRequest.getPhone(),RSAUtil.serverDecryptSign(userLoginRequest.getPassword()))
			if(checkLogin(userLoginRequest.getPhone(),userLoginRequest.getPassword())){
				
				userLoginResponse =new UserLoginResponse();
				
				//查询是否已经存在登录记录
				LoginUser loginUser=loginUserDao.getLoginUserByUserId(user.getUserId());
				
				LoginUser newloginUser=new LoginUser();
				
				if(loginUser !=null){
					//更新登录状态信息
					loginUser.setIsOnline(true);
					loginUser.setLastTime(TimeUtil.getChinaLocalDateTimeNow());
					loginUser.setIsRegist(UserInfoDefaultValueConstants.USER_IS_REGIST);
					loginUserDao.saveOrUpdateLoginUser(loginUser);
				}else{
					//新建登录信息
					newloginUser.setLoginId(UUIDUtil.getUUID());
					newloginUser.setUserId(user.getUserId());
					newloginUser.setIp("127.0.0.1");
					newloginUser.setPort(8188);
					newloginUser.setIsOnline(true);
					newloginUser.setJd(BigDecimal.ZERO);
					newloginUser.setWd(BigDecimal.ZERO);
					newloginUser.setVomitRange(BigDecimal.ZERO);
					newloginUser.setLiveRange(BigDecimal.ZERO);
					newloginUser.setLastTime(TimeUtil.getChinaLocalDateTimeNow());
					newloginUser.setIsRegist(UserInfoDefaultValueConstants.USER_IS_REGIST);
					loginUserDao.saveOrUpdateLoginUser(newloginUser);
				}
				
				userLoginResponse.setLoginId(loginUser==null?newloginUser.getLoginId():loginUser.getLoginId());
				userLoginResponse.setUserId(user.getUserId());
				
				result=responseBodyResultUtil.getSuccess_result(userLoginResponse, "登录成功");
				
			}else{
				
				result=responseBodyResultUtil.getParam_error_result("用户名或密码错误");
			}
		}
		return result;
	}

}
