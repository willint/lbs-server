package com.lbs.nettyserver.model.request.vomit;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 吐槽-专题-获取参与的专题请求类
 * @author visual
 *
 */
public class VomitSpecialGetJoinedSpecialRequest {

	/**
	 * 用户ID
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
	 * 吐槽接收范围
	 */
	private BigDecimal vomitRange;
	
	/**
	 * 获取的数据条数
	 */
	private BigInteger limit;
	/**
	 * 从哪一条数据开始
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

	public BigDecimal getVomitRange() {
		return vomitRange;
	}

	public void setVomitRange(BigDecimal vomitRange) {
		this.vomitRange = vomitRange;
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
