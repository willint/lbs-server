package com.lbs.nettyserver.model.request.common;

/**
 * 验证用户密码是否正确请求类
 * @author visual
 *
 */
public class CheckUserPwsIsRightRequest {

	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 用户密码
	 */
	private String pws;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPws() {
		return pws;
	}

	public void setPws(String pws) {
		this.pws = pws;
	}
	
}
