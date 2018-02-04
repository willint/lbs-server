package com.lbs.nettyserver.service.functionsevice.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbs.nettyserver.model.request.common.VerificationCodeRequest;
import com.lbs.nettyserver.model.sys.VerificationCodeConstants;
import com.lbs.nettyserver.service.impl.CommonBizService;
import com.lbs.nettyserver.utils.functionutils.ResponseBodyResultUtil;
import com.lbs.nettyserver.utils.sysutils.RedisUtil;
import com.lbs.nettyserver.utils.sysutils.SmsVerificationCodeUtil;

import net.sf.json.JSONObject;

/**
 * 发送验证码
 * @author visual
 *
 */
@Service("verificationCodeService")
public class VerificationCodeService implements CommonBizService {
	
	@Autowired
	private RedisUtil redisUtil;

	private VerificationCodeRequest verificationCodeRequest;
	
	@Override
	public boolean checkBizData(JSONObject data) {
		boolean resultflag = false;
		try {
			if(data != null){
				
				this.verificationCodeRequest = (VerificationCodeRequest)JSONObject.toBean(data, VerificationCodeRequest.class);
				resultflag = true;
				
				//非空校验
				if(verificationCodeRequest.getPhone()==null || "".equals(verificationCodeRequest.getPhone())){
					resultflag=false;
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
        	String phone=this.verificationCodeRequest.getPhone();
        	
        	String verificationCode= SmsVerificationCodeUtil.getRandNum(VerificationCodeConstants.checkNum_length);
    		if(!redisUtil.set(phone, verificationCode,VerificationCodeConstants.checkNum_effective_time)){
    			return result=responseBodyResultUtil.getParam_error_result("验证码发送失败");
    		}
    		
    		if(SmsVerificationCodeUtil.sendVerificationCode(phone, verificationCode)){
    			result=responseBodyResultUtil.getSuccess_result(null, "验证码发送成功");
    		}else{
    			result=responseBodyResultUtil.getParam_error_result("验证码发送失败");
    		}
		}
		return result;

	}

	
}
