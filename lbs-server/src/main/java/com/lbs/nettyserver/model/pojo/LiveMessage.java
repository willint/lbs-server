package com.lbs.nettyserver.model.pojo;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 现场-现场消息实体类
 * @author visual
 *
 */
@Entity
@Table(name="t_live_message")
public class LiveMessage {
	/**
	 * 主键
	 */
	@Id
    @Column(name = "lm_id")
	private String lmId;
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
	/**
	 * 发布时间
	 */
	@Column(name = "send_time")
	private Date sendTime;
	/**
	 * 预留
	 */
	@Column(name = "free0")
	private String free0;
	/**
	 * 预留
	 */
	@Column(name = "free1")
	private String free1;
	/**
	 * 具体的消息内容
	 */
	@Column(name = "content")
	private String content;
	/**
	 * 消息得分
	 */
	@Column(name = "message_score")
	private BigDecimal messageScore;
	
	/**
	 * 地理位置名称
	 */
	@Column(name = "location_name")
	private String locationName;
	
	/**
	 * 是否已删除，0-未删除，1-已删除
	 */
	@Column(name = "is_delete")
	private Character isDelete;
	
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
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public String getFree0() {
		return free0;
	}
	public void setFree0(String free0) {
		this.free0 = free0;
	}
	public String getFree1() {
		return free1;
	}
	public void setFree1(String free1) {
		this.free1 = free1;
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
	public Character getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Character isDelete) {
		this.isDelete = isDelete;
	}

}
