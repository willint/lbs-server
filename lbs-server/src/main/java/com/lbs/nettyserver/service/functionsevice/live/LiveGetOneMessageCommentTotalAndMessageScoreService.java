package com.lbs.nettyserver.service.functionsevice.live;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbs.nettyserver.dao.live.LiveMessageDAO;
import com.lbs.nettyserver.model.request.live.LiveGetOneMessageCommentTotalAndMessageScoreRequest;
import com.lbs.nettyserver.model.response.live.LiveGetOneMessageCommentTotalAndMessageScoreResponse;
import com.lbs.nettyserver.service.impl.CommonBizService;
import com.lbs.nettyserver.utils.functionutils.ResponseBodyResultUtil;

import net.sf.json.JSONObject;

/**
 * 现场-获取某一条消息的评论总数和消息质量得分接口
 * @author visual
 *
 */
@Service("liveGetOneMessageCommentTotalAndMessageScoreService")
public class LiveGetOneMessageCommentTotalAndMessageScoreService implements CommonBizService {

	private static final Log logger = LogFactory.getLog(LiveGetOneMessageCommentTotalAndMessageScoreService.class);
	
	@Autowired
	private LiveMessageDAO liveMessageDAO;
	
	private LiveGetOneMessageCommentTotalAndMessageScoreRequest liveGetOneMessageCommentTotalAndMessageScoreRequest;

	@Override
	public boolean checkBizData(JSONObject data) {
		boolean resultflag = false;
		try {
			if(data != null){
				this.liveGetOneMessageCommentTotalAndMessageScoreRequest = (LiveGetOneMessageCommentTotalAndMessageScoreRequest)JSONObject.toBean(data, LiveGetOneMessageCommentTotalAndMessageScoreRequest.class);
				resultflag = true;
				
				//非空校验
				for (Field field : this.liveGetOneMessageCommentTotalAndMessageScoreRequest.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					if(field.get(this.liveGetOneMessageCommentTotalAndMessageScoreRequest)==null || "".equals(field.get(this.liveGetOneMessageCommentTotalAndMessageScoreRequest))){
						resultflag = false;
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.error("现场-获取某一条消息的评论总数和消息质量得分接口checkBizData错误："+e.getMessage());
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
        	
        	LiveGetOneMessageCommentTotalAndMessageScoreResponse livets=liveMessageDAO.getOneMessageCommentTotalAndMessageScoreById(this.liveGetOneMessageCommentTotalAndMessageScoreRequest.getLmId());
			result=responseBodyResultUtil.getSuccess_result(livets==null?new Object():livets, "获取成功");
		}
		return result;

	}
}
