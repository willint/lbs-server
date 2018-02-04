package com.lbs.nettyserver.service.functionsevice.find;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbs.nettyserver.dao.find.FindLiveRankDAO;
import com.lbs.nettyserver.model.request.find.FindLiveRankRequest;
import com.lbs.nettyserver.model.response.find.FindLiveRankResponse;
import com.lbs.nettyserver.service.impl.CommonBizService;
import com.lbs.nettyserver.utils.functionutils.ResponseBodyResultUtil;

import net.sf.json.JSONObject;

/**
 * 发现-现场排名接口
 * @author visual
 *
 */
@Service("findLiveRankService")
public class FindLiveRankService implements CommonBizService {
	
	private static final Log logger = LogFactory.getLog(FindLiveRankService.class);
	
	@Autowired
	private FindLiveRankDAO findLiveRankDAO;
	
	private FindLiveRankRequest findLiveRankRequest;

	@Override
	public boolean checkBizData(JSONObject data) {
		boolean resultflag = false;
		try {
			if(data != null){
				this.findLiveRankRequest = (FindLiveRankRequest)JSONObject.toBean(data, FindLiveRankRequest.class);
				resultflag = true;
				
				//非空校验
				for (Field field : this.findLiveRankRequest.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					if(field.get(this.findLiveRankRequest)==null || "".equals(field.get(this.findLiveRankRequest))){
						resultflag = false;
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.error("发现-现场排名接口checkBizData错误："+e.getMessage());
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
        	
        	List<FindLiveRankResponse> list=new ArrayList<FindLiveRankResponse>();
			list=findLiveRankDAO.getFindLiveRankInfo(this.findLiveRankRequest);
			
			result=responseBodyResultUtil.getSuccess_result(null, "获取成功");
			
			if(list!=null){
				result.put("total", list.size());//总人数
				
				list.get(0).setRank(1);//设置排名
				result.put("first", list.get(0));//第一条是第一名
				
				Integer selfRank=0;
				for (FindLiveRankResponse fr : list) {
					selfRank++;
					if(fr.getUserId().equals(this.findLiveRankRequest.getUserId())){
						fr.setRank(selfRank);//设置排名
						result.put("self", fr);//自己
						break;
					}
				}
			}
		}
		return result;

	}
}
