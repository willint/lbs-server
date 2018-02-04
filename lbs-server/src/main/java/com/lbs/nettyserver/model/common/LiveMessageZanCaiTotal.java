package com.lbs.nettyserver.model.common;

import java.math.BigInteger;

/**
 * 现场-现场消息赞踩统计
 * @author visual
 *
 */
public class LiveMessageZanCaiTotal {

	private BigInteger zanTotal;
	/**
	 * 是否踩，0-否，1-是
	 */
	private BigInteger isZan;
	private BigInteger caiTotal;
	private BigInteger isCai;
	public BigInteger getZanTotal() {
		return zanTotal;
	}
	public void setZanTotal(BigInteger zanTotal) {
		this.zanTotal = zanTotal;
	}
	public BigInteger getIsZan() {
		return isZan;
	}
	public void setIsZan(BigInteger isZan) {
		this.isZan = isZan;
	}
	public BigInteger getCaiTotal() {
		return caiTotal;
	}
	public void setCaiTotal(BigInteger caiTotal) {
		this.caiTotal = caiTotal;
	}
	public BigInteger getIsCai() {
		return isCai;
	}
	public void setIsCai(BigInteger isCai) {
		this.isCai = isCai;
	}
	
	
	
	
}
