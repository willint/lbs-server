package com.lbs.nettyserver.service.functionsevice.find;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbs.nettyserver.dao.find.FindAttentionLiveMessageDAO;
import com.lbs.nettyserver.dao.live.LiveMessageDAO;
import com.lbs.nettyserver.model.common.LiveMessageZanCaiTotal;
import com.lbs.nettyserver.model.request.find.FindAttentionLiveMessageRequest;
import com.lbs.nettyserver.model.response.find.FindAttentionLiveMessageResponse;
import com.lbs.nettyserver.service.impl.CommonBizService;
import com.lbs.nettyserver.utils.functionutils.ResponseBodyResultUtil;

import net.sf.json.JSONObject;

/**
 * 发现-获取我关注的人的现场消息接口
 * @author visual
 *
 */
@Service("findAttentionLiveMessageService")
public class FindAttentionLiveMessageService implements CommonBizService {


	private static final Log logger = LogFactory.getLog(FindAttentionLiveMessageService.class);
	
	@Autowired
	private FindAttentionLiveMessageDAO findAttentionLiveMessageDAO;
	@Autowired
	private LiveMessageDAO liveMessageDAO;
	
	private FindAttentionLiveMessageRequest findAttentionLiveMessageRequest;

	@Override
	public boolean checkBizData(JSONObject data) {
		boolean resultflag = false;
		try {
			if(data != null){
				this.findAttentionLiveMessageRequest = (FindAttentionLiveMessageRequest)JSONObject.toBean(data, FindAttentionLiveMessageRequest.class);
				resultflag = true;
				
				//非空校验
				for (Field field : this.findAttentionLiveMessageRequest.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					if(field.get(this.findAttentionLiveMessageRequest)==null || "".equals(field.get(this.findAttentionLiveMessageRequest))){
						resultflag = false;
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.error("发现-获取我关注的人的现场消息接口checkBizData错误："+e.getMessage());
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
        	List<FindAttentionLiveMessageResponse> list=new ArrayList<FindAttentionLiveMessageResponse>();
			list=findAttentionLiveMessageDAO.getAttentionLiveMessageList(this.findAttentionLiveMessageRequest);
			if(list!=null){
				for (FindAttentionLiveMessageResponse lmr : list) {
					//查询每一消息的赞踩情况
					LiveMessageZanCaiTotal liveMessageZanCaiTotal=liveMessageDAO.getLiveMessageZanAnCaiTotal(lmr.getLmId(), this.findAttentionLiveMessageRequest.getUserId());
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
