package com.lbs.nettyserver.model.response.my;

import java.util.Date;

import com.lbs.nettyserver.utils.sysutils.TimeUtil;

public class MyGetAttentionPersonsListResponse {

	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 昵称
	 */
	private String nickName;
	/**
	 * 性别
	 */
	private Character sex;
	/**
	 * 头像
	 */
	private String headImg;
	
	/**
	 * 关注我的时间
	 */
	private String attentionTime;

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

	public String getAttentionTime() {
		return attentionTime;
	}

	public void setAttentionTime(Date attentionTime) {
		this.attentionTime = TimeUtil.dateTimeFormatToString(attentionTime);
	}
	
	
}
