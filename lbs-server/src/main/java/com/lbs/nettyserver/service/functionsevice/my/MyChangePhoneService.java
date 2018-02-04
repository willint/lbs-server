package com.lbs.nettyserver.service.functionsevice.my;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbs.nettyserver.dao.UserDAO;
import com.lbs.nettyserver.dao.my.MyOptionDAO;
import com.lbs.nettyserver.model.request.my.MyChangePhoneRequest;
import com.lbs.nettyserver.service.impl.CommonBizService;
import com.lbs.nettyserver.utils.functionutils.ResponseBodyResultUtil;
import com.lbs.nettyserver.utils.sysutils.RedisUtil;

import net.sf.json.JSONObject;

/**
 * 用户修改手机号接口
 * @author visual
 *
 */
@Service("myChangePhoneService")
public class MyChangePhoneService implements CommonBizService {

private static final Log logger = LogFactory.getLog(MyChangePhoneService.class);
	

    @Autowired
    private UserDAO userDao;
    
	@Autowired
	private MyOptionDAO myOptionDAO;
	
	@Autowired
	private RedisUtil redisUtil;
	
	private MyChangePhoneRequest myChangePhoneRequest;
	
	/**
	 * 检查验证码是否正确
	 * @param phone
	 * @param checkNum
	 * @return
	 */
	private boolean checkNumIsRight(String phone,String checkNum){
		Object num= redisUtil.get(phone);
		if(num==null || "".equals(num)){
			return false;
		}
		return num.toString().equals(checkNum);
	}

	@Override
	public boolean checkBizData(JSONObject data) {
		boolean resultflag = false;
		try {
			
			if(data != null){
				this.myChangePhoneRequest = (MyChangePhoneRequest)JSONObject.toBean(data, MyChangePhoneRequest.class);
				resultflag = true;
				
				//非空校验
				for (Field field : this.myChangePhoneRequest.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					if(field.get(this.myChangePhoneRequest)==null || "".equals(field.get(this.myChangePhoneRequest))){
						resultflag = false;
						break;
					}
				}	
			}
		} catch (Exception e) {
			logger.error("用户修改手机号接口checkBizData错误："+e.getMessage());
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
				//查询要更换的手机号码是否已被注册了
				if(userDao.getUserByPhone(this.myChangePhoneRequest.getPhone())!=null){
					
					return result=responseBodyResultUtil.getParam_error_result("电话号码已被注册");
				}
				
				if(checkNumIsRight(this.myChangePhoneRequest.getPhone(),this.myChangePhoneRequest.getCheckNum())){
					
					updateRow=myOptionDAO.updatePhoneByUserId(this.myChangePhoneRequest.getUserId(), this.myChangePhoneRequest.getPhone());
					if(updateRow>0){
						result=responseBodyResultUtil.getSuccess_result(null, "修改成功");
					}
					
				}else{
					result=responseBodyResultUtil.getParam_error_result("验证码错误");
				}
				
				
			}catch(Exception e){
				result=responseBodyResultUtil.getSys_error_default_result();
				e.printStackTrace();
				logger.error("用户修改手机号接口handleBiz错误："+e.getMessage());
			}
		}
		return result;
	}
	
}
