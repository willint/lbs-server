package com.lbs.nettyserver.service.functionsevice.common;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbs.nettyserver.dao.AttentionPersonDAO;
import com.lbs.nettyserver.model.pojo.AttentionPerson;
import com.lbs.nettyserver.model.request.common.CancelAttentionPersonRequest;
import com.lbs.nettyserver.service.impl.CommonBizService;
import com.lbs.nettyserver.utils.functionutils.ResponseBodyResultUtil;
import net.sf.json.JSONObject;

/**
 * 取消关注用户接口
 * @author visual
 *
 */
@Service("cancelAttentionPersonService")
public class CancelAttentionPersonService implements CommonBizService {


	private static final Log logger = LogFactory.getLog(CancelAttentionPersonService.class);
	
	@Autowired
	private AttentionPersonDAO attentionPersonDAO;
	
	private CancelAttentionPersonRequest cancelAttentionPersonRequest;

	@Override
	public boolean checkBizData(JSONObject data) {
		boolean resultflag = false;
		try {
			if(data != null){
				this.cancelAttentionPersonRequest = (CancelAttentionPersonRequest)JSONObject.toBean(data, CancelAttentionPersonRequest.class);
				resultflag = true;
				
				//非空校验
				for (Field field : this.cancelAttentionPersonRequest.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					if(field.get(this.cancelAttentionPersonRequest)==null || "".equals(field.get(this.cancelAttentionPersonRequest))){
						resultflag = false;
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.error("取消关注用户接口checkBizData错误："+e.getMessage());
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

        	AttentionPerson liveAttentionPerson=attentionPersonDAO.selectAttentionPersonById(this.cancelAttentionPersonRequest.getLapId());
        	if(liveAttentionPerson==null){
        		return result=responseBodyResultUtil.getParam_error_result("还未关注");
        	}
        	
        	attentionPersonDAO.deleteAttentionPerson(liveAttentionPerson);
			result=responseBodyResultUtil.getSuccess_result(null, "取消成功");
		}
		return result;

	}
}
