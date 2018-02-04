package com.lbs.nettyserver.model.response.my;

import java.math.BigInteger;

/**
 * 我的-获取用户今天送出的踩的总数
 * @author visual
 *
 */
public class MyGetPutCaiTodayTotalResponse {

	private BigInteger putCaiTodayTotal;

	public BigInteger getPutCaiTodayTotal() {
		return putCaiTodayTotal;
	}

	public void setPutCaiTodayTotal(BigInteger putCaiTodayTotal) {
		this.putCaiTodayTotal = putCaiTodayTotal;
	}
	
	
}
