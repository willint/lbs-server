package com.lbs.nettyserver.model.request.common;

import java.math.BigDecimal;


/**
 * 关注用户请求类
 * @author visual
 *
 */
public class AttentionPersonRequest {

	private String beUserId;

	private String userId;
	
	private String attentionSource;
	
	private BigDecimal jd;

	private BigDecimal wd;
	
	private String locationName;
	
	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getBeUserId() {
		return beUserId;
	}

	public void setBeUserId(String beUserId) {
		this.beUserId = beUserId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAttentionSource() {
		return attentionSource;
	}

	public void setAttentionSource(String attentionSource) {
		this.attentionSource = attentionSource;
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
