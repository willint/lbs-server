package com.lbs.nettyserver.model.request.live;

import java.math.BigDecimal;

/**
 * 现场-点踩请求类
 * @author visual
 *
 */
public class LiveGiveCaiRequest {

	/**
	 * 现场消息id
	 */
	private String lmId;
	
	/**
	 * 被踩的用户id
	 */
	private String beUserId;
	
	/**
	 * 点踩的用户id
	 */
	private String userId;
	
	/**
	 * 踩时的经度
	 * */
	private BigDecimal jd;
	
	/**
	 * 踩时的纬度
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

	public BigDecimal getWd() {
		return wd;
	}

	public void setWd(BigDecimal wd) {
		this.wd = wd;
	}
	
	public BigDecimal getJd() {
		return jd;
	}

	public void setJd(BigDecimal jd) {
		this.jd = jd;
	}
	
}
