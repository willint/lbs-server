package com.lbs.nettyserver.service.functionsevice.my;

import java.lang.reflect.Field;
import java.math.BigInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbs.nettyserver.dao.my.MyOptionDAO;
import com.lbs.nettyserver.model.request.my.MyGetJoinSpecialTodayTotalRequest;
import com.lbs.nettyserver.model.response.my.MyGetJoinSpecialTodayTotalResponse;
import com.lbs.nettyserver.service.impl.CommonBizService;
import com.lbs.nettyserver.utils.functionutils.ResponseBodyResultUtil;

import net.sf.json.JSONObject;

/**
 * 我的-获取用户今天加入的专题的总数接口
 * @author visual
 *
 */
@Service("myGetJoinSpecialTodayTotalService")
public class MyGetJoinSpecialTodayTotalService implements CommonBizService {


	private static final Log logger = LogFactory.getLog(MyGetJoinSpecialTodayTotalService.class);
	
	@Autowired
	private MyOptionDAO myOptionDAO;
	
	private MyGetJoinSpecialTodayTotalRequest myGetJoinSpecialTodayTotalRequest;
	
	private MyGetJoinSpecialTodayTotalResponse myGetJoinSpecialTodayTotalResponse;

	@Override
	public boolean checkBizData(JSONObject data) {
		boolean resultflag = false;
		try {
			
			if(data != null){
				this.myGetJoinSpecialTodayTotalRequest = (MyGetJoinSpecialTodayTotalRequest)JSONObject.toBean(data, MyGetJoinSpecialTodayTotalRequest.class);
				resultflag = true;
				
				//非空校验
				for (Field field : this.myGetJoinSpecialTodayTotalRequest.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					if(field.get(this.myGetJoinSpecialTodayTotalRequest)==null || "".equals(field.get(this.myGetJoinSpecialTodayTotalRequest))){
						resultflag = false;
						break;
					}
				}	
			}
		} catch (Exception e) {
			logger.error("我的-获取用户今天加入的专题的总数接口checkBizData错误："+e.getMessage());
			resultflag = false;
		}
		return resultflag;
	}

	@Override
	public JSONObject handleBiz(JSONObject data) {
		JSONObject result = new JSONObject();
		ResponseBodyResultUtil responseBodyResultUtil=new ResponseBodyResultUtil();
		result=responseBodyResultUtil.getParam_error_result("");
		this.myGetJoinSpecialTodayTotalResponse=new MyGetJoinSpecialTodayTotalResponse();
		if(checkBizData(data)){
			try{
				this.myGetJoinSpecialTodayTotalResponse.setJoinSpecialTodayTotal((BigInteger)myOptionDAO.getJoinSpecialTodayTotalByUserId(this.myGetJoinSpecialTodayTotalRequest.getUserId()));	
				result=responseBodyResultUtil.getSuccess_result(this.myGetJoinSpecialTodayTotalResponse, "获取成功");
			}catch(Exception e){
				result=responseBodyResultUtil.getSys_error_default_result();
				e.printStackTrace();
				logger.error("我的-获取用户今天加入的专题的总数接口handleBiz错误："+e.getMessage());
			}
		}
		return result;
	}
}
