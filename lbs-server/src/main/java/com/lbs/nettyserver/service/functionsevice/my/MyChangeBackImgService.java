package com.lbs.nettyserver.service.functionsevice.my;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbs.nettyserver.dao.my.MyOptionDAO;
import com.lbs.nettyserver.model.request.my.MyChangeBackImgRequest;
import com.lbs.nettyserver.service.impl.CommonBizService;
import com.lbs.nettyserver.utils.functionutils.ResponseBodyResultUtil;

import net.sf.json.JSONObject;

/**
 * 用户修改背景图片类
 * @author visual
 *
 */
@Service("changeBackImgService")
public class MyChangeBackImgService implements CommonBizService {

    private static final Log logger = LogFactory.getLog(MyChangeBackImgService.class);
	
	@Autowired
	private MyOptionDAO myOptionDAO;
	
	private MyChangeBackImgRequest changeBackImgRequest;

	@Override
	public boolean checkBizData(JSONObject data) {
		boolean resultflag = false;
		try {
			
			if(data != null){
				this.changeBackImgRequest = (MyChangeBackImgRequest)JSONObject.toBean(data, MyChangeBackImgRequest.class);
				resultflag = true;
				
				//非空校验
				for (Field field : this.changeBackImgRequest.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					if(field.get(this.changeBackImgRequest)==null || "".equals(field.get(this.changeBackImgRequest))){
						resultflag = false;
						break;
					}
				}	
			}
		} catch (Exception e) {
			logger.error("用户修改背景图片接口checkBizData错误："+e.getMessage());
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
			try{//密码解密RSAUtil.serverDecryptSign(changePasswordRequest.getNewPws()))
				updateRow=myOptionDAO.updateBackImgByUserId(this.changeBackImgRequest.getUserId(), this.changeBackImgRequest.getBackImgSrc());
				if(updateRow>0){
					result=responseBodyResultUtil.getSuccess_result(null, "修改成功");
				}
			}catch(Exception e){
				result=responseBodyResultUtil.getSys_error_default_result();
				e.printStackTrace();
				logger.error("背景图片修改接口handleBiz错误："+e.getMessage());
			}
		}
		return result;
	}
}
