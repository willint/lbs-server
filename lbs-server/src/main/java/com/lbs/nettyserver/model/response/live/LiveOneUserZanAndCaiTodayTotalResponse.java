package com.lbs.nettyserver.model.response.live;

import java.math.BigInteger;

/**
 * 现场-获取某一用户今天获得的赞踩总数响应类
 * @author visual
 *
 */
public class LiveOneUserZanAndCaiTodayTotalResponse {

	/**
	 * 赞的总数
	 */
	private BigInteger zanTodayTotal;
	
	/**
	 * 踩的总数
	 */
	private BigInteger caiTodayTotal;

	public BigInteger getZanTodayTotal() {
		return zanTodayTotal;
	}

	public void setZanTodayTotal(BigInteger zanTodayTotal) {
		this.zanTodayTotal = zanTodayTotal;
	}

	public BigInteger getCaiTodayTotal() {
		return caiTodayTotal;
	}

	public void setCaiTodayTotal(BigInteger caiTodayTotal) {
		this.caiTodayTotal = caiTodayTotal;
	}

}
