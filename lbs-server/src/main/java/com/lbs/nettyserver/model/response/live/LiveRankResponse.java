package com.lbs.nettyserver.model.response.live;

import java.math.BigDecimal;
import java.math.BigInteger;

public class LiveRankResponse {

	/**
	 * 用户ID
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
	 * 头像路径
	 */
	private String headImg;
	/**
	 * 现场得分
	 */
	private BigDecimal liveScore;
	/**
	 * 名次
	 */
	private BigInteger rank;
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
	public BigDecimal getLiveScore() {
		return liveScore;
	}
	public void setLiveScore(BigDecimal liveScore) {
		this.liveScore = liveScore;
	}
	public BigInteger getRank() {
		return rank;
	}
	public void setRank(BigInteger rank) {
		this.rank = rank;
	}
}
