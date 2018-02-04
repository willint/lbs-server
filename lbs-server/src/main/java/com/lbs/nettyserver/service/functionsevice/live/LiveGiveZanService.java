package com.lbs.nettyserver.service.functionsevice.live;

import java.lang.reflect.Field;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbs.nettyserver.dao.live.LiveCaiDAO;
import com.lbs.nettyserver.dao.live.LiveZanDAO;
import com.lbs.nettyserver.model.pojo.LiveZan;
import com.lbs.nettyserver.model.request.live.LiveGiveZanRequest;
import com.lbs.nettyserver.service.impl.CommonBizService;
import com.lbs.nettyserver.utils.functionutils.ResponseBodyResultUtil;
import com.lbs.nettyserver.utils.sysutils.TimeUtil;
import com.lbs.nettyserver.utils.sysutils.UUIDUtil;

import net.sf.json.JSONObject;

/**
 * 现场-点赞接口
 * @author visual
 *
 */
@Service("liveGiveZanService")
public class LiveGiveZanService implements CommonBizService {

	private static final Log logger = LogFactory.getLog(LiveGiveZanService.class);
	
	@Autowired
	private LiveZanDAO liveZanDAO;
	
	@Autowired
	private LiveCaiDAO liveCaiDAO;
	
	private LiveGiveZanRequest liveGiveZanRequest;

	@Override
	public boolean checkBizData(JSONObject data) {
		boolean resultflag = false;
		try {
			if(data != null){
				this.liveGiveZanRequest = (LiveGiveZanRequest)JSONObject.toBean(data, LiveGiveZanRequest.class);
				resultflag = true;
				
				//非空校验
				for (Field field : this.liveGiveZanRequest.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					if(field.get(this.liveGiveZanRequest)==null || "".equals(field.get(this.liveGiveZanRequest))){
						resultflag = false;
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.error("现场-点赞接口checkBizData错误："+e.getMessage());
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
        	
        	if(liveCaiDAO.getLiveCaiBylmIdAndUserId(this.liveGiveZanRequest.getLmId(), this.liveGiveZanRequest.getUserId())!=null){
        		return result=responseBodyResultUtil.getParam_error_result("踩过不能赞了哦");
        	}
        	
        	if(liveZanDAO.getLiveZanBylmIdAndUserId(this.liveGiveZanRequest.getLmId(), this.liveGiveZanRequest.getUserId())==null){
        		LiveZan liveZan=new LiveZan();
        		BeanUtils.copyProperties(this.liveGiveZanRequest,liveZan);
        		liveZan.setZanId(UUIDUtil.getUUID());
        		liveZan.setZanTime(TimeUtil.getChinaLocalDateTimeNow());
        		liveZanDAO.saveLiveZan(liveZan);
        		result=responseBodyResultUtil.getSuccess_result(null, "赞成功");

        	}else{
        		
        		result=responseBodyResultUtil.getParam_error_result("已经赞过了");
			
		    }
        }
		return result;

	}
}
