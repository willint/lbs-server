package com.lbs.nettyserver.service.functionsevice.smart;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbs.nettyserver.dao.smart.SmartOptionDAO;
import com.lbs.nettyserver.model.request.smart.SmartGetOneMessageCommentListRequest;
import com.lbs.nettyserver.model.response.smart.SmartGetOneMessageCommentListResponse;
import com.lbs.nettyserver.service.impl.CommonBizService;
import com.lbs.nettyserver.utils.functionutils.ResponseBodyResultUtil;

import net.sf.json.JSONObject;

/**
 * 智囊-获取某一条智囊消息评论列表接口
 * @author visual
 *
 */
@Service("smartGetOneMessageCommentListService")
public class SmartGetOneMessageCommentListService implements CommonBizService {


	private static final Log logger = LogFactory.getLog(SmartGetOneMessageCommentListService.class);
	
	@Autowired
	private SmartOptionDAO smartOptionDAO;
	
	private SmartGetOneMessageCommentListRequest smartGetOneMessageCommentListRequest;

	@Override
	public boolean checkBizData(JSONObject data) {
		boolean resultflag = false;
		try {
			if(data != null){
				this.smartGetOneMessageCommentListRequest = (SmartGetOneMessageCommentListRequest)JSONObject.toBean(data, SmartGetOneMessageCommentListRequest.class);
				resultflag = true;
				
				//非空校验
				for (Field field : this.smartGetOneMessageCommentListRequest.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					if(field.get(this.smartGetOneMessageCommentListRequest)==null || "".equals(field.get(this.smartGetOneMessageCommentListRequest))){
						resultflag = false;
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.error("智囊-获取某一条智囊消息评论列表接口checkBizData错误："+e.getMessage());
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
        	List<SmartGetOneMessageCommentListResponse> list=new ArrayList<SmartGetOneMessageCommentListResponse>();
			list=smartOptionDAO.getOneMessageCommentListBySmId(this.smartGetOneMessageCommentListRequest);
			result=responseBodyResultUtil.getSuccess_result(list==null?new Object[0]:list, "获取成功");
		}
		return result;

	}
}
