package com.lbs.nettyserver.model.request.my;

/**
 * 我的-获取用户粉丝的总数（关注我的人）请求类
 * @author visual
 *
 */
public class MyGetFansTotalRequest {

	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
