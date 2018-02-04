package com.lbs.nettyserver.model.request.my;

/**
 * 删除某一条现场消息请求类
 * @author visual
 *
 */
public class MyDeleteOneLiveMessageRequest {

	private String userId;
	
	private String lmId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLmId() {
		return lmId;
	}

	public void setLmId(String lmId) {
		this.lmId = lmId;
	}
	
	
}
