package com.lbs.nettyserver.service.functionsevice.common;

import java.lang.reflect.Field;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbs.nettyserver.model.request.common.CheckVerificationCodeIsRightRequest;
import com.lbs.nettyserver.model.response.common.CheckVerificationCodeIsRightResponse;
import com.lbs.nettyserver.service.impl.CommonBizService;
import com.lbs.nettyserver.utils.functionutils.ResponseBodyResultUtil;
import com.lbs.nettyserver.utils.sysutils.RedisUtil;

import net.sf.json.JSONObject;

/**
 * 检查验证码是否正确类
 * @author visual
 *
 */
@Service("checkVerificationCodeIsRightService")
public class CheckVerificationCodeIsRightService implements CommonBizService {
	
	@Autowired
	private RedisUtil redisUtil;
	
	private CheckVerificationCodeIsRightRequest checkVerificationCodeIsRightRequest;
	
	private CheckVerificationCodeIsRightResponse checkVerificationCodeIsRightResponse;
	
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

	@Override
	public boolean checkBizData(JSONObject data) {
		boolean resultflag = false;
		try {
			if(data != null){
				
				this.checkVerificationCodeIsRightRequest = (CheckVerificationCodeIsRightRequest)JSONObject.toBean(data, CheckVerificationCodeIsRightRequest.class);
				resultflag = true;
				
				//非空校验
				for (Field field : this.checkVerificationCodeIsRightRequest.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					if(field.get(this.checkVerificationCodeIsRightRequest)==null || "".equals(field.get(this.checkVerificationCodeIsRightRequest))){
						resultflag = false;
						break;
					}
				}	
			}
		} catch (Exception e) {
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
				this.checkVerificationCodeIsRightResponse=new CheckVerificationCodeIsRightResponse();
				this.checkVerificationCodeIsRightResponse.setRight(checkNumIsRight(this.checkVerificationCodeIsRightRequest.getPhone(),this.checkVerificationCodeIsRightRequest.getCheckNum()));
				result=responseBodyResultUtil.getSuccess_result(this.checkVerificationCodeIsRightResponse, "验证码检测成功");
			}
			return result;
	}

}
