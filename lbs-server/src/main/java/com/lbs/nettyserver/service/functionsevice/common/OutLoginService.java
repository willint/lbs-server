package com.lbs.nettyserver.service.functionsevice.common;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbs.nettyserver.dao.LoginUserDAO;
import com.lbs.nettyserver.model.request.common.OutLoginRequest;
import com.lbs.nettyserver.service.impl.CommonBizService;
import com.lbs.nettyserver.utils.functionutils.ResponseBodyResultUtil;

import net.sf.json.JSONObject;

/**
 * 用户退出登录
 * @author visual
 *
 */
@Service("outLoginService")
public class OutLoginService implements CommonBizService {


	private static final Log logger = LogFactory.getLog(OutLoginService.class);
	
	private OutLoginRequest outLoginRequest;
	
	@Autowired
	private LoginUserDAO loginUserDAO;

	@Override
	public boolean checkBizData(JSONObject data) {
		boolean resultflag = false;
		try {
			
			if(data != null){
				this.outLoginRequest = (OutLoginRequest)JSONObject.toBean(data, OutLoginRequest.class);
				resultflag = true;
				
				//非空校验
				for (Field field : this.outLoginRequest.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					if(field.get(this.outLoginRequest)==null || "".equals(field.get(this.outLoginRequest))){
						resultflag = false;
						break;
					}
				}	
			}
		} catch (Exception e) {
			logger.error("用户退出登录接口checkBizData错误："+e.getMessage());
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
			int updateRow=0;
			try{
				updateRow=loginUserDAO.updateLoginUserStateByLoginId(this.outLoginRequest.getLoginId(),this.outLoginRequest.getUserId());
				if(updateRow>0){
					result=responseBodyResultUtil.getSuccess_result(null, "退出登录成功");
				}
			}catch(Exception e){
				result=responseBodyResultUtil.getSys_error_default_result();
				e.printStackTrace();
				logger.error("用户退出登录接口handleBiz错误："+e.getMessage());
			}
		}
		return result;
	}
	
}
