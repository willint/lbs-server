package com.lbs.nettyserver.model.response.my;

import java.math.BigDecimal;
import java.util.Date;

import com.lbs.nettyserver.utils.sysutils.TimeUtil;

public class MyGetLiveMessageTodayListResponse {

	/**
	 * 现场消息id
	 */
	private String lmId;
	/**
	 * 地理位置名称
	 */
	private String locationName;
	/**
	 * 发布时间
	 */
	private String sendTime;
	/**
	 * 现场消息内容结构
	 */
	private String content;
	/**
	 * 现场消息得分
	 */
	private BigDecimal messageScore;

	public String getLmId() {
		return lmId;
	}

	public void setLmId(String lmId) {
		this.lmId = lmId;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = TimeUtil.dateTimeFormatToString(sendTime);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public BigDecimal getMessageScore() {
		return messageScore;
	}

	public void setMessageScore(BigDecimal messageScore) {
		this.messageScore = messageScore;
	}
	
	
}
