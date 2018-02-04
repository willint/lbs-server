package com.lbs.nettyserver.service.functionsevice.my;

import java.lang.reflect.Field;
import java.math.BigInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbs.nettyserver.dao.my.MyOptionDAO;
import com.lbs.nettyserver.model.request.my.MyGetSmartMessageTodayTotalRequest;
import com.lbs.nettyserver.model.response.my.MyGetSmartMessageTodayTotalResponse;
import com.lbs.nettyserver.service.impl.CommonBizService;
import com.lbs.nettyserver.utils.functionutils.ResponseBodyResultUtil;

import net.sf.json.JSONObject;

/**
 * 我的-获取用户今天发布的智囊消息接口
 * @author visual
 *
 */
@Service("myGetSmartMessageTodayTotalService")
public class MyGetSmartMessageTodayTotalService implements CommonBizService {


	private static final Log logger = LogFactory.getLog(MyGetSmartMessageTodayTotalService.class);
	
	@Autowired
	private MyOptionDAO myOptionDAO;
	
	private MyGetSmartMessageTodayTotalRequest myGetSmartMessageTodayTotalRequest;
	
	private MyGetSmartMessageTodayTotalResponse myGetSmartMessageTodayTotalResponse;

	@Override
	public boolean checkBizData(JSONObject data) {
		boolean resultflag = false;
		try {
			
			if(data != null){
				this.myGetSmartMessageTodayTotalRequest = (MyGetSmartMessageTodayTotalRequest)JSONObject.toBean(data, MyGetSmartMessageTodayTotalRequest.class);
				resultflag = true;
				
				//非空校验
				for (Field field : this.myGetSmartMessageTodayTotalRequest.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					if(field.get(this.myGetSmartMessageTodayTotalRequest)==null || "".equals(field.get(this.myGetSmartMessageTodayTotalRequest))){
						resultflag = false;
						break;
					}
				}	
			}
		} catch (Exception e) {
			logger.error("我的-获取用户今天发布的智囊消息接口checkBizData错误："+e.getMessage());
			resultflag = false;
		}
		return resultflag;
	}

	@Override
	public JSONObject handleBiz(JSONObject data) {
		JSONObject result = new JSONObject();
		ResponseBodyResultUtil responseBodyResultUtil=new ResponseBodyResultUtil();
		result=responseBodyResultUtil.getParam_error_result("");
		this.myGetSmartMessageTodayTotalResponse=new MyGetSmartMessageTodayTotalResponse();
		if(checkBizData(data)){
			try{
				this.myGetSmartMessageTodayTotalResponse.setSmartMessageTodayTotal((BigInteger)myOptionDAO.getSmartMessageTodayTotalByUserId(this.myGetSmartMessageTodayTotalRequest.getUserId()));	
				result=responseBodyResultUtil.getSuccess_result(this.myGetSmartMessageTodayTotalResponse, "获取成功");
			}catch(Exception e){
				result=responseBodyResultUtil.getSys_error_default_result();
				e.printStackTrace();
				logger.error("我的-获取用户今天发布的智囊消息接口handleBiz错误："+e.getMessage());
			}
		}
		return result;
	}
}
