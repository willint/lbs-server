package com.lbs.nettyserver.service.functionsevice.vomit;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbs.nettyserver.dao.vomit.SpecialDAO;
import com.lbs.nettyserver.dao.vomit.SpecialUserDAO;
import com.lbs.nettyserver.model.pojo.Special;
import com.lbs.nettyserver.model.pojo.SpecialUser;
import com.lbs.nettyserver.model.request.vomit.VomitSpecialCreateSpecialRequest;
import com.lbs.nettyserver.model.response.vomit.VomitSpecialCreateSpecialResponse;
import com.lbs.nettyserver.service.impl.CommonBizService;
import com.lbs.nettyserver.utils.functionutils.ResponseBodyResultUtil;
import com.lbs.nettyserver.utils.sysutils.TimeUtil;
import com.lbs.nettyserver.utils.sysutils.UUIDUtil;

import net.sf.json.JSONObject;

/**
 * 创建吐槽专题接口
 * @author visual
 *
 */
@Service("vomitSpecialCreateSpecialService")
public class VomitSpecialCreateSpecialService implements CommonBizService {
	
	private static final Log logger = LogFactory.getLog(VomitSpecialCreateSpecialService.class);
	
	@Autowired
	private SpecialDAO specialDAO;
	
	@Autowired
	private SpecialUserDAO specialUserDAO;
	
	private VomitSpecialCreateSpecialRequest vomitCreateSpecialRequest;

	@Override
	public boolean checkBizData(JSONObject data) {
		boolean resultflag = false;
		try {
			if(data != null){
				this.vomitCreateSpecialRequest = (VomitSpecialCreateSpecialRequest)JSONObject.toBean(data, VomitSpecialCreateSpecialRequest.class);
				resultflag = true;
				//非空校验
				for (Field field : this.vomitCreateSpecialRequest.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					if(field.get(this.vomitCreateSpecialRequest)==null || "".equals(field.get(this.vomitCreateSpecialRequest))){
						resultflag = false;
					}
				}	
			}
		} catch (Exception e) {
			logger.error("吐槽-创建吐槽专题接口checkBizData错误："+e.getMessage());
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
				Special special=new Special();
				
				//赋值到专题实体保存
				BeanUtils.copyProperties(this.vomitCreateSpecialRequest, special);
				special.setSpecialId(UUIDUtil.getUUID());
				special.setCreateTime(TimeUtil.getChinaLocalDateTimeNow());
				specialDAO.saveSpecial(special);
				
				//将创建人加入专题成员
				SpecialUser specialUser=new SpecialUser();
				specialUser.setSuId(UUIDUtil.getUUID());
				specialUser.setSpecialId(special.getSpecialId());
				specialUser.setUserId(this.vomitCreateSpecialRequest.getCreateUserid());
				specialUser.setJd(this.vomitCreateSpecialRequest.getJd());
				specialUser.setWd(this.vomitCreateSpecialRequest.getWd());
				specialUser.setJoinTime(special.getCreateTime());
				specialUserDAO.saveSpecialUser(specialUser);
				
				//返回专题信息
				VomitSpecialCreateSpecialResponse vomitCreateSpecialResponse=new VomitSpecialCreateSpecialResponse();
				BeanUtils.copyProperties(this.vomitCreateSpecialRequest,vomitCreateSpecialResponse);
				vomitCreateSpecialResponse.setSpecialId(special.getSpecialId());
				vomitCreateSpecialResponse.setCreateTime(TimeUtil.dateTimeFormatToString(special.getCreateTime()));
				
				result=responseBodyResultUtil.getSuccess_result(vomitCreateSpecialResponse, "创建成功");
				
			}catch(Exception e){
				e.printStackTrace();
				logger.error("吐槽-创建吐槽专题接口handleBiz错误："+e.getMessage());
				result=responseBodyResultUtil.getSys_error_default_result();
			}
		}
		return result;

	}
}
