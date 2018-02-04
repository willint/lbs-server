package com.lbs.nettyserver.model.response.my;

import java.math.BigDecimal;

/**
 * 我的-获取用户基本信息响应类
 * @author visual
 *
 */
public class MyGetUserBaseInfoResponse {

	/**
	 * 邮箱地址
	 */
	private String email;
	/**
	 * 电话号码
	 */
	private String phone;
	/**
	 * 头像路径
	 */
	private String headImg;
	/**
	 * 昵称
	 */
	private String nickName;
	
	private Character sex;
	/**
	 * 现场综合得分
	 */
	private BigDecimal liveScore;
	/**
	 * 吐槽等级值
	 */
	private BigDecimal lv;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
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
	public BigDecimal getLiveScore() {
		return liveScore;
	}
	public void setLiveScore(BigDecimal liveScore) {
		this.liveScore = liveScore;
	}
	public BigDecimal getLv() {
		return lv;
	}
	public void setLv(BigDecimal lv) {
		this.lv = lv;
	}
	
	
	
}
