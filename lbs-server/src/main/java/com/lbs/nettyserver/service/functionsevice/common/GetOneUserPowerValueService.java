package com.lbs.nettyserver.service.functionsevice.common;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbs.nettyserver.dao.PowerValueDAO;
import com.lbs.nettyserver.model.pojo.PowerValue;
import com.lbs.nettyserver.model.request.common.GetOneUserPowerValueRequest;
import com.lbs.nettyserver.model.response.common.GetOneUserPowerValueResponse;
import com.lbs.nettyserver.service.impl.CommonBizService;
import com.lbs.nettyserver.utils.functionutils.ResponseBodyResultUtil;

import net.sf.json.JSONObject;

/**
 * 获取某一用户的体力和能量接口
 * @author visual
 *
 */
@Service("getOneUserPowerValueService")
public class GetOneUserPowerValueService implements CommonBizService {


	private static final Log logger = LogFactory.getLog(GetOneUserPowerValueService.class);
	
	@Autowired
	private PowerValueDAO powerValueDAO;
	
	private GetOneUserPowerValueRequest getOneUserPowerValueRequest;
	

	@Override
	public boolean checkBizData(JSONObject data) {
		boolean resultflag = false;
		try {
			if(data != null){
				this.getOneUserPowerValueRequest = (GetOneUserPowerValueRequest)JSONObject.toBean(data, GetOneUserPowerValueRequest.class);
				resultflag = true;
				
				//非空校验
				for (Field field : this.getOneUserPowerValueRequest.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					if(field.get(this.getOneUserPowerValueRequest)==null || "".equals(field.get(this.getOneUserPowerValueRequest))){
						resultflag = false;
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.error("获取某一用户的体力和能量接口checkBizData错误："+e.getMessage());
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
        	
        	PowerValue powerValue=powerValueDAO.getPowerValueByUserId(this.getOneUserPowerValueRequest.getUserId());
        	GetOneUserPowerValueResponse getOneUserPowerValueResponse=new GetOneUserPowerValueResponse();
        	if(powerValue!=null){
        		getOneUserPowerValueResponse.setTpvId(powerValue.getTpvId());
        		getOneUserPowerValueResponse.setTlValue(powerValue.getTlValue());
        		getOneUserPowerValueResponse.setNlValue(powerValue.getNlValue());
        		getOneUserPowerValueResponse.setUpdateTime(powerValue.getUpdateTime());
			}
        	result=responseBodyResultUtil.getSuccess_result(powerValue==null?new Object():getOneUserPowerValueResponse, "获取成功");
		}
		return result;

	}
}
