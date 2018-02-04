package com.lbs.nettyserver.service.functionsevice.common;


import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbs.nettyserver.dao.LoginUserDAO;
import com.lbs.nettyserver.dao.UserDAO;
import com.lbs.nettyserver.model.pojo.LoginUser;
import com.lbs.nettyserver.model.response.common.TLoginUserResponse;
import com.lbs.nettyserver.model.sys.UserInfoDefaultValueConstants;
import com.lbs.nettyserver.service.impl.CommonBizService;
import com.lbs.nettyserver.utils.functionutils.ResponseBodyResultUtil;
import com.lbs.nettyserver.utils.sysutils.TimeUtil;
import com.lbs.nettyserver.utils.sysutils.UUIDUtil;

import net.sf.json.JSONObject;

/**
 * 登录状态-游客和已注册用户通用
 * @author visual
 *
 */
@Service("tLoginUserService")
public class TLoginUserService implements CommonBizService {

	private static final Log logger = LogFactory.getLog(TLoginUserService.class);
	
	@Autowired
	private LoginUserDAO loginUserDao;
	@Autowired
	private UserDAO userDAO;
	
	private LoginUser loginUser;
	
	private void saveOrUpdateLoginInfo(LoginUser loginUser){
		loginUserDao.saveOrUpdateLoginUser(loginUser);
	}
	
	@Override
	public boolean checkBizData(JSONObject data) {
		boolean resultflag = false;
		try {
			if(data != null){
				this.loginUser = (LoginUser)JSONObject.toBean(data, LoginUser.class);
				resultflag = true;
				
				//非空校验
				for (Field field : this.loginUser.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					if(field.getName()=="loginId" || field.getName()=="userId" || field.getName()=="token" || field.getName()=="free0" || field.getName()=="free1" || field.getName()=="lastTime" || field.getName()=="isRegist")continue;//这些字段可为空
					if(field.get(this.loginUser)==null || "".equals(field.get(this.loginUser))){
						resultflag = false;
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户状态更新接口checkBizData错误："+e.getMessage());
			resultflag = false;
		}
		return resultflag;
	}

	@Override
	public JSONObject handleBiz(JSONObject data) {
        
		JSONObject result = new JSONObject();
		ResponseBodyResultUtil responseBodyResultUtil=new ResponseBodyResultUtil();
		result=responseBodyResultUtil.getParam_error_result("");
		
		LoginUser newLoginUser=new LoginUser();
		
		if(checkBizData(data)){
			
			//游客(第一次使用)或者退出登录过-loginId和userId为空
			if((this.loginUser.getLoginId()==null || "".equals(this.loginUser.getLoginId())) || (this.loginUser.getUserId()==null || "".equals(this.loginUser.getUserId()))){
				
				//token（手机唯一标识码）不为空，根据token（手机唯一标识码）查找该设备是否有非注册用户登录过
				if(this.loginUser.getToken()!=null && !"".equals(this.loginUser.getToken())){
					
					newLoginUser=loginUserDao.getIsNoRegistLoginUserByToken(this.loginUser.getToken());
				
					if(newLoginUser!=null){//该设备非注册用户登录过,保持原来的loginId和userId
						this.loginUser.setLoginId(newLoginUser.getLoginId());
						this.loginUser.setUserId(newLoginUser.getUserId());
						this.loginUser.setIsRegist(UserInfoDefaultValueConstants.USER_IS_NO_REGIST);//0未注册
				
					}else{//该设备非注册用户没有登录过,生成新的loginid和userId
						this.loginUser.setLoginId(UUIDUtil.getUUID());
						this.loginUser.setUserId(UUIDUtil.getUUID());
						this.loginUser.setIsRegist(UserInfoDefaultValueConstants.USER_IS_NO_REGIST);//0未注册
					}
				}else{//token（手机唯一标识码）为空,生成新的loginid和userId
					this.loginUser.setLoginId(UUIDUtil.getUUID());
					this.loginUser.setUserId(UUIDUtil.getUUID());
					this.loginUser.setIsRegist(UserInfoDefaultValueConstants.USER_IS_NO_REGIST);//0未注册
				}
			
			}else{//之前用过APP
				if(userDAO.getUserByUserId(this.loginUser.getUserId())==null){//不是注册用户
					this.loginUser.setIsRegist(UserInfoDefaultValueConstants.USER_IS_NO_REGIST);//0未注册
				}else{
					this.loginUser.setIsRegist(UserInfoDefaultValueConstants.USER_IS_REGIST);//1已注册
				}
			}
			
		    this.loginUser.setLastTime(TimeUtil.getChinaLocalDateTimeNow());//更新登录时间
			saveOrUpdateLoginInfo(this.loginUser);
			
			TLoginUserResponse tLoginUserResponse=new TLoginUserResponse();
			tLoginUserResponse.setUserId(this.loginUser.getUserId());
			tLoginUserResponse.setLoginId(this.loginUser.getLoginId());
			tLoginUserResponse.setLastTime(TimeUtil.dateTimeFormatToString(this.loginUser.getLastTime()));
			tLoginUserResponse.setIsRegist(this.loginUser.getIsRegist());
			
			result=responseBodyResultUtil.getSuccess_result(tLoginUserResponse, "登录状态更新成功");
		}
		return result;
	}

}
