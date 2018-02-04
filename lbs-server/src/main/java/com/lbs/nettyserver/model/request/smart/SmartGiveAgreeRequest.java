package com.lbs.nettyserver.model.request.smart;

import java.math.BigDecimal;

/**
 * 智囊-点击同意请求类
 * @author visual
 *
 */
public class SmartGiveAgreeRequest {

	/**
	 * 智囊消息id
	 */
	private String smId;
	
	/**
	 * 被同意的用户id
	 */
	private String beUserId;
	
	/**
	 * 点同意的用户id
	 */
	private String userId;
	
	/**
	 * 同意时的经度
	 * */
	private BigDecimal jd;
	
	/**
	 * 同意时的纬度
	 */
	private BigDecimal wd;
	
	/**
	 * 地理位置名称
	 */
	private String locationName;

	public String getSmId() {
		return smId;
	}

	public void setSmId(String smId) {
		this.smId = smId;
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

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
}
