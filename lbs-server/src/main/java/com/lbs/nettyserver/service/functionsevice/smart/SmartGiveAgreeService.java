package com.lbs.nettyserver.service.functionsevice.smart;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbs.nettyserver.dao.smart.SmartOptionDAO;
import com.lbs.nettyserver.model.pojo.SmartAgree;
import com.lbs.nettyserver.model.request.smart.SmartGiveAgreeRequest;
import com.lbs.nettyserver.service.impl.CommonBizService;
import com.lbs.nettyserver.utils.functionutils.ResponseBodyResultUtil;
import com.lbs.nettyserver.utils.sysutils.TimeUtil;
import com.lbs.nettyserver.utils.sysutils.UUIDUtil;

import net.sf.json.JSONObject;

/**
 * 智囊-点击同意接口
 * @author visual
 *
 */
@Service("smartGiveAgreeService")
public class SmartGiveAgreeService implements CommonBizService  {


	private static final Log logger = LogFactory.getLog(SmartGiveAgreeService.class);
	
	@Autowired
	private SmartOptionDAO smartOptionDAO;

	private SmartGiveAgreeRequest smartGiveAgreeRequest;

	@Override
	public boolean checkBizData(JSONObject data) {
		boolean resultflag = false;
		try {
			if(data != null){
				this.smartGiveAgreeRequest = (SmartGiveAgreeRequest)JSONObject.toBean(data, SmartGiveAgreeRequest.class);
				resultflag = true;
				
				//非空校验
				for (Field field : this.smartGiveAgreeRequest.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					if(field.get(this.smartGiveAgreeRequest)==null || "".equals(field.get(this.smartGiveAgreeRequest))){
						resultflag = false;
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.error("智囊-点击同意接口checkBizData错误："+e.getMessage());
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
        	
        	//查询用户是否已经反对过
        	if(smartOptionDAO.getSmartAgainstBySmIdAndUserId(this.smartGiveAgreeRequest.getSmId(), this.smartGiveAgreeRequest.getUserId())!=null){
        		return result=responseBodyResultUtil.getParam_error_result("你要坚持自己反对过的态度");
        	}
        	
        	//查询是否已经同意过
        	if(smartOptionDAO.getSmartAgreeBySmIdAndUserId(this.smartGiveAgreeRequest.getSmId(), this.smartGiveAgreeRequest.getUserId())==null){
        		
        		SmartAgree smartAgree=new SmartAgree();
        		BeanUtils.copyProperties(this.smartGiveAgreeRequest,smartAgree);
        		
        		smartAgree.setSaId(UUIDUtil.getUUID());
        		smartAgree.setAgreeTime(TimeUtil.getChinaLocalDateTimeNow());
        		
        		smartOptionDAO.saveSmartAgree(smartAgree);
        		
        		result=responseBodyResultUtil.getSuccess_result(null, "成功");

        	}else{
        		
        		result=responseBodyResultUtil.getParam_error_result("已经同意过了");
			
		    }
        	
        	
        }
		return result;

	}
	
}
