package com.lbs.nettyserver.model.request.my;

/**
 * 我的-获取用户今天点出的赞的总数请求类
 * @author visual
 *
 */
public class MyGetPutZanTodayTotalRequest {

	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
