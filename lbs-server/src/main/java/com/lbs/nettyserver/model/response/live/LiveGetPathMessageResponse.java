package com.lbs.nettyserver.model.response.live;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import com.lbs.nettyserver.utils.sysutils.TimeUtil;

/**
 * 现场-获取轨迹现场消息响应类
 * @author visual
 *
 */
public class LiveGetPathMessageResponse {

	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 昵称
	 */
	private String nickName;
	/**
	 * 头像
	 */
	private String headImg;
	/**
	 * 性别
	 */
	private Character sex;
	/**
	 * 现场得分
	 */
	private BigDecimal liveScore;
	
	/**
	 * 消息ID
	 */
	private String lmId;
	/**
	 * 经度
	 */
	private BigDecimal jd;
	/**
	 * 纬度
	 */
	private BigDecimal wd;
	/**
	 * 消息具体内容
	 */
	private String content;
	/**
	 * 消息得分
	 */
	private BigDecimal messageScore;
	/**
	 * 消息发送时间
	 */
	private String sendTime;
	/**
	 * 距离
	 */
	private Double distance;
	
	/**
	 * 赞的总数
	 */
	private BigInteger zanTotal;
	/**
	 * 我是否赞过，0-否，1-是
	 */
	private BigInteger isZan;
	/**
	 * 踩的数量
	 */
    private BigInteger caiTotal;
	/**
	 * 我是否踩过
	 */
	private BigInteger isCai;
	
	/**
	 * 地理位置名称
	 */
	private String locationName;
	
	

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

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

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
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

	public String getLmId() {
		return lmId;
	}

	public void setLmId(String lmId) {
		this.lmId = lmId;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public BigDecimal getMessageScore() {
		return messageScore;
	}

	public void setMessageScore(BigDecimal messageScore) {
		this.messageScore = messageScore;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = TimeUtil.dateTimeFormatToString(sendTime);
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public BigInteger getZanTotal() {
		return zanTotal;
	}

	public void setZanTotal(BigInteger zanTotal) {
		this.zanTotal = zanTotal;
	}

	public BigInteger getIsZan() {
		return isZan;
	}

	public void setIsZan(BigInteger isZan) {
		this.isZan = isZan;
	}

	public BigInteger getCaiTotal() {
		return caiTotal;
	}

	public void setCaiTotal(BigInteger caiTotal) {
		this.caiTotal = caiTotal;
	}

	public BigInteger getIsCai() {
		return isCai;
	}

	public void setIsCai(BigInteger isCai) {
		this.isCai = isCai;
	}
}
