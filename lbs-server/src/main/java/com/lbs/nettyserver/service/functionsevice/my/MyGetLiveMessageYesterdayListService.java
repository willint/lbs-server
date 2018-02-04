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
import com.lbs.nettyserver.model.request.my.MyGetLiveMessageYesterdayListResquest;
import com.lbs.nettyserver.model.response.my.MyGetLiveMessageTodayListResponse;
import com.lbs.nettyserver.model.response.my.MyGetLiveMessageYesterdayListResponse;
import com.lbs.nettyserver.service.impl.CommonBizService;
import com.lbs.nettyserver.utils.functionutils.ResponseBodyResultUtil;

import net.sf.json.JSONObject;

/**
 * 我的-获取我昨天发布的现场消息列表接口
 * @author visual
 *
 */
@Service("myGetLiveMessageYesterdayListService")
public class MyGetLiveMessageYesterdayListService implements CommonBizService {


	private static final Log logger = LogFactory.getLog(MyGetLiveMessageYesterdayListService.class);
	
	@Autowired
	private MyOptionDAO myOptionDAO;
	
	private MyGetLiveMessageYesterdayListResquest myGetLiveMessageYesterdayListResquest;

	@Override
	public boolean checkBizData(JSONObject data) {
		boolean resultflag = false;
		try {
			
			if(data != null){
				this.myGetLiveMessageYesterdayListResquest = (MyGetLiveMessageYesterdayListResquest)JSONObject.toBean(data, MyGetLiveMessageYesterdayListResquest.class);
				resultflag = true;
				
				//非空校验
				for (Field field : this.myGetLiveMessageYesterdayListResquest.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					if(field.get(this.myGetLiveMessageYesterdayListResquest)==null || "".equals(field.get(this.myGetLiveMessageYesterdayListResquest))){
						resultflag = false;
						break;
					}
				}	
			}
		} catch (Exception e) {
			logger.error("我的-获取我昨天发布的现场消息列表接口checkBizData错误："+e.getMessage());
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
			
			List<MyGetLiveMessageYesterdayListResponse> list=new ArrayList<MyGetLiveMessageYesterdayListResponse>();
			
			list=myOptionDAO.getOneUserLiveMessageYesterdayList(this.myGetLiveMessageYesterdayListResquest);
			result=responseBodyResultUtil.getSuccess_result(list==null?new Object[0]:list, "获取成功");
			
		}
		return result;
	}
}
