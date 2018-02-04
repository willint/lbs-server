package com.lbs.nettyserver.model.request.common;

/**
 * 检查用户是否已经关注
 * @author visual
 *
 */
public class CheckIsAttentionRequest {

	/**
	 * 关注的用户id（当前使用手机的用户）
	 */
	private String userId;
	/**
	 * 被关注的用户id（被看到的用户）
	 */
	private String beUserId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBeUserId() {
		return beUserId;
	}

	public void setBeUserId(String beUserId) {
		this.beUserId = beUserId;
	}
	
	
	
}