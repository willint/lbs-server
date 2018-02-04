package com.lbs.nettyserver.model.response.live;

import java.math.BigDecimal;
/**
 * 现场-获取某一用户获得的赞踩总数响应类
 * @author visual
 *
 */
public class LiveOneUserZanAndCaiTotalResponse {

	/**
	 * 赞的总数
	 */
	private BigDecimal zanTotal;
	
	/**
	 * 踩的总数
	 */
	private BigDecimal caiTotal;

	public BigDecimal getZanTotal() {
		return zanTotal;
	}

	public void setZanTotal(BigDecimal zanTotal) {
		this.zanTotal = zanTotal;
	}

	public BigDecimal getCaiTotal() {
		return caiTotal;
	}

	public void setCaiTotal(BigDecimal caiTotal) {
		this.caiTotal = caiTotal;
	}

	
	
	
}
