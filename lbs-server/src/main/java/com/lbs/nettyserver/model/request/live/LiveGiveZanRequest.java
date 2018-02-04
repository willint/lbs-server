package com.lbs.nettyserver.model.request.live;

import java.math.BigDecimal;

/**
 * 现场-点赞请求类
 * @author visual
 *
 */
public class LiveGiveZanRequest {

	/**
	 * 现场消息id
	 */
	private String lmId;
	
	/**
	 * 被赞的用户id
	 */
	private String beUserId;
	
	/**
	 * 点赞的用户id
	 */
	private String userId;
	
	/**
	 * 赞时的经度
	 */
	private BigDecimal jd;
	
	/**
	 * 赞时的纬度
	 */
	private BigDecimal wd;
	
	/**
	 * 地理位置名称
	 */
	private String locationName;
	

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getLmId() {
		return lmId;
	}

	public void setLmId(String lmId) {
		this.lmId = lmId;
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
}
