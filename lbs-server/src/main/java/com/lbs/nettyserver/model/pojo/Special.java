package com.lbs.nettyserver.model.pojo;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 专题实体类
 * @author visual
 *
 */
@Entity
@Table(name="t_special")
public class Special {
	/**
	 * 专题ID
	 */
	@Id
    @Column(name = "special_id")
	private String specialId;
	/**
	 * 专题创建者的用户ID
	 */
	@Column(name = "create_user_id")
	private String createUserid;
	/**
	 * 专题主题
	 */
	@Column(name = "them")
	private String them;
	/**
	 * 专题图片
	 */
	@Column(name = "img_path")
	private String imgPath;
	/**
	 * 专题条件（1只限男，2只限女，3男女不限）
	 */
	@Column(name = "condition")
	private String condition;
	
	/**
	 * 专题人数限制
	 */
	@Column(name = "person_count_limit")
	private Integer personCountLimit;
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
	 * 专题有效时间，最大24小时
	 */
	@Column(name = "effective_time")
	private Integer effectiveTime;
	/**
	 * 专题创建时间
	 */
	@Column(name = "creat_time")
	private Date createTime;
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
	public String getSpecialId() {
		return specialId;
	}
	public void setSpecialId(String specialId) {
		this.specialId = specialId;
	}
	public String getCreateUserid() {
		return createUserid;
	}
	public void setCreateUserid(String createUserid) {
		this.createUserid = createUserid;
	}
	public String getThem() {
		return them;
	}
	public void setThem(String them) {
		this.them = them;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public Integer getPersonCountLimit() {
		return personCountLimit;
	}
	public void setPersonCountLimit(Integer personCountLimit) {
		this.personCountLimit = personCountLimit;
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
	public Integer getEffectiveTime() {
		return effectiveTime;
	}
	public void setEffectiveTime(Integer effectiveTime) {
		this.effectiveTime = effectiveTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
