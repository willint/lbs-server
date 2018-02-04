package com.lbs.nettyserver.service.functionsevice.find;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbs.nettyserver.dao.find.FindNearVomitHotSpecialDAO;
import com.lbs.nettyserver.model.request.find.FindNearVomitHotSpecialRequest;
import com.lbs.nettyserver.model.response.find.FindNearVomitHotSpecialResponse;
import com.lbs.nettyserver.service.impl.CommonBizService;
import com.lbs.nettyserver.utils.functionutils.ResponseBodyResultUtil;

import net.sf.json.JSONObject;

/**
 * 发现-获取附近吐槽热点专题
 * @author visual
 *
 */
@Service("findNearVomitHotSpecialService")
public class FindNearVomitHotSpecialService implements CommonBizService {
	@Autowired
	private FindNearVomitHotSpecialDAO findNearVomitHotSpecialDAO;
	
	private FindNearVomitHotSpecialRequest findNearVomitHotSpecialRequest;

	@Override
	public boolean checkBizData(JSONObject data) {
		boolean resultflag = false;
		try {
			if(data != null){
				this.findNearVomitHotSpecialRequest = (FindNearVomitHotSpecialRequest)JSONObject.toBean(data, FindNearVomitHotSpecialRequest.class);
				resultflag = true;
				
				//非空校验
				if((this.findNearVomitHotSpecialRequest.getUserId()==null || "".equals(this.findNearVomitHotSpecialRequest.getUserId())) || (this.findNearVomitHotSpecialRequest.getJd()==null || "".equals(this.findNearVomitHotSpecialRequest.getJd()) || (this.findNearVomitHotSpecialRequest.getWd()==null || "".equals(this.findNearVomitHotSpecialRequest.getWd()) || (this.findNearVomitHotSpecialRequest.getVomitRange()==null || "".equals(this.findNearVomitHotSpecialRequest.getVomitRange()))))){
					resultflag=false;
				}
			}
		} catch (Exception e) {
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
			if(this.findNearVomitHotSpecialRequest !=null){
				List<FindNearVomitHotSpecialResponse> list=new ArrayList<FindNearVomitHotSpecialResponse>();
				list=findNearVomitHotSpecialDAO.getFindNearVomitHotSpecialList(this.findNearVomitHotSpecialRequest);
				
				result=responseBodyResultUtil.getSuccess_result(list==null?new Object[0]:list, "获取成功");
			}
		}
		return result;

	}
}
