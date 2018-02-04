package com.lbs.nettyserver.service.functionsevice.my;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbs.nettyserver.dao.my.MyOptionDAO;
import com.lbs.nettyserver.model.request.my.MyGetGetCommentListRequest;
import com.lbs.nettyserver.model.response.my.MyGetGetCommentListResponse;
import com.lbs.nettyserver.service.impl.CommonBizService;
import com.lbs.nettyserver.utils.functionutils.ResponseBodyResultUtil;

import net.sf.json.JSONObject;

/**
 * 我的-获取现场我得到的评论列表接口
 * @author visual
 *
 */
@Service("myGetGetCommentListService")
public class MyGetGetCommentListService implements CommonBizService {


	private static final Log logger = LogFactory.getLog(MyGetGetCommentListService.class);
	
	@Autowired
	private MyOptionDAO myOptionDAO;
	
	private MyGetGetCommentListRequest myGetGetCommentListRequest;

	@Override
	public boolean checkBizData(JSONObject data) {
		boolean resultflag = false;
		try {
			
			if(data != null){
				this.myGetGetCommentListRequest = (MyGetGetCommentListRequest)JSONObject.toBean(data, MyGetGetCommentListRequest.class);
				resultflag = true;
				
				//非空校验
				for (Field field : this.myGetGetCommentListRequest.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					if(field.get(this.myGetGetCommentListRequest)==null || "".equals(field.get(this.myGetGetCommentListRequest))){
						resultflag = false;
						break;
					}
				}	
			}
		} catch (Exception e) {
			logger.error("我的-获取现场我得到的评论列表接口checkBizData错误："+e.getMessage());
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
			
			List<MyGetGetCommentListResponse> list=new ArrayList<MyGetGetCommentListResponse>();
			
			list=myOptionDAO.getOneUserGetCommentList(this.myGetGetCommentListRequest);
			result=responseBodyResultUtil.getSuccess_result(list==null?new Object[0]:list, "获取成功");
			
		}
		return result;
	}
}
