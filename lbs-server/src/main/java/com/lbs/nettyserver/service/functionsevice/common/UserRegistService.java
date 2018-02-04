package com.lbs.nettyserver.service.functionsevice.common;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbs.nettyserver.dao.LoginUserDAO;
import com.lbs.nettyserver.dao.PowerValueDAO;
import com.lbs.nettyserver.dao.UserDAO;
import com.lbs.nettyserver.model.pojo.LoginUser;
import com.lbs.nettyserver.model.pojo.PowerValue;
import com.lbs.nettyserver.model.pojo.User;
import com.lbs.nettyserver.model.request.common.UserRegistRequest;
import com.lbs.nettyserver.model.response.common.UserRegistResponse;
import com.lbs.nettyserver.model.sys.UserInfoDefaultValueConstants;
import com.lbs.nettyserver.service.impl.CommonBizService;
import com.lbs.nettyserver.utils.functionutils.NickNameUtil;
import com.lbs.nettyserver.utils.functionutils.ResponseBodyResultUtil;
import com.lbs.nettyserver.utils.sysutils.Md5Util;
import com.lbs.nettyserver.utils.sysutils.RSAUtil;
import com.lbs.nettyserver.utils.sysutils.RedisUtil;
import com.lbs.nettyserver.utils.sysutils.TimeUtil;
import com.lbs.nettyserver.utils.sysutils.UUIDUtil;

import net.sf.json.JSONObject;

/**
 * 用户注册接口方法类
 * @author visual
 *
 */
@Service("userRegistService")
public class UserRegistService implements CommonBizService {
	
	private static final Log logger = LogFactory.getLog(UserRegistService.class);
	
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private UserDAO userDao;
	@Autowired
	private LoginUserDAO loginUserDAO;
	@Autowired
	private PowerValueDAO powerValueDAO;
	
	private UserRegistRequest userRegistRequest;
	
	private UserRegistResponse userRegistResponse;
	
	
	private User user;
	
	/**
	 * 检查验证码是否正确
	 * @param phone
	 * @param checkNum
	 * @return
	 */
	private boolean checkNumIsRight(String phone,String checkNum){
		Object num= redisUtil.get(phone);
		if(num==null || "".equals(num)){
			return false;
		}
		return num.toString().equals(checkNum);
	}
	
	/**
	 * 根据电话号码查询是否已被注册
	 */
	private boolean checkPhoneIsRegisted(String phone){

		boolean result=false;
		User user=userDao.getUserByPhone(phone);
		if(user==null){
			result=true;
		}else{
			result=false;
		}
		return result;
	}
	
	/**
	 * 根据注册信息生成user实体类
	 * @throws Exception 
	 */
	private void setUserValueForUserRegist(UserRegistRequest userRegistRequest){
		
		this.user=new User();
		
		String uid=userRegistRequest.getUserId();
		user.setUserId((uid==null || "".equals(uid))?UUIDUtil.getUUID():uid);//生成用户ID
		
		user.setLv(UserInfoDefaultValueConstants.VOMIT_LV_DEFAULT_VALUE);//设置用户初始等级
		
		user.setSex(userRegistRequest.getSex());
		user.setPhone(userRegistRequest.getPhone());
		
		//Md5Util.getEncryptedPwd(RSAUtil.serverDecryptSign(userRegistRequest.getPassword()))
		user.setPassword(Md5Util.getEncryptedPwd(userRegistRequest.getPassword()));//密码,MD5加密入库
		
		user.setNickName(NickNameUtil.generateNickName());//昵称
		
		user.setLiveScore(UserInfoDefaultValueConstants.LIVE_SCORE_DEFAULT_VALUE);//设置现场得分
		
		user.setBackImg(UserInfoDefaultValueConstants.USER_BACKIMG_DEFAULT_VALUE);//设置默认个人中心背景图片路径

		//设置默认头像
		if(userRegistRequest.getHeadImg()==null || "".equals(userRegistRequest.getHeadImg())){
			user.setHeadImg(UserInfoDefaultValueConstants.USER_HEADIMG_DEFAULT_VALUE);
		}
	}
	
	/**
	 * 校验客户端请求数据是否与实体类一致
	 */
	@Override
	public boolean checkBizData(JSONObject data) {
		boolean resultflag = false;
		try {
			if(data != null){
				
				this.userRegistRequest = (UserRegistRequest)JSONObject.toBean(data, UserRegistRequest.class);
				resultflag = true;
				
				//非空校验
				for (Field field : this.userRegistRequest.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					if(field.getName()=="headImg" || field.getName()=="userId")continue;//可以为空
					if(field.get(this.userRegistRequest)==null || "".equals(field.get(this.userRegistRequest))){
						resultflag = false;
						break;
					}
				}	
			}
		} catch (Exception e) {
			logger.error("用户注册接口checkBizData错误："+e.getMessage());
			resultflag = false;
		}
		return resultflag;
	}

	/**
	 * 用户注册调用方法，保存信息并返回json信息
	 */
	@Override
	public JSONObject handleBiz(JSONObject data) {
		
       JSONObject result = new JSONObject();
       ResponseBodyResultUtil responseBodyResultUtil=new ResponseBodyResultUtil();
	   result=responseBodyResultUtil.getParam_error_result("");
		
		if(checkBizData(data)){
			
			if(!checkNumIsRight(userRegistRequest.getPhone(),userRegistRequest.getCheckNum())){
				return result=responseBodyResultUtil.getParam_error_result("验证码错误");
			}
			
			if(!checkPhoneIsRegisted(userRegistRequest.getPhone())){
				return result=responseBodyResultUtil.getParam_error_result("电话号码已注册");
			}
			
			LoginUser loginUser=null;
			//检查该用户之前是否登录过
			if(this.userRegistRequest.getUserId()!=null && !"".equals(this.userRegistRequest.getUserId())){
				loginUser=loginUserDAO.getLoginUserByUserId(this.userRegistRequest.getUserId());
				if(loginUser!=null){//之前登录过
					this.userRegistRequest.setUserId(loginUser.getUserId());
				}
			}
		
			setUserValueForUserRegist(userRegistRequest);//设置用户user表的数据
			userDao.saveUser(user);//保存用户注册信息
			
			//保存用户体力能量信息
			PowerValue powerValue=new PowerValue();
			powerValue.setTpvId(UUIDUtil.getUUID());
			powerValue.setUserId(user.getUserId());
			powerValue.setTlValue(BigDecimal.valueOf(100));
			powerValue.setTlValue(BigDecimal.valueOf(100));
			powerValue.setUpdateTime(TimeUtil.getChinaLocalDateTimeNow());
			powerValueDAO.savePowerValue(powerValue);
			
			if(loginUser!=null){
				loginUser.setIsRegist(UserInfoDefaultValueConstants.USER_IS_REGIST);//设置注册状态为已注册
				loginUserDAO.saveOrUpdateLoginUser(loginUser);//更新用户登录表状态
			}
			
			userRegistResponse=new UserRegistResponse(user);
			result=responseBodyResultUtil.getSuccess_result(userRegistResponse, "注册成功");
		}
		return result;
	}

}
