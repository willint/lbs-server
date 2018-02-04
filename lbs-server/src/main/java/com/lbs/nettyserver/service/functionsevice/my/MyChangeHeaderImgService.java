package com.lbs.nettyserver.service.functionsevice.my;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbs.nettyserver.dao.my.MyOptionDAO;
import com.lbs.nettyserver.model.request.my.MyChangeHeaderImgRequest;
import com.lbs.nettyserver.service.impl.CommonBizService;
import com.lbs.nettyserver.utils.functionutils.ResponseBodyResultUtil;

import net.sf.json.JSONObject;

/**
 * 用户修改头像接口
 * @author visual
 *
 */
@Service("changeHeaderImgService")
public class MyChangeHeaderImgService implements CommonBizService {
	
	private static final Log logger = LogFactory.getLog(MyChangeHeaderImgService.class);
	
	@Autowired
	private MyOptionDAO myOptionDAO;
	
	private MyChangeHeaderImgRequest changeHeaderImgRequest;

	@Override
	public boolean checkBizData(JSONObject data) {
		boolean resultflag = false;
		try {
			
			if(data != null){
				this.changeHeaderImgRequest = (MyChangeHeaderImgRequest)JSONObject.toBean(data, MyChangeHeaderImgRequest.class);
				resultflag = true;
				
				//非空校验
				for (Field field : this.changeHeaderImgRequest.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					if(field.get(this.changeHeaderImgRequest)==null || "".equals(field.get(this.changeHeaderImgRequest))){
						resultflag = false;
						break;
					}
				}	
			}
		} catch (Exception e) {
			logger.error("用户修改头像接口checkBizData错误："+e.getMessage());
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
			int updateRow=0;
			try{
				updateRow=myOptionDAO.updateHeaderImgByUserId(this.changeHeaderImgRequest.getUserId(), this.changeHeaderImgRequest.getHeaderImgSrc());
				if(updateRow>0){
					result=responseBodyResultUtil.getSuccess_result(null, "头像修改成功");
				}
			}catch(Exception e){
				result=responseBodyResultUtil.getSys_error_default_result();
				e.printStackTrace();
				logger.error("头像修改接口handleBiz错误："+e.getMessage());
			}
		}
		return result;
	}

}
