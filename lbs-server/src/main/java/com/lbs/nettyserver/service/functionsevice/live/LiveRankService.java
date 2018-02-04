package com.lbs.nettyserver.service.functionsevice.live;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbs.nettyserver.dao.live.LiveMessageDAO;
import com.lbs.nettyserver.model.request.live.LiveRankRequest;
import com.lbs.nettyserver.model.response.live.LiveRankResponse;
import com.lbs.nettyserver.service.impl.CommonBizService;
import com.lbs.nettyserver.utils.functionutils.ResponseBodyResultUtil;

import net.sf.json.JSONObject;


/**
 * 现场-获取排名接口
 * @author visual
 *
 */
@Service("liveRankService")
public class LiveRankService implements CommonBizService {


	private static final Log logger = LogFactory.getLog(LiveRankService.class);
	
	@Autowired
	private LiveMessageDAO liveMessageDAO;
	
	private LiveRankRequest liveRankRequest;

	@Override
	public boolean checkBizData(JSONObject data) {
		boolean resultflag = false;
		try {
			if(data != null){
				this.liveRankRequest = (LiveRankRequest)JSONObject.toBean(data, LiveRankRequest.class);
				resultflag = true;
				
				//非空校验
				for (Field field : this.liveRankRequest.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					if(field.get(this.liveRankRequest)==null || "".equals(field.get(this.liveRankRequest))){
						resultflag = false;
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.error("现场-获取排名接口checkBizData错误："+e.getMessage());
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
        	
        	LiveRankResponse liveRankResponse=new LiveRankResponse();
        	
        	liveRankResponse=liveMessageDAO.getLiveOneUserRank(this.liveRankRequest);
			
			result=responseBodyResultUtil.getSuccess_result(liveRankResponse==null?new Object():liveRankResponse, "获取成功");
		}
		return result;

	}
}
