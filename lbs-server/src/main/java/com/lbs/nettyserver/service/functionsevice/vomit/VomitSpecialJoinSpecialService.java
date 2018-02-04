package com.lbs.nettyserver.service.functionsevice.vomit;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbs.nettyserver.dao.vomit.SpecialDAO;
import com.lbs.nettyserver.dao.vomit.SpecialUserDAO;
import com.lbs.nettyserver.model.pojo.SpecialUser;
import com.lbs.nettyserver.model.request.vomit.VomitSpecialJoinSpecialRequest;
import com.lbs.nettyserver.service.impl.CommonBizService;
import com.lbs.nettyserver.utils.functionutils.ResponseBodyResultUtil;
import com.lbs.nettyserver.utils.sysutils.TimeUtil;
import com.lbs.nettyserver.utils.sysutils.UUIDUtil;

import net.sf.json.JSONObject;

/**
 * 吐槽-加入专题接口
 * @author visual
 *
 */
@Service("vomitSpecialJoinSpecialService")
public class VomitSpecialJoinSpecialService implements CommonBizService {

	private static final Log logger = LogFactory.getLog(VomitSpecialJoinSpecialService.class);
	
	@Autowired
	private SpecialUserDAO specialUserDAO;
	@Autowired
	private SpecialDAO specialDAO;
	
	private VomitSpecialJoinSpecialRequest vomitJoinSpecialRequest;

	@Override
	public boolean checkBizData(JSONObject data) {
		boolean resultflag = false;
		try {
			if(data != null){
				this.vomitJoinSpecialRequest = (VomitSpecialJoinSpecialRequest)JSONObject.toBean(data, VomitSpecialJoinSpecialRequest.class);
				resultflag = true;
				//非空校验
				for (Field field : this.vomitJoinSpecialRequest.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					if(field.get(this.vomitJoinSpecialRequest)==null || "".equals(field.get(this.vomitJoinSpecialRequest))){
						resultflag = false;
					}
				}	
			}
		} catch (Exception e) {
			logger.error("吐槽-加入吐槽专题接口checkBizData错误："+e.getMessage());
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
        	try{
        		
        		if(!specialDAO.getSpecialIsOverTime(this.vomitJoinSpecialRequest.getSpecialId())){
					return result=responseBodyResultUtil.getParam_error_result("专题已失效");
				}
        		
        		if(!specialUserDAO.getSpecialUsersIsMax(this.vomitJoinSpecialRequest.getSpecialId())){
					return result=responseBodyResultUtil.getParam_error_result("人员已满");
				}
				
				if(specialUserDAO.getSpecialUserListBySpecialIdAndUserId(this.vomitJoinSpecialRequest.getSpecialId(), this.vomitJoinSpecialRequest.getUserId())!=null){
					return result=responseBodyResultUtil.getParam_error_result("不能重复加入");
				}
						
				SpecialUser specialUser=new SpecialUser();
				//赋值到专题成员实体保存
				BeanUtils.copyProperties(this.vomitJoinSpecialRequest, specialUser);
				specialUser.setSuId(UUIDUtil.getUUID());
				specialUser.setJoinTime(TimeUtil.getChinaLocalDateTimeNow());
				specialUserDAO.saveSpecialUser(specialUser);

				result=responseBodyResultUtil.getSuccess_result(null, "加入成功");
				
			}catch(Exception e){
				e.printStackTrace();
				logger.error("吐槽-加入吐槽专题接口handleBiz错误："+e.getMessage());
				result=responseBodyResultUtil.getSys_error_default_result();
			}
		}
		return result;

	}
}
