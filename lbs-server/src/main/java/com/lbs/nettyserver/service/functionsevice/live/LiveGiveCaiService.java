package com.lbs.nettyserver.service.functionsevice.live;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbs.nettyserver.dao.live.LiveCaiDAO;
import com.lbs.nettyserver.dao.live.LiveZanDAO;
import com.lbs.nettyserver.model.pojo.LiveCai;
import com.lbs.nettyserver.model.request.live.LiveGiveCaiRequest;
import com.lbs.nettyserver.service.impl.CommonBizService;
import com.lbs.nettyserver.utils.functionutils.ResponseBodyResultUtil;
import com.lbs.nettyserver.utils.sysutils.TimeUtil;
import com.lbs.nettyserver.utils.sysutils.UUIDUtil;

import net.sf.json.JSONObject;

/**
 * 现场-点踩接口
 * @author visual
 *
 */
@Service("liveGiveCaiService")
public class LiveGiveCaiService implements CommonBizService {
	
	private static final Log logger = LogFactory.getLog(LiveGiveCaiService.class);
	
	@Autowired
	private LiveCaiDAO liveCaiDAO;
	
	@Autowired
	private LiveZanDAO liveZanDAO;
	
	private LiveGiveCaiRequest liveGiveCaiRequest;

	@Override
	public boolean checkBizData(JSONObject data) {
		boolean resultflag = false;
		try {
			if(data != null){
				this.liveGiveCaiRequest = (LiveGiveCaiRequest)JSONObject.toBean(data, LiveGiveCaiRequest.class);
				resultflag = true;
				
				//非空校验
				for (Field field : this.liveGiveCaiRequest.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					if(field.get(this.liveGiveCaiRequest)==null || "".equals(field.get(this.liveGiveCaiRequest))){
						resultflag = false;
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.error("现场-点踩接口checkBizData错误："+e.getMessage());
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
        	if(liveZanDAO.getLiveZanBylmIdAndUserId(this.liveGiveCaiRequest.getLmId(), this.liveGiveCaiRequest.getUserId())!=null){
        		return result=responseBodyResultUtil.getParam_error_result("赞过不能踩了哦");
        	}
        	
        	if(liveCaiDAO.getLiveCaiBylmIdAndUserId(this.liveGiveCaiRequest.getLmId(), this.liveGiveCaiRequest.getUserId())==null){
        		LiveCai liveCai=new LiveCai();
        		BeanUtils.copyProperties(this.liveGiveCaiRequest,liveCai);
        		liveCai.setCaiId(UUIDUtil.getUUID());
        		liveCai.setCaiTime(TimeUtil.getChinaLocalDateTimeNow());
        		liveCaiDAO.saveLiveCai(liveCai);
        		result=responseBodyResultUtil.getSuccess_result(null, "踩成功");

        	}else{
        		
        		result=responseBodyResultUtil.getParam_error_result("已经踩过了");
			
		    }
        }
		return result;

	}
}
