package com.lbs.nettyserver.model.response.my;

import java.math.BigInteger;

/**
 * 我的-获取用户今天发表的评论总数响应类
 * @author visual
 *
 */
public class MyGetPutCommentTodayTotalResponse {

	private BigInteger putCommentTodayTotal;

	public BigInteger getPutCommentTodayTotal() {
		return putCommentTodayTotal;
	}

	public void setPutCommentTodayTotal(BigInteger putCommentTodayTotal) {
		this.putCommentTodayTotal = putCommentTodayTotal;
	}
	

}
