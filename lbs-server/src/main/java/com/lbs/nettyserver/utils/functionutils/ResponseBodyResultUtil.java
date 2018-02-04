package com.lbs.nettyserver.utils.functionutils;


import com.lbs.nettyserver.model.sys.CodeAndMsgConstants;
import com.lbs.nettyserver.model.sys.FlagConstants;
import com.lbs.nettyserver.model.sys.SysCharConstants;

import net.sf.json.JSONObject;

/**
 * 得到服务端body响应部分json
 * @author visual
 *
 */
public class ResponseBodyResultUtil {

	/**
	 * 获取业务参数错误的JSONObject
	 * @param param_error_msg 业务参数消息，如果为空或null则为默认消息
	 * @return
	 */
    public JSONObject getParam_error_result(String param_error_msg){
		
    	JSONObject result=new JSONObject();
	    result.put(SysCharConstants.flagChar, FlagConstants.fail_flag);
	    result.put(SysCharConstants.codeChar, CodeAndMsgConstants.param_error_code);
	    result.put(SysCharConstants.msgChar,  (param_error_msg==null || "".equals(param_error_msg))?CodeAndMsgConstants.param_error_msg:param_error_msg);
		return result;
	}
   
    /**
     * 获取请求成功的JSONObject
     * @param data 响应的具体数据对象,无则传null
     * @param success_msg 消息，若果为空或者null，则为默认消息
     * @return
     */
    public JSONObject getSuccess_result(Object data,String success_msg){
    	JSONObject result=new JSONObject();
    	if(data!=null){
    		result.put(SysCharConstants.dataChar, data);
    	}
		result.put(SysCharConstants.flagChar, FlagConstants.success_flag);
		result.put(SysCharConstants.codeChar, CodeAndMsgConstants.success_code);
		result.put(SysCharConstants.msgChar, (success_msg==null || "".equals(success_msg))?CodeAndMsgConstants.success_code_msg:success_msg);
		return result;

    }
    
    /**
     * 系统错误默认的JSONObject
     * @return
     */
    public JSONObject getSys_error_default_result(){
    	
    	JSONObject result=new JSONObject();	
    	result.put(SysCharConstants.flagChar, FlagConstants.syserror_flag);
    	result.put(SysCharConstants.codeChar, CodeAndMsgConstants.sys_error_code);
    	result.put(SysCharConstants.msgChar, CodeAndMsgConstants.sys_error_msg);
    	return result;
    }
	
}
