package com.lbs.nettyserver.service.functionsevice.vomit;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbs.nettyserver.dao.vomit.SpecialDAO;
import com.lbs.nettyserver.model.request.vomit.VomitSpecialGetCreatedSpecialRequest;
import com.lbs.nettyserver.model.response.vomit.VomitSpecialGetCreatedSpecialResponse;
import com.lbs.nettyserver.service.impl.CommonBizService;
import com.lbs.nettyserver.utils.functionutils.ResponseBodyResultUtil;

import net.sf.json.JSONObject;

/**
 * 吐槽-专题-获取已创建的专题接口
 * @author visual
 *
 */
@Service("vomitSpecialGetCreatedSpecialService")
public class VomitSpecialGetCreatedSpecialService implements CommonBizService {

private static final Log logger = LogFactory.getLog(VomitSpecialGetCreatedSpecialService.class);
	
    @Autowired
    private SpecialDAO specialDAO;
	
	private VomitSpecialGetCreatedSpecialRequest vomitSpecialGetCreatedSpecialRequest;

	@Override
	public boolean checkBizData(JSONObject data) {
		boolean resultflag = false;
		try {
			if(data != null){
				this.vomitSpecialGetCreatedSpecialRequest = (VomitSpecialGetCreatedSpecialRequest)JSONObject.toBean(data, VomitSpecialGetCreatedSpecialRequest.class);
				resultflag = true;
				
				//非空校验
				for (Field field : this.vomitSpecialGetCreatedSpecialRequest.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					if(field.get(this.vomitSpecialGetCreatedSpecialRequest)==null || "".equals(field.get(this.vomitSpecialGetCreatedSpecialRequest))){
						resultflag = false;
						break;
					}
				}
			}
		} catch (Exception e) {
			resultflag = false;
			logger.error("吐槽-专题-获取已创建的专题接口checkBizData错误："+e.getMessage());
		}
		return resultflag;
	}

	@Override
	public JSONObject handleBiz(JSONObject data) {
		JSONObject result = new JSONObject();
	    ResponseBodyResultUtil responseBodyResultUtil=new ResponseBodyResultUtil();
		result=responseBodyResultUtil.getParam_error_result("获取失败");
		
        if(checkBizData(data)){
        	
        	List<VomitSpecialGetCreatedSpecialResponse> list=new ArrayList<VomitSpecialGetCreatedSpecialResponse>();
        	list=specialDAO.getVomitCreatedSpecial(this.vomitSpecialGetCreatedSpecialRequest);
        	result=responseBodyResultUtil.getSuccess_result(list==null?new Object[0]:list, "获取成功");
		}
		return result;

	}
}
