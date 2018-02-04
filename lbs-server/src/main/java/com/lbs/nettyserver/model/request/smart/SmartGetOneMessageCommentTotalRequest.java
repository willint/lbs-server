package com.lbs.nettyserver.model.request.smart;

/**
 * 智囊-获取某一条消息的评论总数请求类
 * @author visual
 *
 */
public class SmartGetOneMessageCommentTotalRequest {

	/**
	 * 智囊消息id
	 */
	private String smId;

	public String getSmId() {
		return smId;
	}

	public void setSmId(String smId) {
		this.smId = smId;
	}
	
	
}
