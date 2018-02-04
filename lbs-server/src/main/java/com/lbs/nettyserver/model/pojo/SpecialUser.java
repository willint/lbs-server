package com.lbs.nettyserver.model.pojo;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 吐槽专题-专题成员实体类
 * @author visual
 *
 */
@Entity
@Table(name="t_special_users")
public class SpecialUser {

	/**
	 * 主键
	 */
	@Id
	@Column(name = "su_id")
	private String suId;
	/**
	 * 专题id
	 */
	@Column(name = "special_id")
	private String specialId;
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
	 * 加入时间
	 */
	@Column(name = "join_time")
	private Date joinTime;
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
	public String getSuId() {
		return suId;
	}
	public void setSuId(String suId) {
		this.suId = suId;
	}
	public String getSpecialId() {
		return specialId;
	}
	public void setSpecialId(String specialId) {
		this.specialId = specialId;
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
	public Date getJoinTime() {
		return joinTime;
	}
	public void setJoinTime(Date joinTime) {
		this.joinTime = joinTime;
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
