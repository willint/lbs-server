package com.lbs.nettyserver.service.functionsevice.my;

import java.lang.reflect.Field;
import java.math.BigInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbs.nettyserver.dao.my.MyOptionDAO;
import com.lbs.nettyserver.model.request.my.MyGetGetCaiTodayTotalRequest;
import com.lbs.nettyserver.model.response.my.MyGetGetCaiTodayTotalResponse;
import com.lbs.nettyserver.service.impl.CommonBizService;
import com.lbs.nettyserver.utils.functionutils.ResponseBodyResultUtil;

import net.sf.json.JSONObject;

/**
 * 我的-获取用户今天得到的踩的接口
 * @author visual
 *
 */
@Service("myGetGetCaiTodayTotalService")
public class MyGetGetCaiTodayTotalService implements CommonBizService {
	

	private static final Log logger = LogFactory.getLog(MyGetGetCaiTodayTotalService.class);
	
	@Autowired
	private MyOptionDAO myOptionDAO;
	
	private  MyGetGetCaiTodayTotalRequest myGetGetCaiTodayTotalRequest;
	
	private  MyGetGetCaiTodayTotalResponse myGetGetCaiTodayTotalResponse;

	@Override
	public boolean checkBizData(JSONObject data) {
		boolean resultflag = false;
		try {
			
			if(data != null){
				this.myGetGetCaiTodayTotalRequest = (MyGetGetCaiTodayTotalRequest)JSONObject.toBean(data, MyGetGetCaiTodayTotalRequest.class);
				resultflag = true;
				
				//非空校验
				for (Field field : this.myGetGetCaiTodayTotalRequest.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					if(field.get(this.myGetGetCaiTodayTotalRequest)==null || "".equals(field.get(this.myGetGetCaiTodayTotalRequest))){
						resultflag = false;
						break;
					}
				}	
			}
		} catch (Exception e) {
			logger.error("我的-获取用户今天得到的踩的接口checkBizData错误："+e.getMessage());
			resultflag = false;
		}
		return resultflag;
	}

	@Override
	public JSONObject handleBiz(JSONObject data) {
		JSONObject result = new JSONObject();
		ResponseBodyResultUtil responseBodyResultUtil=new ResponseBodyResultUtil();
		result=responseBodyResultUtil.getParam_error_result("");
		this.myGetGetCaiTodayTotalResponse=new MyGetGetCaiTodayTotalResponse();
		if(checkBizData(data)){
			try{
				this.myGetGetCaiTodayTotalResponse.setGetCaiTodayTotal((BigInteger)myOptionDAO.getGetCaiTodayTotalByUserId(this.myGetGetCaiTodayTotalRequest.getUserId()));	
				result=responseBodyResultUtil.getSuccess_result(this.myGetGetCaiTodayTotalResponse, "获取成功");
			}catch(Exception e){
				result=responseBodyResultUtil.getSys_error_default_result();
				e.printStackTrace();
				logger.error("我的-获取用户今天得到的踩的接口handleBiz错误："+e.getMessage());
			}
		}
		return result;
	}

}
