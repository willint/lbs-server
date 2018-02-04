package com.lbs.nettyserver.service.functionsevice.my;

import java.lang.reflect.Field;
import java.math.BigInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbs.nettyserver.dao.my.MyOptionDAO;
import com.lbs.nettyserver.model.request.my.MyGetPutZanTodayTotalRequest;
import com.lbs.nettyserver.model.response.my.MyGetPutZanTodayTotalResponse;
import com.lbs.nettyserver.service.impl.CommonBizService;
import com.lbs.nettyserver.utils.functionutils.ResponseBodyResultUtil;

import net.sf.json.JSONObject;

/**
 * 我的-获取用户今天点出的赞的接口
 * @author visual
 *
 */
@Service("myGetPutZanTodayTotalService")
public class MyGetPutZanTodayTotalService implements CommonBizService {


	private static final Log logger = LogFactory.getLog(MyGetPutZanTodayTotalService.class);
	
	@Autowired
	private MyOptionDAO myOptionDAO;
	
	private  MyGetPutZanTodayTotalRequest myGetPutZanTodayTotalRequest;
	
	private  MyGetPutZanTodayTotalResponse myGetPutZanTodayTotalResponse;

	@Override
	public boolean checkBizData(JSONObject data) {
		boolean resultflag = false;
		try {
			
			if(data != null){
				this.myGetPutZanTodayTotalRequest = (MyGetPutZanTodayTotalRequest)JSONObject.toBean(data, MyGetPutZanTodayTotalRequest.class);
				resultflag = true;
				
				//非空校验
				for (Field field : this.myGetPutZanTodayTotalRequest.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					if(field.get(this.myGetPutZanTodayTotalRequest)==null || "".equals(field.get(this.myGetPutZanTodayTotalRequest))){
						resultflag = false;
						break;
					}
				}	
			}
		} catch (Exception e) {
			logger.error("我的-获取用户今天点出的赞的接口checkBizData错误："+e.getMessage());
			resultflag = false;
		}
		return resultflag;
	}

	@Override
	public JSONObject handleBiz(JSONObject data) {
		JSONObject result = new JSONObject();
		ResponseBodyResultUtil responseBodyResultUtil=new ResponseBodyResultUtil();
		result=responseBodyResultUtil.getParam_error_result("");
		this.myGetPutZanTodayTotalResponse=new MyGetPutZanTodayTotalResponse();
		if(checkBizData(data)){
			try{
				this.myGetPutZanTodayTotalResponse.setPutZanTodayTotal((BigInteger)myOptionDAO.getPutZanTodayTotalByUserId(this.myGetPutZanTodayTotalRequest.getUserId()));	
				result=responseBodyResultUtil.getSuccess_result(this.myGetPutZanTodayTotalResponse, "获取成功");
			}catch(Exception e){
				result=responseBodyResultUtil.getSys_error_default_result();
				e.printStackTrace();
				logger.error("我的-获取用户今天点出的赞的接口handleBiz错误："+e.getMessage());
			}
		}
		return result;
	}
}
