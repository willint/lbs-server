package com.lbs.nettyserver.model.response.vomit;

import java.math.BigDecimal;

/**
 * 吐槽-创建专题响应类
 * @author visual
 *
 */
public class VomitSpecialCreateSpecialResponse {
	private String createUserid;
	private String them;
	private String imgPath;
	private String condition;
	private Integer personCountLimit;
	private BigDecimal jd;
	private BigDecimal wd;
	private Integer effectiveTime;
	private String createTime;
	private String specialId;
	
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
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getSpecialId() {
		return specialId;
	}
	public void setSpecialId(String specialId) {
		this.specialId = specialId;
	}
	
	
}
