package com.lbs.nettyserver.service.functionsevice.live;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbs.nettyserver.dao.live.LiveMessageDAO;
import com.lbs.nettyserver.model.request.live.LiveOneUserZanAndCaiTodayTotalRequest;
import com.lbs.nettyserver.model.response.live.LiveOneUserZanAndCaiTodayTotalResponse;
import com.lbs.nettyserver.service.impl.CommonBizService;
import com.lbs.nettyserver.utils.functionutils.ResponseBodyResultUtil;

import net.sf.json.JSONObject;

/**
 * 现场-获取某一用户今天获得的赞踩总数接口
 * @author visual
 *
 */

@Service("liveOneUserZanAndCaiTodayTotalService")
public class LiveOneUserZanAndCaiTodayTotalService implements CommonBizService {

	private static final Log logger = LogFactory.getLog(LiveOneUserZanAndCaiTodayTotalService.class);
	
	
	@Autowired
	private LiveMessageDAO liveMessageDAO;
	
	private LiveOneUserZanAndCaiTodayTotalRequest liveOneUserZanAndCaiTodayTotalRequest;

	@Override
	public boolean checkBizData(JSONObject data) {
		boolean resultflag = false;
		try {
			if(data != null){
				this.liveOneUserZanAndCaiTodayTotalRequest = (LiveOneUserZanAndCaiTodayTotalRequest)JSONObject.toBean(data, LiveOneUserZanAndCaiTodayTotalRequest.class);
				resultflag = true;
				
				//非空校验
				for (Field field : this.liveOneUserZanAndCaiTodayTotalRequest.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					if(field.get(this.liveOneUserZanAndCaiTodayTotalRequest)==null || "".equals(field.get(this.liveOneUserZanAndCaiTodayTotalRequest))){
						resultflag = false;
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.error("现场-获取某一用户今天获得的赞踩总数接口checkBizData错误："+e.getMessage());
			resultflag = false;
		}
		return resultflag;
	}

	@Override
	public JSONObject handleBiz(JSONObject data) {
		
		JSONObject result = new JSONObject();
	    ResponseBodyResultUtil responseBodyResultUtil=new ResponseBodyResultUtil();
		result=responseBodyResultUtil.getParam_error_result("获取失败");
		
        if(checkBizData(data)){
        	LiveOneUserZanAndCaiTodayTotalResponse liveOneUserZanAndCaiTodayTotalResponse=new LiveOneUserZanAndCaiTodayTotalResponse();
        	liveOneUserZanAndCaiTodayTotalResponse=liveMessageDAO.getOneUserZanAndCaiTodayTotalByBeUserId(this.liveOneUserZanAndCaiTodayTotalRequest.getUserId());
        	result=responseBodyResultUtil.getSuccess_result(liveOneUserZanAndCaiTodayTotalResponse==null?new Object():liveOneUserZanAndCaiTodayTotalResponse, "获取成功");
        	
        }
		return result;

	}
}
