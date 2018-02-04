package com.lbs.nettyserver.model.response.my;

import java.math.BigDecimal;

/**
 * 我的-获取用户的粉丝总数（关注我的人）响应类
 * @author visual
 *
 */
public class MyGetFansTotalResponse {

	private BigDecimal fansTotal;

	public BigDecimal getFansTotal() {
		return fansTotal;
	}

	public void setFansTotal(BigDecimal fansTotal) {
		this.fansTotal = fansTotal;
	}
	
	
}
