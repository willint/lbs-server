package com.lbs.nettyserver.service.testservice;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.NullLiteral;
import org.springframework.stereotype.Service;

import com.lbs.nettyserver.dao.CitiesDAO;
import com.lbs.nettyserver.model.test.Cities;
import com.lbs.nettyserver.service.impl.CommonBizService;
import com.lbs.nettyserver.utils.functionutils.ResponseBodyResultUtil;



@Service("citiesService")
public class CitiesService implements CommonBizService{
	
	@Autowired
	private CitiesDAO citiesDAO;
	
	public Cities cities;
	
	public void storeCities(Cities e){
		citiesDAO.storeCities(e);
	}

	@Override
	public boolean checkBizData(JSONObject data) {
		boolean resultflag = false;
		try {
			if(null != data){
				cities = (Cities)JSONObject.toBean(data, Cities.class);
				resultflag = true;
			}
		} catch (Exception e) {
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
			if(null != cities){
				storeCities(cities);
				result=responseBodyResultUtil.getSuccess_result(null, "插入数据成功");
				
			}
		}
		return result;
	}

}
