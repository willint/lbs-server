package com.lbs.nettyserver.model.response.find;


import java.math.BigDecimal;

/**
 * 发现-吐槽排名响应类
 * @author visual
 *
 */
public class FindVomitRankResponse {
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
	 * 吐槽等级值
	 */
	private BigDecimal lv;
	/**
	 * 距离
	 */
	private Double distance;
	/**
	 * 名词
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

	public BigDecimal getLv() {
		return lv;
	}

	public void setLv(BigDecimal lv) {
		this.lv = lv;
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
