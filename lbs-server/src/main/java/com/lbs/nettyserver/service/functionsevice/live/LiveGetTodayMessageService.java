package com.lbs.nettyserver.service.functionsevice.live;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbs.nettyserver.dao.live.LiveMessageDAO;
import com.lbs.nettyserver.model.common.LiveMessageZanCaiTotal;
import com.lbs.nettyserver.model.request.live.LiveGetTodayMessageRequest;
import com.lbs.nettyserver.model.response.live.LiveGetTodayMessageResponse;
import com.lbs.nettyserver.service.impl.CommonBizService;
import com.lbs.nettyserver.utils.functionutils.ResponseBodyResultUtil;

import net.sf.json.JSONObject;

/**
 * 现场-获取今天现场消息
 * @author visual
 *
 */
@Service("liveGetTodayMessageService")
public class LiveGetTodayMessageService implements CommonBizService {

	private static final Log logger = LogFactory.getLog(LiveGetTodayMessageService.class);
	
	@Autowired
	private LiveMessageDAO liveMessageDAO;
	
	private LiveGetTodayMessageRequest liveGetTodayMessageRequest;

	@Override
	public boolean checkBizData(JSONObject data) {
		boolean resultflag = false;
		try {
			if(data != null){
				this.liveGetTodayMessageRequest = (LiveGetTodayMessageRequest)JSONObject.toBean(data, LiveGetTodayMessageRequest.class);
				resultflag = true;
				
				//非空校验
				for (Field field : this.liveGetTodayMessageRequest.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					if(field.get(this.liveGetTodayMessageRequest)==null || "".equals(field.get(this.liveGetTodayMessageRequest))){
						resultflag = false;
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.error("现场-获取今天消息接口checkBizData错误："+e.getMessage());
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
        	List<LiveGetTodayMessageResponse> list=new ArrayList<LiveGetTodayMessageResponse>();
			list=liveMessageDAO.getTodayLiveMessage(this.liveGetTodayMessageRequest);
			if(list!=null){
				for (LiveGetTodayMessageResponse lmr : list) {
					//查询每一消息的赞踩情况
					LiveMessageZanCaiTotal liveMessageZanCaiTotal=liveMessageDAO.getLiveMessageZanAnCaiTotal(lmr.getLmId(), this.liveGetTodayMessageRequest.getUserId());
					if(liveMessageZanCaiTotal!=null){
						lmr.setCaiTotal(liveMessageZanCaiTotal.getCaiTotal());
						lmr.setIsCai(liveMessageZanCaiTotal.getIsCai());
						lmr.setZanTotal(liveMessageZanCaiTotal.getZanTotal());
						lmr.setIsZan(liveMessageZanCaiTotal.getIsZan());
					}
				}
			}
			result=responseBodyResultUtil.getSuccess_result(list==null?new Object[0]:list, "获取成功");
		}
		return result;

	}
	
}
