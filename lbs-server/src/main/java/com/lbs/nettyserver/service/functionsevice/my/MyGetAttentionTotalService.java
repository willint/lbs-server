package com.lbs.nettyserver.service.functionsevice.my;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbs.nettyserver.dao.my.MyOptionDAO;
import com.lbs.nettyserver.model.request.my.MyGetAttentionTotalRequest;
import com.lbs.nettyserver.model.response.my.MyGetAttentionTotalResponse;
import com.lbs.nettyserver.service.impl.CommonBizService;
import com.lbs.nettyserver.utils.functionutils.ResponseBodyResultUtil;

import net.sf.json.JSONObject;

/**
 * 我的-获取用户关注的人数（我关注的人数）的接口
 * @author visual
 *
 */
@Service("myGetAttentionTotalService")
public class MyGetAttentionTotalService implements CommonBizService {


	private static final Log logger = LogFactory.getLog(MyGetAttentionTotalService.class);
	
	@Autowired
	private MyOptionDAO myOptionDAO;
	
	private  MyGetAttentionTotalRequest myGetAttentionTotalRequest;
	
	private  MyGetAttentionTotalResponse myGetAttentionTotalResponse;

	@Override
	public boolean checkBizData(JSONObject data) {
		boolean resultflag = false;
		try {
			
			if(data != null){
				this.myGetAttentionTotalRequest = (MyGetAttentionTotalRequest)JSONObject.toBean(data, MyGetAttentionTotalRequest.class);
				resultflag = true;
				
				//非空校验
				for (Field field : this.myGetAttentionTotalRequest.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					if(field.get(this.myGetAttentionTotalRequest)==null || "".equals(field.get(this.myGetAttentionTotalRequest))){
						resultflag = false;
						break;
					}
				}	
			}
		} catch (Exception e) {
			logger.error("我的-获取用户现场关注的人数（我关注的人数）的接口checkBizData错误："+e.getMessage());
			resultflag = false;
		}
		return resultflag;
	}

	@Override
	public JSONObject handleBiz(JSONObject data) {
		JSONObject result = new JSONObject();
		ResponseBodyResultUtil responseBodyResultUtil=new ResponseBodyResultUtil();
		result=responseBodyResultUtil.getParam_error_result("");
		this.myGetAttentionTotalResponse=new MyGetAttentionTotalResponse();
		if(checkBizData(data)){
			try{
				this.myGetAttentionTotalResponse.setAttentionTotal((BigDecimal)myOptionDAO.getAttentionTotalByUserId(this.myGetAttentionTotalRequest.getUserId()));	
				result=responseBodyResultUtil.getSuccess_result(this.myGetAttentionTotalResponse, "获取成功");
			}catch(Exception e){
				result=responseBodyResultUtil.getSys_error_default_result();
				e.printStackTrace();
				logger.error("我的-获取用户现场关注的人数（我关注的人数）的接口handleBiz错误："+e.getMessage());
			}
		}
		return result;
	}
}
