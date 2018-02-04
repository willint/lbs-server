package com.lbs.nettyserver.model.request.live;

import java.math.BigDecimal;

import com.lbs.nettyserver.model.common.LiveMessageContent;

/**
 * 现场-发布现场消息请求类
 * @author visual
 *
 */
public class LivePublishMessageRequest {

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
	 * 现场内容
	 */
	private LiveMessageContent liveMessageContent;
	
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
	public LiveMessageContent getLiveMessageContent() {
		return liveMessageContent;
	}
	public void setLiveMessageContent(LiveMessageContent liveMessageContent) {
		this.liveMessageContent = liveMessageContent;
	}
}
