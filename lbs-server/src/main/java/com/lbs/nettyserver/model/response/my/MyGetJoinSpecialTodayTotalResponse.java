package com.lbs.nettyserver.model.response.my;

import java.math.BigInteger;

/**
 * 我的-获取用户今天加入的专题总数响应类
 * @author visual
 *
 */
public class MyGetJoinSpecialTodayTotalResponse {
	
	private BigInteger joinSpecialTodayTotal;

	public BigInteger getJoinSpecialTodayTotal() {
		return joinSpecialTodayTotal;
	}

	public void setJoinSpecialTodayTotal(BigInteger joinSpecialTodayTotal) {
		this.joinSpecialTodayTotal = joinSpecialTodayTotal;
	}
}
