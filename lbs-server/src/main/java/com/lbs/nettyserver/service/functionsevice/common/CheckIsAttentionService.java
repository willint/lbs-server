package com.lbs.nettyserver.service.functionsevice.common;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
	

import com.lbs.nettyserver.dao.AttentionPersonDAO;
import com.lbs.nettyserver.model.pojo.AttentionPerson;
import com.lbs.nettyserver.model.request.common.CheckIsAttentionRequest;
import com.lbs.nettyserver.model.response.common.CheckIsAttentionResponse;
import com.lbs.nettyserver.service.impl.CommonBizService;
import com.lbs.nettyserver.utils.functionutils.ResponseBodyResultUtil;

import net.sf.json.JSONObject;

/**
 * 检查用户是否已经关注接口
 * @author visual
 *
 */
@Service("checkIsAttentionService")
public class CheckIsAttentionService implements CommonBizService {


	private static final Log logger = LogFactory.getLog(CheckIsAttentionService.class);
	
	@Autowired
	private AttentionPersonDAO attentionPersonDAO;
	
	private CheckIsAttentionRequest checkIsAttentionRequest;

	@Override
	public boolean checkBizData(JSONObject data) {
		boolean resultflag = false;
		try {
			if(data != null){
				this.checkIsAttentionRequest = (CheckIsAttentionRequest)JSONObject.toBean(data, CheckIsAttentionRequest.class);
				resultflag = true;
				
				//非空校验
				for (Field field : this.checkIsAttentionRequest.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					if(field.get(this.checkIsAttentionRequest)==null || "".equals(field.get(this.checkIsAttentionRequest))){
						resultflag = false;
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.error("检查用户是否已经关注接口checkBizData错误："+e.getMessage());
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

        	AttentionPerson liveAttentionPerson=attentionPersonDAO.selectAttentionPersonByIdAndUserId(this.checkIsAttentionRequest.getBeUserId(), this.checkIsAttentionRequest.getUserId());
        	
        	CheckIsAttentionResponse checkIsAttentionResponse=new CheckIsAttentionResponse();
        	checkIsAttentionResponse.setIsAttention(liveAttentionPerson==null?false:true);
        	result=responseBodyResultUtil.getSuccess_result(checkIsAttentionResponse, "获取成功");
			
		}
		return result;

	}
}