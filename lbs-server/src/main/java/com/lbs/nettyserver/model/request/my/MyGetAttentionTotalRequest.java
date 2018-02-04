package com.lbs.nettyserver.model.request.my;

/**
 * 我的-获取用户我关注的人数的总数请求类
 * @author visual
 *
 */
public class MyGetAttentionTotalRequest {

	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
