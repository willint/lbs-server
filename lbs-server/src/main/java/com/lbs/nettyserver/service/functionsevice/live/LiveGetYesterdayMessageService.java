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
import com.lbs.nettyserver.model.request.live.LiveGetYesterdayMessageRequest;
import com.lbs.nettyserver.model.response.live.LiveGetYesterdayMessageResponse;
import com.lbs.nettyserver.service.impl.CommonBizService;
import com.lbs.nettyserver.utils.functionutils.ResponseBodyResultUtil;

import net.sf.json.JSONObject;
/**
 * 现场-获取昨天现场消息请求接口
 * @author visual
 *
 */
@Service("liveGetYesterdayMessageService")
public class LiveGetYesterdayMessageService implements CommonBizService {

	private static final Log logger = LogFactory.getLog(LiveGetYesterdayMessageService.class);
	
	@Autowired
	private LiveMessageDAO liveMessageDAO;
	
	private LiveGetYesterdayMessageRequest liveGetYesterdayMessageRequest;

	@Override
	public boolean checkBizData(JSONObject data) {
		boolean resultflag = false;
		try {
			if(data != null){
				this.liveGetYesterdayMessageRequest = (LiveGetYesterdayMessageRequest)JSONObject.toBean(data, LiveGetYesterdayMessageRequest.class);
				resultflag = true;
				
				//非空校验
				for (Field field : this.liveGetYesterdayMessageRequest.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					if(field.get(this.liveGetYesterdayMessageRequest)==null || "".equals(field.get(this.liveGetYesterdayMessageRequest))){
						resultflag = false;
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.error("现场-获取昨天消息接口checkBizData错误："+e.getMessage());
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
        	List<LiveGetYesterdayMessageResponse> list=new ArrayList<LiveGetYesterdayMessageResponse>();
			list=liveMessageDAO.getYesterdayLiveMessage(this.liveGetYesterdayMessageRequest);
			if(list!=null){
				for (LiveGetYesterdayMessageResponse lmr : list) {
					//查询每一消息的赞踩情况
					LiveMessageZanCaiTotal liveMessageZanCaiTotal=liveMessageDAO.getLiveMessageZanAnCaiTotal(lmr.getLmId(), this.liveGetYesterdayMessageRequest.getUserId());
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
