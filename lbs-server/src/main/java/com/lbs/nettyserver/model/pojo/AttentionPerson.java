package com.lbs.nettyserver.model.pojo;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户关注实体类
 * @author visual
 *
 */
@Entity
@Table(name="t_attention_person")
public class AttentionPerson {
	/**
	 * 主键
	 */
	@Id
    @Column(name = "lap_id")
	private String lapId;
	
	/**
	 * 被关注的用户id
	 */
	@Column(name = "be_user_id")
	private String beUserId;
	
	/**
	 * 关注的用户id
	 */
	@Column(name = "user_id")
	private String userId;
	
	/**
	 *关注来源
	 */
	@Column(name = "attention_source")
	private String attentionSource;
	
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
	 * 关注时间
	 */
	@Column(name = "attention_time")
	private Date attentionTime;
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

	public String getLapId() {
		return lapId;
	}

	public void setLapId(String lapId) {
		this.lapId = lapId;
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

	public Date getAttentionTime() {
		return attentionTime;
	}

	public void setAttentionTime(Date attentionTime) {
		this.attentionTime = attentionTime;
	}
	
	
}
