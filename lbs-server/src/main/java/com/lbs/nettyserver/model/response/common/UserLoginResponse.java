package com.lbs.nettyserver.model.response.common;

/**
 * 用户登录结果响应类
 * @author visual
 *
 */
public class UserLoginResponse {
	/**
	 * 登录ID
	 */
	private String loginId;
	/**
	 * 用户ID
	 */
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
