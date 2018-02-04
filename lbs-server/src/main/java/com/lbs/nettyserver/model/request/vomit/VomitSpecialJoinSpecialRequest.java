package com.lbs.nettyserver.model.request.vomit;

import java.math.BigDecimal;

import javax.persistence.Column;

/**
 * 吐槽-加入专题请求类
 * @author visual
 *
 */
public class VomitSpecialJoinSpecialRequest {
	/**
	 * 专题id
	 */
	@Column(name = "special_id")
	private String specialId;
	/**
	 * 用户ID
	 */
	@Column(name = "user_id")
	private String userId;
	/**
	 * 经度
	 */
	@Column(name = "jd")
	private BigDecimal jd;
	/**
	 * 纬度
	 */
	@Column(name = "wd")
	
	
	private BigDecimal wd;
	public String getSpecialId() {
		return specialId;
	}
	public void setSpecialId(String specialId) {
		this.specialId = specialId;
	}
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
}
