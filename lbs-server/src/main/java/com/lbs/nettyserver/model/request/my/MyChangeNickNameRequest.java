package com.lbs.nettyserver.model.request.my;

/**
 * 我的-用户修改昵称请求类
 * @author visual
 *
 */
public class MyChangeNickNameRequest {

	private String userId;
	
	private String nickName;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
}
