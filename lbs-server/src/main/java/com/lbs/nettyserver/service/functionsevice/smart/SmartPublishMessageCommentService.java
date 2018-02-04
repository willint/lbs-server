package com.lbs.nettyserver.service.functionsevice.smart;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbs.nettyserver.dao.smart.SmartOptionDAO;
import com.lbs.nettyserver.model.common.SmartMessageCommentContent;
import com.lbs.nettyserver.model.pojo.SmartMessageComment;
import com.lbs.nettyserver.model.request.smart.SmartPublishMessageCommentRequest;
import com.lbs.nettyserver.model.response.smart.SmartPublishMessageCommentResponse;
import com.lbs.nettyserver.service.impl.CommonBizService;
import com.lbs.nettyserver.utils.functionutils.ResponseBodyResultUtil;
import com.lbs.nettyserver.utils.sysutils.TimeUtil;
import com.lbs.nettyserver.utils.sysutils.UUIDUtil;

import net.sf.json.JSONObject;

/**
 *智囊-发布评论接口
 * @author visual
 *
 */
@Service("smartPublishMessageCommentService")
public class SmartPublishMessageCommentService implements CommonBizService {


	private static final Log logger = LogFactory.getLog(SmartPublishMessageCommentService.class);
	
	@Autowired
	private SmartOptionDAO smartOptionDAO;
	
	private SmartPublishMessageCommentRequest smartPublishMessageCommentRequest;
	
	/**
	 * 校验消息结构是否为空
	 * @return
	 */
	private boolean checkContentIsNull(){
		boolean resultflag=true;
		SmartMessageCommentContent smc=this.smartPublishMessageCommentRequest.getSmartMessageCommentContent();
		if(smc==null)return false;
		for (Field field : smc.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			try {
				if(field.get(smc)==null || "".equals(field.get(smc))){
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
				this.smartPublishMessageCommentRequest = (SmartPublishMessageCommentRequest)JSONObject.toBean(data, SmartPublishMessageCommentRequest.class);
				resultflag = true;
				
				//非空校验
				//校验外层
				for (Field field : this.smartPublishMessageCommentRequest.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					if(field.getName()=="pId" || field.getName()=="imgPathList") continue;//pid和图片可以为空
					if(field.get(this.smartPublishMessageCommentRequest)==null || "".equals(field.get(this.smartPublishMessageCommentRequest))){
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
			logger.error("智囊-发布评论接口checkBizData错误："+e.getMessage());
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
        	if(this.smartPublishMessageCommentRequest.getpId()==null || this.smartPublishMessageCommentRequest.getpId().length()<=0){
        		//查询是否已经对消息评论过
        		if(smartOptionDAO.getSmartMessageCommentBySmIdAndUserId(this.smartPublishMessageCommentRequest.getSmId(),this.smartPublishMessageCommentRequest.getUserId())!=null){
            		return result=responseBodyResultUtil.getParam_error_result("已经评论过了");
            	}
        	}
        	
        	SmartMessageComment smartMessageComment=new SmartMessageComment();
			
			BeanUtils.copyProperties(this.smartPublishMessageCommentRequest, smartMessageComment);
			
			smartMessageComment.setCommentId(UUIDUtil.getUUID());
			smartMessageComment.setCommentTime(TimeUtil.getChinaLocalDateTimeNow());
			smartMessageComment.setContent(JSONObject.fromObject(this.smartPublishMessageCommentRequest.getSmartMessageCommentContent()).toString());

			smartOptionDAO.saveSmartMessageComment(smartMessageComment);
			
			SmartPublishMessageCommentResponse smartPublishMessageCommentResponse=new SmartPublishMessageCommentResponse();
			
			BeanUtils.copyProperties(smartMessageComment, smartPublishMessageCommentResponse);
			smartPublishMessageCommentResponse.setCommentTime(TimeUtil.dateTimeFormatToString(smartMessageComment.getCommentTime()));
			
			
			result=responseBodyResultUtil.getSuccess_result(smartPublishMessageCommentResponse, "评论成功");
		}
		return result;

	}
	
}
