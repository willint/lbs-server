package com.lbs.nettyserver.model.pojo;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 现场-踩
 * @author visual
 *
 */
@Entity
@Table(name="t_live_cai")
public class LiveCai {
	/**
	 * 主键
	 */
	@Id
    @Column(name = "cai_id")
	private String caiId;
	
	/**
	 * 现场消息id
	 */
	@Column(name = "lm_id")
	private String lmId;
	
	/**
	 * 被踩的用户id
	 */
	@Column(name = "be_user_id")
	private String beUserId;
	
	/**
	 * 点踩的用户id
	 */
	@Column(name = "user_id")
	private String userId;
	
	/**
	 * 踩时的经度
	 */
	@Column(name = "jd")
	private BigDecimal jd;
	
	/**
	 * 踩时的纬度
	 */
	@Column(name = "wd")
	private BigDecimal wd;

	
	/**
	 * 踩的时间
	 */
	@Column(name = "cai_time")
	private Date caiTime;
	
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

	public String getCaiId() {
		return caiId;
	}

	public void setCaiId(String caiId) {
		this.caiId = caiId;
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

	public Date getCaiTime() {
		return caiTime;
	}

	public void setCaiTime(Date caiTime) {
		this.caiTime = caiTime;
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
