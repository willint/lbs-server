package com.lbs.nettyserver.model.response.find;

import java.math.BigDecimal;

/**
 * 发现-现场排名结果类
 * @author visual
 *
 */
public class FindLiveRankResponse {

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
	 * 距离
	 */
	private Double distance;
	/**
	 * 名次
	 */
	private Integer rank;
	
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
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	
	
}
