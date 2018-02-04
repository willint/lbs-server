package com.lbs.nettyserver.model.response.vomit;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import com.lbs.nettyserver.utils.sysutils.TimeUtil;

/**
 * 吐槽-获取附近专题响应类
 * @author visual
 *
 */
public class VomitSpecialGetFindSpecialResponse {

	private String userId;
	
	private String nickName;
	
	private Character sex;
	
	private String headImg;
	
	private BigDecimal lv;
	
	private String them;
	
	private String imgPath;
	
	private String condition;
	
	private Integer personCountLimit;
	
	private BigDecimal jd;
	
	private BigDecimal wd;
	
	private String specialId;
	
	private String createTime;
	
	private Integer effectiveTime;
	
	private String free0;
	
	private String free1;
	
	private BigInteger personTotal;
	
	private Double distance;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Character getSex() {
		return sex;
	}

	public void setSex(Character sex) {
		this.sex = sex;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public BigDecimal getLv() {
		return lv;
	}

	public void setLv(BigDecimal lv) {
		this.lv = lv;
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

	public String getSpecialId() {
		return specialId;
	}

	public void setSpecialId(String specialId) {
		this.specialId = specialId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = TimeUtil.dateTimeFormatToString(createTime);
	}

	public Integer getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(Integer effectiveTime) {
		this.effectiveTime = effectiveTime;
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

	public BigInteger getPersonTotal() {
		return personTotal;
	}

	public void setPersonTotal(BigInteger personTotal) {
		this.personTotal = personTotal;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}
}
