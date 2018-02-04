package com.lbs.nettyserver.model.pojo;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户信息类
 * @author visual
 *
 */
@Entity
@Table(name="t_user")
public class User {
	/**
	 * 主键
	 */
	@Id
    @Column(name = "user_id")
	private String userId;
	/**
	 * 邮箱地址
	 */
	@Column(name = "email")
	private String email;
	/**
	 * 电话号码
	 */
	@Column(name = "phone")
	private String phone;
	/**
	 * 头像图片路径
	 */
	@Column(name = "headimg")
	private String headImg;
	/**
	 * 昵称
	 */
	@Column(name = "nickname")
	private String nickName;
	/**
	 * 密码
	 */
	@Column(name = "password")
	private String password;
	/**
	 * 性别
	 */
	@Column(name = "sex")
	private Character sex;
	/**
	 * 等级值
	 */
	@Column(name = "levl_val")
	private BigDecimal lv;
	/**
	 * 现场得分
	 */
	@Column(name = "live_score")
	private BigDecimal liveScore;
	/**
	 * 用户中心背景图片路径
	 */
	@Column(name = "backimg")
	private String backImg;
	/**
	 * 预留字段
	 */
	@Column(name = "free0")
	private String free0;
	/**
	 * 预留字段
	 */
	@Column(name = "free1")
	private String free1;
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Character getSex() {
		return sex;
	}
	public void setSex(Character sex) {
		this.sex = sex;
	}
	
	public BigDecimal getLv() {
		return lv;
	}
	public void setLv(BigDecimal lv) {
		this.lv = lv;
	}
	public BigDecimal getLiveScore() {
		return liveScore;
	}
	public void setLiveScore(BigDecimal liveScore) {
		this.liveScore = liveScore;
	}
	public String getBackImg() {
		return backImg;
	}
	public void setBackImg(String backImg) {
		this.backImg = backImg;
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
