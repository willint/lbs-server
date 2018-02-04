package com.lbs.nettyserver.service.impl;

import net.sf.json.JSONObject;

public interface CommonBizService {
	
	public boolean checkBizData(JSONObject data);
	
	public JSONObject handleBiz(JSONObject data);

}
