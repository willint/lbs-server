package com.lbs.nettyserver.service.functionsevice.smart;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbs.nettyserver.dao.smart.SmartOptionDAO;
import com.lbs.nettyserver.model.pojo.SmartAgainst;
import com.lbs.nettyserver.model.request.smart.SmartGiveAgainstRequest;
import com.lbs.nettyserver.service.impl.CommonBizService;
import com.lbs.nettyserver.utils.functionutils.ResponseBodyResultUtil;
import com.lbs.nettyserver.utils.sysutils.TimeUtil;
import com.lbs.nettyserver.utils.sysutils.UUIDUtil;

import net.sf.json.JSONObject;

/**
 * 智囊-点击反对接口
 * @author visual
 *
 */
@Service("smartGiveAgainstService")
public class SmartGiveAgainstService implements CommonBizService {


	private static final Log logger = LogFactory.getLog(SmartGiveAgainstService.class);
	
	@Autowired
	private SmartOptionDAO smartOptionDAO;

	private SmartGiveAgainstRequest smartGiveAgainstRequest;

	@Override
	public boolean checkBizData(JSONObject data) {
		boolean resultflag = false;
		try {
			if(data != null){
				this.smartGiveAgainstRequest = (SmartGiveAgainstRequest)JSONObject.toBean(data, SmartGiveAgainstRequest.class);
				resultflag = true;
				
				//非空校验
				for (Field field : this.smartGiveAgainstRequest.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					if(field.get(this.smartGiveAgainstRequest)==null || "".equals(field.get(this.smartGiveAgainstRequest))){
						resultflag = false;
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.error("智囊-点击反对接口checkBizData错误："+e.getMessage());
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
        	
        	//查询用户是否已经同意过
        	if(smartOptionDAO.getSmartAgreeBySmIdAndUserId(this.smartGiveAgainstRequest.getSmId(), this.smartGiveAgainstRequest.getUserId())!=null){
        		return result=responseBodyResultUtil.getParam_error_result("你要坚持自己同意过的态度");
        	}
        	
        	//查询是否已经反对过
        	if(smartOptionDAO.getSmartAgainstBySmIdAndUserId(this.smartGiveAgainstRequest.getSmId(), this.smartGiveAgainstRequest.getUserId())==null){
        		
        		SmartAgainst smartAgainst=new SmartAgainst();
        		BeanUtils.copyProperties(this.smartGiveAgainstRequest,smartAgainst);
        		
        		smartAgainst.setSagId(UUIDUtil.getUUID());
        		smartAgainst.setAgainstTime(TimeUtil.getChinaLocalDateTimeNow());
        		
        		smartOptionDAO.saveSmartAgainst(smartAgainst);
        		
        		result=responseBodyResultUtil.getSuccess_result(null, "成功");

        	}else{
        		
        		result=responseBodyResultUtil.getParam_error_result("已经反对过了");
			
		    }
        	
        	
        }
		return result;

	}
}
