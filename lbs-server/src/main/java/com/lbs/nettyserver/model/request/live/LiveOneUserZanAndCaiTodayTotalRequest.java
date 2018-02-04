package com.lbs.nettyserver.model.request.live;

/**
 * 现场-获取某一用户今天获得的赞踩总数请求类
 * @author visual
 *
 */
public class LiveOneUserZanAndCaiTodayTotalRequest {

	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
