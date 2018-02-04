package com.lbs.nettyserver.service.functionsevice.common;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbs.nettyserver.dao.AttentionPersonDAO;
import com.lbs.nettyserver.model.pojo.AttentionPerson;
import com.lbs.nettyserver.model.request.common.AttentionPersonRequest;
import com.lbs.nettyserver.model.response.common.AttentionPersonResponse;
import com.lbs.nettyserver.service.impl.CommonBizService;
import com.lbs.nettyserver.utils.functionutils.ResponseBodyResultUtil;
import com.lbs.nettyserver.utils.sysutils.TimeUtil;
import com.lbs.nettyserver.utils.sysutils.UUIDUtil;

import net.sf.json.JSONObject;

/**
 * 关注用户接口
 * @author visual
 *
 */
@Service("attentionPersonService")
public class AttentionPersonService implements CommonBizService {

	private static final Log logger = LogFactory.getLog(AttentionPersonService.class);
	
	@Autowired
	private AttentionPersonDAO attentionPersonDAO;
	
	private AttentionPersonRequest attentionPersonRequest;

	@Override
	public boolean checkBizData(JSONObject data) {
		boolean resultflag = false;
		try {
			if(data != null){
				this.attentionPersonRequest = (AttentionPersonRequest)JSONObject.toBean(data, AttentionPersonRequest.class);
				resultflag = true;
				
				//非空校验
				for (Field field : this.attentionPersonRequest.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					if(field.get(this.attentionPersonRequest)==null || "".equals(field.get(this.attentionPersonRequest))){
						resultflag = false;
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.error("关注用户接口checkBizData错误："+e.getMessage());
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
        	
        	//如果已经关注了
        	if(attentionPersonDAO.selectAttentionPersonByIdAndUserId(this.attentionPersonRequest.getBeUserId(), this.attentionPersonRequest.getUserId())!=null){
        		return result=responseBodyResultUtil.getParam_error_result("已经关注了");
        	}
        	
        	//实体类
        	AttentionPerson liveAttentionPerson=new AttentionPerson();
        	BeanUtils.copyProperties(this.attentionPersonRequest, liveAttentionPerson);
        	liveAttentionPerson.setLapId(UUIDUtil.getUUID());
        	liveAttentionPerson.setAttentionTime(TimeUtil.getChinaLocalDateTimeNow());
        	
        	attentionPersonDAO.saveAttentionPerson(liveAttentionPerson);
        	
        	//返回结果
        	AttentionPersonResponse attentionPersonResponse=new AttentionPersonResponse();
        	attentionPersonResponse.setLapId(liveAttentionPerson.getLapId());
        	attentionPersonResponse.setAttentionTime(TimeUtil.dateTimeFormatToString(liveAttentionPerson.getAttentionTime()));

			result=responseBodyResultUtil.getSuccess_result(attentionPersonResponse, "关注成功");
		}
		return result;

	}
}
