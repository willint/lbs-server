package com.lbs.nettyserver.service.sysservice;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.lbs.nettyserver.dao.sys.SystemParamDAO;
import com.lbs.nettyserver.model.pojo.SystemParam;
import com.lbs.nettyserver.model.test.Cities;
import com.lbs.nettyserver.service.impl.CommonBizService;
import com.lbs.nettyserver.utils.functionutils.ResponseBodyResultUtil;
import com.lbs.nettyserver.utils.sysutils.JsonPluginsUtil;



@Service("systemParamService")
public class SystemParamService implements CommonBizService{
	
	@Autowired
	private SystemParamDAO systemParamDAO;
	
	public Cities cities;
	

	@Override
	public boolean checkBizData(JSONObject data) {
		boolean resultflag = false;
		return resultflag;
	}

	@Override
	public JSONObject handleBiz(JSONObject data) {
		JSONObject result = new JSONObject();
		ResponseBodyResultUtil responseBodyResultUtil=new ResponseBodyResultUtil();
		if(null == data){
			result = responseBodyResultUtil.getParam_error_result("请求参数不能为空");
		}else{
			//判断type类型
			String type = data.getString("type");
			String paramName =  data.getString("paramName");
			if("0".equals(type)){
				List<SystemParam> sysParams = getSystemParamList();
				JSONArray arr = JSONArray.fromObject(JsonPluginsUtil.beanListToJson(sysParams));
				
				result=responseBodyResultUtil.getSuccess_result(arr, "参数获取成功");
				result.put("size", sysParams.size());
			}else if ("1".equals(type)){
				if(StringUtils.isEmpty(paramName)){
					result = responseBodyResultUtil.getParam_error_result("参数名paramName为空");
					result.put("size", 0);
				}else{
					SystemParam sysParam = getSystemParamByName(paramName);
					
					if(sysParam != null){
						JSONArray arr = JSONArray.fromObject(JSONObject.fromObject(sysParam));
						result=responseBodyResultUtil.getSuccess_result(arr, "参数获取成功");
						result.put("size", 1);
					}else{
						result = responseBodyResultUtil.getParam_error_result("未找到参数名"+paramName+"的系统变量");
						result.put("size", 0);
					}
				}
			}else{
				result = responseBodyResultUtil.getParam_error_result("请求对应参数type系统变量值不存在");
				result.put("size", 0);
			}
		}
		
		return result;
	}
	
	private List<SystemParam> getSystemParamList(){
		return systemParamDAO.getSystemParamList();
	}
	
	private SystemParam getSystemParamByName(String paramName){
		return systemParamDAO.getSystemParamByName(paramName);
	}
}
