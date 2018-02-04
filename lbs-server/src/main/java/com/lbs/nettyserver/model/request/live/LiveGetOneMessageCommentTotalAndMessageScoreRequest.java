package com.lbs.nettyserver.model.request.live;

/**
 * 现场-获取某一条消息的评论总数和消息质量分数请求类
 * @author visual
 *
 */
public class LiveGetOneMessageCommentTotalAndMessageScoreRequest {

	/**
	 * 消息id
	 */
	private String lmId;

	public String getLmId() {
		return lmId;
	}

	public void setLmId(String lmId) {
		this.lmId = lmId;
	}
	
	
}
