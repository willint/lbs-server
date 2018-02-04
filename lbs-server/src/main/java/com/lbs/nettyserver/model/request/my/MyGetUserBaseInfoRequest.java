package com.lbs.nettyserver.model.request.my;

/**
 * 我的-获取用户基本信息请求类
 * @author visual
 *
 */
public class MyGetUserBaseInfoRequest {

	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
