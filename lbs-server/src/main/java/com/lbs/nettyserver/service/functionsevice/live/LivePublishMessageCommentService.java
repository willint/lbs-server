package com.lbs.nettyserver.service.functionsevice.live;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbs.nettyserver.dao.live.LiveMessageCommentDAO;
import com.lbs.nettyserver.model.common.LiveMessageCommentContent;
import com.lbs.nettyserver.model.pojo.LiveMessageComment;
import com.lbs.nettyserver.model.request.live.LivePublishMessageCommentRequest;
import com.lbs.nettyserver.model.response.live.LivePublishMessageCommentResponse;
import com.lbs.nettyserver.service.impl.CommonBizService;
import com.lbs.nettyserver.utils.functionutils.LiveCommentScoreUtil;
import com.lbs.nettyserver.utils.functionutils.ResponseBodyResultUtil;
import com.lbs.nettyserver.utils.sysutils.TimeUtil;
import com.lbs.nettyserver.utils.sysutils.UUIDUtil;

import net.sf.json.JSONObject;

/**
 *现场-发布评论接口
 * @author visual
 *
 */
@Service("livePublishMessageCommentService")
public class LivePublishMessageCommentService implements CommonBizService {

	private static final Log logger = LogFactory.getLog(LivePublishMessageCommentService.class);
	
	@Autowired
	private LiveMessageCommentDAO liveMessageCommentDAO;
	
	private LivePublishMessageCommentRequest livePublishMessageCommentRequest;
	
	/**
	 * 校验消息结构是否为空
	 * @return
	 */
	private boolean checkContentIsNull(){
		boolean resultflag=true;
		LiveMessageCommentContent lmc=this.livePublishMessageCommentRequest.getLiveMessageCommentContent();
		if(lmc==null)return false;
		for (Field field : lmc.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			try {
				if(field.get(lmc)==null || "".equals(field.get(lmc))){
					resultflag = false;
					break;
				}
			} catch (Exception e) {
				resultflag=false;
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return resultflag;
	}

	@Override
	public boolean checkBizData(JSONObject data) {
		boolean resultflag = false;
		try {
			if(data != null){
				this.livePublishMessageCommentRequest = (LivePublishMessageCommentRequest)JSONObject.toBean(data, LivePublishMessageCommentRequest.class);
				resultflag = true;
				
				//非空校验
				//校验外层
				for (Field field : this.livePublishMessageCommentRequest.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					if(field.getName()=="pId" || field.getName()=="imgPathList") continue;//pid和图片可以为空
					if(field.get(this.livePublishMessageCommentRequest)==null || "".equals(field.get(this.livePublishMessageCommentRequest))){
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
			logger.error("现场-发布评论接口checkBizData错误："+e.getMessage());
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
        	
        	/**
        	 * 如果是直接对消息评论而不是回复评论
        	 */
        	if(this.livePublishMessageCommentRequest.getpId()==null || this.livePublishMessageCommentRequest.getpId().length()<=0){
        		//查询是否已经对消息评论过
        		if(liveMessageCommentDAO.getSmartMessageCommentByLmIdAndUserId(this.livePublishMessageCommentRequest.getLmId(),this.livePublishMessageCommentRequest.getUserId())!=null){
            		return result=responseBodyResultUtil.getParam_error_result("已经评论过了");
            	}
        	}
        	
        	LiveMessageComment liveMessageComment=new LiveMessageComment();
			
			BeanUtils.copyProperties(this.livePublishMessageCommentRequest, liveMessageComment);
			
			liveMessageComment.setCommentId(UUIDUtil.getUUID());
			liveMessageComment.setCommentTime(TimeUtil.getChinaLocalDateTimeNow());
			liveMessageComment.setContent(JSONObject.fromObject(this.livePublishMessageCommentRequest.getLiveMessageCommentContent()).toString());
			liveMessageComment.setCommentScore(LiveCommentScoreUtil.getCommentScore(liveMessageComment.getRealScore(),liveMessageComment.getClearScore(),liveMessageComment.getUseScore()));
			
			liveMessageCommentDAO.saveLiveMessageComment(liveMessageComment);
			
			LivePublishMessageCommentResponse livePublishMessageCommentResponse=new LivePublishMessageCommentResponse();
			
			BeanUtils.copyProperties(liveMessageComment, livePublishMessageCommentResponse);
			livePublishMessageCommentResponse.setCommentTime(TimeUtil.dateTimeFormatToString(liveMessageComment.getCommentTime()));
			livePublishMessageCommentResponse.setScoreView(LiveCommentScoreUtil.getCommentScoreView(liveMessageComment.getCommentScore()));
			
			result=responseBodyResultUtil.getSuccess_result(livePublishMessageCommentResponse, "评论成功");
		}
		return result;

	}
}
