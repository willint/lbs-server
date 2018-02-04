package com.lbs.nettyserver.model.request.find;

import java.math.BigDecimal;


/**
 * 发现-吐槽排名请求类
 * @author visual
 *
 */
public class FindVomitRankRequest {

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
	
	
}
