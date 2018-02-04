package com.lbs.nettyserver.model.request.my;

/**
 * 我的-获取用户今天得到的赞的总数请求类
 * @author visual
 *
 */
public class MyGetGetZanTodayTotalRequest {

	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
