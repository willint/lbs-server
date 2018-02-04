package com.lbs.nettyserver.service.sysservice;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbs.nettyserver.dao.sys.FuncSetDAO;
import com.lbs.nettyserver.model.pojo.FuncSet;
import com.lbs.nettyserver.service.impl.CommonBizService;
import com.lbs.nettyserver.utils.functionutils.ResponseBodyResultUtil;

import net.sf.json.JSONObject;

/**
 * 获取吐槽技能信息接口
 * @author visual
 *
 */
@Service("getFuncSetListService")
public class GetFuncSetListService implements CommonBizService {


	private static final Log logger = LogFactory.getLog(GetFuncSetListService.class);
	
	@Autowired
	private FuncSetDAO funcSetDAO;
	

	@Override
	public boolean checkBizData(JSONObject data) {
		boolean resultflag = false;
	    return resultflag;
	}

	@Override
	public JSONObject handleBiz(JSONObject data) {
		JSONObject result = new JSONObject();
	    ResponseBodyResultUtil responseBodyResultUtil=new ResponseBodyResultUtil();
		try{
			List<FuncSet> funcSetList=new ArrayList<FuncSet>();
			funcSetList=funcSetDAO.getFuncSetList();
	        result=responseBodyResultUtil.getSuccess_result(funcSetList==null?new Object[0]:funcSetList, "技能配置获取成功");
		}catch(Exception e){
			logger.error("获取吐槽技能信息接口handleBiz错误："+e.getMessage());
			e.printStackTrace();
		}	
		return result;
	}
}
