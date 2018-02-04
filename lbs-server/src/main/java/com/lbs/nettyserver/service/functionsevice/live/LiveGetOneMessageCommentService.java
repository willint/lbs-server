package com.lbs.nettyserver.service.functionsevice.live;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbs.nettyserver.dao.live.LiveMessageCommentDAO;
import com.lbs.nettyserver.model.request.live.LiveGetOneMessageCommentRequest;
import com.lbs.nettyserver.model.response.live.LiveGetOneMessageCommentResponse;
import com.lbs.nettyserver.service.impl.CommonBizService;
import com.lbs.nettyserver.utils.functionutils.ResponseBodyResultUtil;

import net.sf.json.JSONObject;

/**
 * 现场-获取某一条消息的评论接口
 * @author visual
 *
 */
@Service("liveGetOneMessageCommentService")
public class LiveGetOneMessageCommentService  implements CommonBizService {
	
	private static final Log logger = LogFactory.getLog(LiveGetOneMessageCommentService.class);
	
	@Autowired
	private LiveMessageCommentDAO liveMessageCommentDAO;
	
	private LiveGetOneMessageCommentRequest liveGetOneMessageCommentRequest;

	@Override
	public boolean checkBizData(JSONObject data) {
		boolean resultflag = false;
		try {
			if(data != null){
				this.liveGetOneMessageCommentRequest = (LiveGetOneMessageCommentRequest)JSONObject.toBean(data, LiveGetOneMessageCommentRequest.class);
				resultflag = true;
				
				//非空校验
				for (Field field : this.liveGetOneMessageCommentRequest.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					if(field.get(this.liveGetOneMessageCommentRequest)==null || "".equals(field.get(this.liveGetOneMessageCommentRequest))){
						resultflag = false;
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.error("现场-获取现场消息评论接口checkBizData错误："+e.getMessage());
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
        	List<LiveGetOneMessageCommentResponse> list=new ArrayList<LiveGetOneMessageCommentResponse>();
			list=liveMessageCommentDAO.getLiveOneMessageCommentById(this.liveGetOneMessageCommentRequest);
			result=responseBodyResultUtil.getSuccess_result(list==null?new Object[0]:list, "获取成功");
		}
		return result;

	}
}
