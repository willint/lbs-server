package com.lbs.nettyserver.model.response.common;

/**
 * 用户状态更新响应类
 * @author visual
 *
 */
public class TLoginUserResponse{
	/**
	 * 主键
	 */
	private String loginId;
	
	/**
	 * 用户ID
	 */
	private String userId;
	
	/**
	 * 更新状态时间
	 */
	private String lastTime;
	
	/**
	 * 是否为注册用户-0-未注册，1已注册
	 */
	private Character isRegist;

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

	public String getLastTime() {
		return lastTime;
	}

	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}

	public Character getIsRegist() {
		return isRegist;
	}

	public void setIsRegist(Character isRegist) {
		this.isRegist = isRegist;
	}
	
	
}
