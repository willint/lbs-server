package com.lbs.nettyserver.service.functionsevice.vomit;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbs.nettyserver.dao.vomit.SpecialDAO;
import com.lbs.nettyserver.model.request.vomit.VomitSpecialGetJoinedSpecialRequest;
import com.lbs.nettyserver.model.response.vomit.VomitSpecialGetJoinedSpecialResponse;
import com.lbs.nettyserver.service.impl.CommonBizService;
import com.lbs.nettyserver.utils.functionutils.ResponseBodyResultUtil;

import net.sf.json.JSONObject;

/**
 * 吐槽-专题-获取已加入的专题接口
 * @author visual
 *
 */
@Service("vomitSpecialGetJoinedSpecialService")
public class VomitSpecialGetJoinedSpecialService implements CommonBizService {

	private static final Log logger = LogFactory.getLog(VomitSpecialGetJoinedSpecialService.class);
	
	@Autowired
	private SpecialDAO specialDAO;
	
	private VomitSpecialGetJoinedSpecialRequest vomitSpecialGetJoinedSpecialRequest;

	@Override
	public boolean checkBizData(JSONObject data) {
		boolean resultflag = false;
		try {
			if(data != null){
				this.vomitSpecialGetJoinedSpecialRequest = (VomitSpecialGetJoinedSpecialRequest)JSONObject.toBean(data, VomitSpecialGetJoinedSpecialRequest.class);
				resultflag = true;
				
				//非空校验
				for (Field field : this.vomitSpecialGetJoinedSpecialRequest.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					if(field.get(this.vomitSpecialGetJoinedSpecialRequest)==null || "".equals(field.get(this.vomitSpecialGetJoinedSpecialRequest))){
						resultflag = false;
						break;
					}
				}
			}
		} catch (Exception e) {
			resultflag = false;
			logger.error("吐槽-专题-获取已加入的专题接口checkBizData错误："+e.getMessage());
		}
		return resultflag;
	}

	@Override
	public JSONObject handleBiz(JSONObject data) {
		JSONObject result = new JSONObject();
	    ResponseBodyResultUtil responseBodyResultUtil=new ResponseBodyResultUtil();
		result=responseBodyResultUtil.getParam_error_result("获取失败");
		
        if(checkBizData(data)){
        	
        	List<VomitSpecialGetJoinedSpecialResponse> list=new ArrayList<VomitSpecialGetJoinedSpecialResponse>();
        	list=specialDAO.getVomitJoinedSpecial(this.vomitSpecialGetJoinedSpecialRequest);
        	result=responseBodyResultUtil.getSuccess_result(list==null?new Object[0]:list, "获取成功");
		}
		return result;

	}
}
