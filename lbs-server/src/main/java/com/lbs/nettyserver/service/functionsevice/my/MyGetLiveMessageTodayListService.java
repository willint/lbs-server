package com.lbs.nettyserver.service.functionsevice.my;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbs.nettyserver.dao.my.MyOptionDAO;
import com.lbs.nettyserver.model.request.my.MyGetLiveMessageTodayListRequest;
import com.lbs.nettyserver.model.response.my.MyGetLiveMessageTodayListResponse;
import com.lbs.nettyserver.service.impl.CommonBizService;
import com.lbs.nettyserver.utils.functionutils.ResponseBodyResultUtil;

import net.sf.json.JSONObject;

/**
 * 我的-获取用户今天发布的现场消息列表接口
 * @author visual
 *
 */
@Service("myGetLiveMessageTodayListService")
public class MyGetLiveMessageTodayListService implements CommonBizService {


	private static final Log logger = LogFactory.getLog(MyGetLiveMessageTodayListService.class);
	
	@Autowired
	private MyOptionDAO myOptionDAO;
	
	private MyGetLiveMessageTodayListRequest myGetLiveMessageTodayListRequest;

	@Override
	public boolean checkBizData(JSONObject data) {
		boolean resultflag = false;
		try {
			
			if(data != null){
				this.myGetLiveMessageTodayListRequest = (MyGetLiveMessageTodayListRequest)JSONObject.toBean(data, MyGetLiveMessageTodayListRequest.class);
				resultflag = true;
				
				//非空校验
				for (Field field : this.myGetLiveMessageTodayListRequest.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					if(field.get(this.myGetLiveMessageTodayListRequest)==null || "".equals(field.get(this.myGetLiveMessageTodayListRequest))){
						resultflag = false;
						break;
					}
				}	
			}
		} catch (Exception e) {
			logger.error("我的-获取用户今天发布的现场消息列表接口checkBizData错误："+e.getMessage());
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
			
			List<MyGetLiveMessageTodayListResponse> list=new ArrayList<MyGetLiveMessageTodayListResponse>();
			
			list=myOptionDAO.getOneUserLiveMessageTodayList(this.myGetLiveMessageTodayListRequest);
			result=responseBodyResultUtil.getSuccess_result(list==null?new Object[0]:list, "获取成功");
			
		}
		return result;
	}
	
}
