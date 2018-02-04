package com.lbs.nettyserver.service.functionsevice.common;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbs.nettyserver.dao.UserDAO;
import com.lbs.nettyserver.model.request.common.CheckUserPwsIsRightRequest;
import com.lbs.nettyserver.model.response.common.CheckUserPwsIsRightResponse;
import com.lbs.nettyserver.service.impl.CommonBizService;
import com.lbs.nettyserver.utils.functionutils.ResponseBodyResultUtil;
import com.lbs.nettyserver.utils.sysutils.Md5Util;

import net.sf.json.JSONObject;

/**
 * 验证用户密码是否正确接口
 * @author visual
 *
 */
@Service("checkUserPwsIsRightService")
public class CheckUserPwsIsRightService implements CommonBizService {


	private static final Log logger = LogFactory.getLog(CheckUserPwsIsRightService.class);
	
	private CheckUserPwsIsRightRequest checkUserPwsIsRightRequest;
	
	@Autowired
	private UserDAO userDAO;

	@Override
	public boolean checkBizData(JSONObject data) {
		boolean resultflag = false;
		try {
			
			if(data != null){
				this.checkUserPwsIsRightRequest = (CheckUserPwsIsRightRequest)JSONObject.toBean(data, CheckUserPwsIsRightRequest.class);
				resultflag = true;
				
				//非空校验
				for (Field field : this.checkUserPwsIsRightRequest.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					if(field.get(this.checkUserPwsIsRightRequest)==null || "".equals(field.get(this.checkUserPwsIsRightRequest))){
						resultflag = false;
						break;
					}
				}	
			}
		} catch (Exception e) {
			logger.error("验证用户密码是否正确接口checkBizData错误："+e.getMessage());
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
				//查询出密码
				String userPws=userDAO.getUserPwsByUserId(this.checkUserPwsIsRightRequest.getUserId());
				
				if(userPws==null || "".equals(userPws)){
					return result=responseBodyResultUtil.getParam_error_result("");
				}else{
					
					CheckUserPwsIsRightResponse checkUserPwsIsRightResponse=new CheckUserPwsIsRightResponse();
					//需要解密提交上的密码
					checkUserPwsIsRightResponse.setIsRight(userPws.equals(Md5Util.getEncryptedPwd(this.checkUserPwsIsRightRequest.getPws())));
					result=responseBodyResultUtil.getSuccess_result(checkUserPwsIsRightResponse, "密码验证成功");
				}
				
			}catch(Exception e){
				result=responseBodyResultUtil.getSys_error_default_result();
				e.printStackTrace();
				logger.error("验证用户密码是否正确接口handleBiz错误："+e.getMessage());
			}
		}
		return result;
	}
	
}
