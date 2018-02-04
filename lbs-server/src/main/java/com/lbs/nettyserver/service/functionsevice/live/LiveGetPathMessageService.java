package com.lbs.nettyserver.service.functionsevice.live;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbs.nettyserver.dao.live.LiveMessageDAO;
import com.lbs.nettyserver.model.common.Line;
import com.lbs.nettyserver.model.common.LiveMessageZanCaiTotal;
import com.lbs.nettyserver.model.request.live.LiveGetPathMessageRequest;
import com.lbs.nettyserver.model.response.live.LiveGetPathMessageResponse;
import com.lbs.nettyserver.service.impl.CommonBizService;
import com.lbs.nettyserver.utils.functionutils.ResponseBodyResultUtil;
import com.lbs.nettyserver.utils.sysutils.LinearFittinUtil;
import com.lbs.nettyserver.utils.sysutils.PotinToLineDistanceUtil;

import net.sf.json.JSONObject;

/**
 * 现场-获取现场轨迹消息
 * @author visual
 *
 */
@Service("liveGetPathMessageService")
public class LiveGetPathMessageService implements CommonBizService {
	

	private static final Log logger = LogFactory.getLog(LiveGetPathMessageService.class);
	
	@Autowired
	private LiveMessageDAO liveMessageDAO;
	
	private LiveGetPathMessageRequest liveGetPathMessageRequest;
	
	/**
	 * 点到直线的有效距离，单位米
	 */
	private static final double inDistance=50.00;
	

	@Override
	public boolean checkBizData(JSONObject data) {
		boolean resultflag = false;
		try {
			if(data != null){
				this.liveGetPathMessageRequest = (LiveGetPathMessageRequest)JSONObject.toBean(data, LiveGetPathMessageRequest.class);
				resultflag = true;
				
				//非空校验
				for (Field field : this.liveGetPathMessageRequest.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					if(field.getName()=="pathWdArray" || field.getName()=="pathJdArray"){
						if(field.get(this.liveGetPathMessageRequest)==null){
							resultflag = false;
							break;
						}
					}
					if(field.get(this.liveGetPathMessageRequest)==null || "".equals(field.get(this.liveGetPathMessageRequest))){
						resultflag = false;
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.error("现场-获取现场轨迹消息接口checkBizData错误："+e.getMessage());
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
        	
        	if(this.liveGetPathMessageRequest.getPathJdArray().length<=0 || this.liveGetPathMessageRequest.getPathWdArray().length<=0 || this.liveGetPathMessageRequest.getPathWdArray().length!=this.liveGetPathMessageRequest.getPathJdArray().length){
        		return result;
        	}
        	
        	
        	List<LiveGetPathMessageResponse> list=new ArrayList<LiveGetPathMessageResponse>();
			list=liveMessageDAO.getPathLiveMessage(this.liveGetPathMessageRequest);
			if(list!=null){
				
				//拟合直线通过大部分点
				double ax= LinearFittinUtil.getA(this.liveGetPathMessageRequest.getPathJdArray(),this.liveGetPathMessageRequest.getPathWdArray());
				double b= LinearFittinUtil.getB(this.liveGetPathMessageRequest.getPathJdArray(),this.liveGetPathMessageRequest.getPathWdArray(), ax);
				Line line=LinearFittinUtil.lineParamTransform(ax, b);
				
				//计算点到线的距离
				for (int i=0;i<list.size();i++){
					if(PotinToLineDistanceUtil.getDistance(line, list.get(i).getJd().doubleValue(),list.get(i).getWd().doubleValue())>inDistance){
						list.remove(i);
						i--;
					}
				}
				
				if(list.size()>0){
					//查询每一消息的赞踩情况
					for (LiveGetPathMessageResponse lmr : list) {
						LiveMessageZanCaiTotal liveMessageZanCaiTotal=liveMessageDAO.getLiveMessageZanAnCaiTotal(lmr.getLmId(), this.liveGetPathMessageRequest.getUserId());
						if(liveMessageZanCaiTotal!=null){
							lmr.setCaiTotal(liveMessageZanCaiTotal.getCaiTotal());
							lmr.setIsCai(liveMessageZanCaiTotal.getIsCai());
							lmr.setZanTotal(liveMessageZanCaiTotal.getZanTotal());
							lmr.setIsZan(liveMessageZanCaiTotal.getIsZan());
						}
					}
				}
				
			}
			result=responseBodyResultUtil.getSuccess_result(list==null || list.size()==0?new Object[0]:list, "获取成功");
		}
		return result;

	}

}
