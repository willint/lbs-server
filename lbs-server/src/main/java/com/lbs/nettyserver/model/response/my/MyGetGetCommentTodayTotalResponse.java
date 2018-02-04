package com.lbs.nettyserver.model.response.my;

import java.math.BigInteger;

/**
 * 我的-获取用户今天得到的评论的总数响应类
 * @author visual
 *
 */
public class MyGetGetCommentTodayTotalResponse {

	private BigInteger getCommentTodayTotal;

	public BigInteger getGetCommentTodayTotal() {
		return getCommentTodayTotal;
	}

	public void setGetCommentTodayTotal(BigInteger getCommentTodayTotal) {
		this.getCommentTodayTotal = getCommentTodayTotal;
	}
	
	
}
