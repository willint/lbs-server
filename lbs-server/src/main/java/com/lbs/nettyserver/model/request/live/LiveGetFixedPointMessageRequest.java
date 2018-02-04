package com.lbs.nettyserver.model.request.live;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 现场-获取定点消息请求类
 * @author visual
 *
 */
public class LiveGetFixedPointMessageRequest {
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 经度
	 */
	private BigDecimal jd;
	/**
	 * 纬度
	 */
	private BigDecimal wd;
	/**
	 * 现场消息接受范围
	 */
	private BigDecimal liveRange;
	/**
	 * 分页查询获取的数据条数
	 */
	private BigInteger limit;
	/**
	 * 分页查询获取的数据起点
	 */
	private BigInteger offset;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public BigDecimal getJd() {
		return jd;
	}

	public void setJd(BigDecimal jd) {
		this.jd = jd;
	}

	public BigDecimal getWd() {
		return wd;
	}

	public void setWd(BigDecimal wd) {
		this.wd = wd;
	}

	public BigDecimal getLiveRange() {
		return liveRange;
	}

	public void setLiveRange(BigDecimal liveRange) {
		this.liveRange = liveRange;
	}

	public BigInteger getLimit() {
		return limit;
	}

	public void setLimit(BigInteger limit) {
		this.limit = limit;
	}

	public BigInteger getOffset() {
		return offset;
	}

	public void setOffset(BigInteger offset) {
		this.offset = offset;
	}
}
