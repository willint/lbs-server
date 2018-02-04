package com.lbs.nettyserver.service.functionsevice.live;

import java.lang.reflect.Field;
import java.math.BigDecimal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbs.nettyserver.dao.live.LiveMessageDAO;
import com.lbs.nettyserver.model.common.LiveMessageContent;
import com.lbs.nettyserver.model.pojo.LiveMessage;
import com.lbs.nettyserver.model.request.live.LivePublishMessageRequest;
import com.lbs.nettyserver.model.response.live.LivePublishMessageResponse;
import com.lbs.nettyserver.model.sys.MediaTypeConstants;
import com.lbs.nettyserver.service.impl.CommonBizService;
import com.lbs.nettyserver.utils.functionutils.ResponseBodyResultUtil;
import com.lbs.nettyserver.utils.sysutils.TimeUtil;
import com.lbs.nettyserver.utils.sysutils.UUIDUtil;

import net.sf.json.JSONObject;

@Service("livePublishMessageService")
public class LivePublishMessageService implements CommonBizService {
	
	private static final Log logger = LogFactory.getLog(LivePublishMessageService.class);
	
	@Autowired
	private LiveMessageDAO liveMessageDAO;
	
	private LivePublishMessageRequest livePublishMessageRequest;

	/**
	 * 校验消息内容是否有空存在
	 * @return
	 */
	private boolean checkContentIsNull(){
		boolean checkResult=true;
		
		LiveMessageContent lmc=this.livePublishMessageRequest.getLiveMessageContent();
		if(lmc==null) return false;
		if(lmc.getTitle() == null || "".equals(lmc.getTitle())){
			return checkResult=false;
		}
		
		if(lmc.getDescribe() == null || "".equals(lmc.getDescribe())){
			return checkResult=false;
		}
		
		if(lmc.getMediaType() == null || "".equals(lmc.getMediaType())){
			return checkResult = false;
		}else{
			//视频
			if(MediaTypeConstants.VIDEO.equals(this.livePublishMessageRequest.getLiveMessageContent().getMediaType())){
				if(lmc.getThumbPath() == null || "".equals(lmc.getThumbPath())){
					return checkResult=false;
				}
				
				if(lmc.getVideoTime() == null || "".equals(lmc.getVideoTime())){
					return checkResult=false;
				}
				
				if(lmc.getVideoSize() == null || "".equals(lmc.getVideoSize())){
					return checkResult=false;
				}
			}else{
				if(lmc.getImgPathList() == null || lmc.getImgPathList().size()==0){
					return checkResult=false;
				}
			}
		}
		return checkResult;
	}
	
	
	@Override
	public boolean checkBizData(JSONObject data) {
		boolean resultflag = false;
		try {
			if(data != null){
				this.livePublishMessageRequest = (LivePublishMessageRequest)JSONObject.toBean(data, LivePublishMessageRequest.class);
				resultflag = true;
				
				//非空校验
				//校验外层
				for (Field field : this.livePublishMessageRequest.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					if(field.get(this.livePublishMessageRequest)==null || "".equals(field.get(this.livePublishMessageRequest))){
						resultflag = false;
						break;
					}
				}
				//校验内容结构
				if(resultflag){
					resultflag=checkContentIsNull();
				}
				
			}
		} catch (Exception e) {
			logger.error("现场-发布现场消息接口checkBizData错误："+e.getMessage());
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
        	LiveMessage liveMessage=new LiveMessage();
			liveMessage.setLmId(UUIDUtil.getUUID());
			liveMessage.setUserId(this.livePublishMessageRequest.getUserId());
			liveMessage.setJd(this.livePublishMessageRequest.getJd());
			liveMessage.setWd(this.livePublishMessageRequest.getWd());
			liveMessage.setSendTime(TimeUtil.getChinaLocalDateTimeNow());
			liveMessage.setContent(JSONObject.fromObject(this.livePublishMessageRequest.getLiveMessageContent()).toString());
			liveMessage.setMessageScore(BigDecimal.ZERO);
			liveMessage.setLocationName(this.livePublishMessageRequest.getLocationName());
			liveMessage.setIsDelete('0');//0-未删除
			liveMessageDAO.saveLiveMessage(liveMessage);
			
			LivePublishMessageResponse livePublishMessageResponse=new LivePublishMessageResponse();
			BeanUtils.copyProperties(liveMessage, livePublishMessageResponse);
			livePublishMessageResponse.setSendTime(TimeUtil.dateTimeFormatToString(liveMessage.getSendTime()));
			
			result=responseBodyResultUtil.getSuccess_result(livePublishMessageResponse, "发布成功");
		}
		return result;

	}
}
