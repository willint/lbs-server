package com.lbs.nettyserver.model.response.my;

import java.math.BigInteger;

/**
 * 我的-获取用户今天发布的现场消息的总数响应类
 * @author visual
 *
 */
public class MyGetLiveMessageTodayTotalResponse {

	
	private BigInteger liveMessageTodayTotal;

	public BigInteger getLiveMessageTodayTotal() {
		return liveMessageTodayTotal;
	}

	public void setLiveMessageTodayTotal(BigInteger liveMessageTodayTotal) {
		this.liveMessageTodayTotal = liveMessageTodayTotal;
	}
	
	
}
