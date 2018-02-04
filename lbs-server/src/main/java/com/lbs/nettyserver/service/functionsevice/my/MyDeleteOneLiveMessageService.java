package com.lbs.nettyserver.service.functionsevice.my;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbs.nettyserver.dao.my.MyOptionDAO;
import com.lbs.nettyserver.model.request.my.MyDeleteOneLiveMessageRequest;
import com.lbs.nettyserver.model.response.my.MyDeleteOneLiveMessageResponse;
import com.lbs.nettyserver.service.impl.CommonBizService;
import com.lbs.nettyserver.utils.functionutils.ResponseBodyResultUtil;

import net.sf.json.JSONObject;

/**
 * 我的-删除某一条现场消息接口
 * @author visual
 *
 */
@Service("myDeleteOneLiveMessageService")
public class MyDeleteOneLiveMessageService  implements CommonBizService  {

private static final Log logger = LogFactory.getLog(MyGetAttentionPersonsListService.class);
	
	@Autowired
	private MyOptionDAO myOptionDAO;
	
	private MyDeleteOneLiveMessageRequest myDeleteOneLiveMessageRequest;

	@Override
	public boolean checkBizData(JSONObject data) {
		boolean resultflag = false;
		try {
			
			if(data != null){
				this.myDeleteOneLiveMessageRequest = (MyDeleteOneLiveMessageRequest)JSONObject.toBean(data, MyDeleteOneLiveMessageRequest.class);
				resultflag = true;
				
				//非空校验
				for (Field field : this.myDeleteOneLiveMessageRequest.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					if(field.get(this.myDeleteOneLiveMessageRequest)==null || "".equals(field.get(this.myDeleteOneLiveMessageRequest))){
						resultflag = false;
						break;
					}
				}	
			}
		} catch (Exception e) {
			logger.error("我的-删除某一条现场消息接口checkBizData错误："+e.getMessage());
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
			
			MyDeleteOneLiveMessageResponse myDeleteOneLiveMessageResponse=new MyDeleteOneLiveMessageResponse();
			
			myDeleteOneLiveMessageResponse.setDelSuccess(myOptionDAO.deleteLiveMessage(this.myDeleteOneLiveMessageRequest.getUserId(), this.myDeleteOneLiveMessageRequest.getLmId()));

			result=responseBodyResultUtil.getSuccess_result(myDeleteOneLiveMessageResponse==null?new Object():myDeleteOneLiveMessageResponse, "删除成功");
			
		}
		return result;
	}
}
