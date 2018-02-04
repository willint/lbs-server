package com.lbs.nettyserver.model.pojo;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 现场-赞
 * @author visual
 *
 */
@Entity
@Table(name="t_live_zan")
public class LiveZan {
	/**
	 * 主键
	 */
	@Id
    @Column(name = "zan_id")
	private String zanId;
	
	/**
	 * 现场消息id
	 */
	@Column(name = "lm_id")
	private String lmId;
	
	/**
	 * 被赞的用户id
	 */
	@Column(name = "be_user_id")
	private String beUserId;
	
	/**
	 * 点赞的用户id
	 */
	@Column(name = "user_id")
	private String userId;
	
	/**
	 * 赞时的经度
	 */
	@Column(name = "jd")
	private BigDecimal jd;
	
	/**
	 * 赞时的纬度
	 */
	@Column(name = "wd")
	private BigDecimal wd;
	
	/**
	 * 赞的时间
	 */
	@Column(name = "zan_time")
	private Date zanTime;
	
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
	 * 地理位置名称
	 */
	@Column(name = "location_name")
	private String locationName;
	
	

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getZanId() {
		return zanId;
	}

	public void setZanId(String zanId) {
		this.zanId = zanId;
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

	public Date getZanTime() {
		return zanTime;
	}

	public void setZanTime(Date zanTime) {
		this.zanTime = zanTime;
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
}
