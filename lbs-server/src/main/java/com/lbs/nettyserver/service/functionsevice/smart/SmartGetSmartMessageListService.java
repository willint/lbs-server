package com.lbs.nettyserver.service.functionsevice.smart;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbs.nettyserver.dao.smart.SmartOptionDAO;
import com.lbs.nettyserver.model.common.SmartOneUserIsAgainstAndAgree;
import com.lbs.nettyserver.model.request.smart.SmartGetSmartMessageListRequest;
import com.lbs.nettyserver.model.response.smart.SmartGetSmartMessageListResponse;
import com.lbs.nettyserver.service.impl.CommonBizService;
import com.lbs.nettyserver.utils.functionutils.ResponseBodyResultUtil;

import net.sf.json.JSONObject;

/**
 * 智囊-获取智囊消息列表接口
 * @author visual
 *
 */
@Service("smartGetSmartMessageListService")
public class SmartGetSmartMessageListService implements CommonBizService {


	private static final Log logger = LogFactory.getLog(SmartGetSmartMessageListService.class);
	
	@Autowired
	private SmartOptionDAO smartOptionDAO;
	
	private SmartGetSmartMessageListRequest smartGetSmartMessageListRequest;

	@Override
	public boolean checkBizData(JSONObject data) {
		boolean resultflag = false;
		try {
			if(data != null){
				this.smartGetSmartMessageListRequest = (SmartGetSmartMessageListRequest)JSONObject.toBean(data, SmartGetSmartMessageListRequest.class);
				resultflag = true;
				
				//非空校验
				for (Field field : this.smartGetSmartMessageListRequest.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					if(field.get(this.smartGetSmartMessageListRequest)==null || "".equals(field.get(this.smartGetSmartMessageListRequest))){
						resultflag = false;
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.error("智囊-获取智囊消息列表接口checkBizData错误："+e.getMessage());
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
        	List<SmartGetSmartMessageListResponse> list=new ArrayList<SmartGetSmartMessageListResponse>();
			list=smartOptionDAO.getSmartMessageListByJdAndWd(this.smartGetSmartMessageListRequest);
			if(list!=null){
				for (SmartGetSmartMessageListResponse lmr : list) {
					//查询每一消息的反对和同意情况
					SmartOneUserIsAgainstAndAgree smartOneUserIsAgainstAndAgree=smartOptionDAO.checkOneUserIsAgainstOrAgree(this.smartGetSmartMessageListRequest.getUserId(), lmr.getSmId());
					if(smartOneUserIsAgainstAndAgree!=null){
						lmr.setIsAgainst(smartOneUserIsAgainstAndAgree.getIsAgainst());
						lmr.setIsAgree(smartOneUserIsAgainstAndAgree.getIsAgree());
					}
				}
			}
			result=responseBodyResultUtil.getSuccess_result(list==null?new Object[0]:list, "获取成功");
		}
		return result;

	}
	
}
