package com.lbs.nettyserver.model.request.my;

/**
 * 我的-获取用户今天加入的专题总数请求类
 * @author visual
 *
 */
public class MyGetJoinSpecialTodayTotalRequest {

	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
