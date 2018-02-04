package com.lbs.nettyserver.model.request.common;

/**
 * 获取某一用户的体力能量请求类
 * @author visual
 *
 */
public class GetOneUserPowerValueRequest {

	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
