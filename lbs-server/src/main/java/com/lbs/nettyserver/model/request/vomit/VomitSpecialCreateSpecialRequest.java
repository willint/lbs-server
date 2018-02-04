package com.lbs.nettyserver.model.request.vomit;

import java.math.BigDecimal;

/**
 * 创建专题请求类
 * @author visual
 *
 */
public class VomitSpecialCreateSpecialRequest {
	
	private String createUserid;
	private String them;
	private String imgPath;
	private String condition;
	private Integer personCountLimit;
	private BigDecimal jd;
	private BigDecimal wd;
	private Integer effectiveTime;
	
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
	
	

}
