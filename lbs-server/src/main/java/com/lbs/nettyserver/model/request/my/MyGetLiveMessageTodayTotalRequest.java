package com.lbs.nettyserver.model.request.my;

/**
 * 我的-获取用户今天发布的现场消息总数请求类
 * @author visual
 *
 */
public class MyGetLiveMessageTodayTotalRequest {

	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
