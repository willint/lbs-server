package com.lbs.nettyserver.service.functionsevice.find;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbs.nettyserver.dao.find.FindVomitRankDAO;
import com.lbs.nettyserver.model.request.find.FindVomitRankRequest;
import com.lbs.nettyserver.model.response.find.FindVomitRankResponse;
import com.lbs.nettyserver.service.impl.CommonBizService;
import com.lbs.nettyserver.utils.functionutils.ResponseBodyResultUtil;

import net.sf.json.JSONObject;

/**
 * 发现-吐槽排名接口
 * @author visual
 *
 */
@Service("findVomitRankService")
public class FindVomitRankService implements CommonBizService {

	private static final Log logger = LogFactory.getLog(FindVomitRankService.class);
	
	@Autowired
	private FindVomitRankDAO findVomitRankDAO;
	
	private FindVomitRankRequest findVomitRankRequest;

	@Override
	public boolean checkBizData(JSONObject data) {
		boolean resultflag = false;
		try {
			if(data != null){
				this.findVomitRankRequest = (FindVomitRankRequest)JSONObject.toBean(data, FindVomitRankRequest.class);
				resultflag = true;
				
				//非空校验
				for (Field field : this.findVomitRankRequest.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					if(field.get(this.findVomitRankRequest)==null || "".equals(field.get(this.findVomitRankRequest))){
						resultflag = false;
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.error("发现-吐槽排名接口checkBizData错误："+e.getMessage());
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
        	List<FindVomitRankResponse> list=new ArrayList<FindVomitRankResponse>();
			list=findVomitRankDAO.getFindVomitRankInfo(this.findVomitRankRequest);
			
			result=responseBodyResultUtil.getSuccess_result(null, "获取成功");
			
			if(list!=null){
				result.put("total", list.size());//总人数
				
				list.get(0).setRank(1);
				result.put("first", list.get(0));//第一条是第一名
				
				Integer selfRank=0;
				for (FindVomitRankResponse fr : list) {
					selfRank++;
					if(fr.getUserId().equals(this.findVomitRankRequest.getUserId())){
						fr.setRank(selfRank);
						result.put("self", fr);//自己
						break;
					}
				}
			}
		}
		return result;

	}
}
