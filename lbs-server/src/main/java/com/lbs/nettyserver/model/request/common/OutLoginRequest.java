package com.lbs.nettyserver.model.request.common;

/**
 * 用户退出登录请求类
 * @author visual
 *
 */
public class OutLoginRequest {

	private String loginId;
	
	private String userId;

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
