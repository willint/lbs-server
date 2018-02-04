package com.lbs.nettyserver.model.response.common;


/**
 * 用户关注响应类
 * @author visual
 *
 */
public class AttentionPersonResponse {

	/**
	 * 关注的id
	 */
	private String lapId;
	/**
	 * 关注时间
	 */
	private String attentionTime;

	public String getLapId() {
		return lapId;
	}

	public void setLapId(String lapId) {
		this.lapId = lapId;
	}

	public String getAttentionTime() {
		return attentionTime;
	}

	public void setAttentionTime(String attentionTime) {
		this.attentionTime = attentionTime;
	}

}
