package com.lbs.nettyserver.service.functionsevice.smart;

import java.lang.reflect.Field;
import java.math.BigInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbs.nettyserver.dao.smart.SmartOptionDAO;
import com.lbs.nettyserver.model.request.smart.SmartGetOneMessageCommentTotalRequest;
import com.lbs.nettyserver.model.response.smart.SmartGetOneMessageCommentTotalResponse;
import com.lbs.nettyserver.service.impl.CommonBizService;
import com.lbs.nettyserver.utils.functionutils.ResponseBodyResultUtil;

import net.sf.json.JSONObject;

/**
 * 智囊-获取某一条智囊消息得到的评论总数接口
 * @author visual
 *
 */
@Service("smartGetOneMessageCommentTotalService")
public class SmartGetOneMessageCommentTotalService implements CommonBizService {


	private static final Log logger = LogFactory.getLog(SmartGetOneMessageCommentTotalService.class);
	
	@Autowired
	private SmartOptionDAO smartOptionDAO;
	
	private  SmartGetOneMessageCommentTotalRequest smartGetOneMessageCommentTotalRequest;
	
	private  SmartGetOneMessageCommentTotalResponse smartGetOneMessageCommentTotalResponse;

	@Override
	public boolean checkBizData(JSONObject data) {
		boolean resultflag = false;
		try {
			
			if(data != null){
				this.smartGetOneMessageCommentTotalRequest = (SmartGetOneMessageCommentTotalRequest)JSONObject.toBean(data, SmartGetOneMessageCommentTotalRequest.class);
				resultflag = true;
				
				//非空校验
				for (Field field : this.smartGetOneMessageCommentTotalRequest.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					if(field.get(this.smartGetOneMessageCommentTotalRequest)==null || "".equals(field.get(this.smartGetOneMessageCommentTotalRequest))){
						resultflag = false;
						break;
					}
				}	
			}
		} catch (Exception e) {
			logger.error("智囊-获取某一条智囊消息得到的评论总数接口checkBizData错误："+e.getMessage());
			resultflag = false;
		}
		return resultflag;
	}

	@Override
	public JSONObject handleBiz(JSONObject data) {
		
		JSONObject result = new JSONObject();
		ResponseBodyResultUtil responseBodyResultUtil=new ResponseBodyResultUtil();
		result=responseBodyResultUtil.getParam_error_result("");
		this.smartGetOneMessageCommentTotalResponse=new SmartGetOneMessageCommentTotalResponse();
		if(checkBizData(data)){
			try{
				this.smartGetOneMessageCommentTotalResponse.setSmartCommentCount((BigInteger)smartOptionDAO.getOneSmartMessageCommentTotalBySmId(this.smartGetOneMessageCommentTotalRequest.getSmId()));;	
				result=responseBodyResultUtil.getSuccess_result(this.smartGetOneMessageCommentTotalResponse, "获取成功");
			}catch(Exception e){
				result=responseBodyResultUtil.getSys_error_default_result();
				e.printStackTrace();
				logger.error("智囊-获取某一条智囊消息得到的评论总数接口handleBiz错误："+e.getMessage());
			}
		}
		return result;
	}
}
