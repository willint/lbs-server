package com.lbs.nettyserver.model.request.common;

/**
 * 取消关注请求类
 * @author visual
 *
 */
public class CancelAttentionPersonRequest {

	/**
	 * 关注的id
	 */
	private String lapId;

	public String getLapId() {
		return lapId;
	}

	public void setLapId(String lapId) {
		this.lapId = lapId;
	}
	
	
}
