package com.lbs.nettyserver.model.response.smart;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import com.lbs.nettyserver.utils.sysutils.TimeUtil;

/**
 * 智囊-获取智囊消息列表响应类
 * @author visual
 *
 */
public class SmartGetSmartMessageListResponse {

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
	 * 智囊消息id
	 */
	private String smId;
	
	/**
	 * 经度
	 */
	private BigDecimal jd;
	/**
	 * 纬度
	 */
	private BigDecimal wd;
	/**
	 * 地理位置名称
	 */
	private String locationName;
	/**
	 * 消息具体内容
	 */
	private String content;
	/**
	 * 消息得分
	 */
	private BigDecimal smartScore;
	/**
	 * 发送时间
	 */
	private String sendTime;
	
	/**
	 * 得到反对的总数
	 */
	private BigInteger againstCount;
	/**
	 * 我是否反对过，0-否，1-是
	 */
	private BigInteger isAgainst;
	
	/**
	 * 得到赞同的总数
	 */
	private BigInteger agreeCount;
	/**
	 * 我是否赞同过，0-否，1-是
	 */
	private BigInteger isAgree;
	/**
	 * 阅读的次数
	 */
	private BigInteger readCount;
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
	public String getSmId() {
		return smId;
	}
	public void setSmId(String smId) {
		this.smId = smId;
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
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public BigDecimal getSmartScore() {
		return smartScore;
	}
	public void setSmartScore(BigDecimal smartScore) {
		this.smartScore = smartScore;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = TimeUtil.dateTimeFormatToString(sendTime);
	}
	public BigInteger getAgainstCount() {
		return againstCount;
	}
	public void setAgainstCount(BigInteger againstCount) {
		this.againstCount = againstCount;
	}
	public BigInteger getIsAgainst() {
		return isAgainst;
	}
	public void setIsAgainst(BigInteger isAgainst) {
		this.isAgainst = isAgainst;
	}
	public BigInteger getAgreeCount() {
		return agreeCount;
	}
	public void setAgreeCount(BigInteger agreeCount) {
		this.agreeCount = agreeCount;
	}
	public BigInteger getIsAgree() {
		return isAgree;
	}
	public void setIsAgree(BigInteger isAgree) {
		this.isAgree = isAgree;
	}
	public BigInteger getReadCount() {
		return readCount;
	}
	public void setReadCount(BigInteger readCount) {
		this.readCount = readCount;
	}

}
