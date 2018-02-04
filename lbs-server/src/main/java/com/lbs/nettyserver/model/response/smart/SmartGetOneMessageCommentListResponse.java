package com.lbs.nettyserver.model.response.smart;

import java.math.BigDecimal;
import java.util.Date;

import com.lbs.nettyserver.utils.sysutils.TimeUtil;

/**
 * 智囊-获取某一条消息的评论列表响应类
 * @author visual
 *
 */
public class SmartGetOneMessageCommentListResponse {

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
	 * 评论id
	 */
	private String commentId;
	/**
	 * 智囊消息id
	 */
	private String smId;
	/**
	 * 评论的内容
	 */
	private String content;
	
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
	 * 评论时间
	 */
	private String commentTime;
	/**
	 * 评论分数
	 */
	private BigDecimal commentScore;
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
	public String getCommentId() {
		return commentId;
	}
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}
	public String getSmId() {
		return smId;
	}
	public void setSmId(String smId) {
		this.smId = smId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public String getCommentTime() {
		return commentTime;
	}
	public void setCommentTime(Date commentTime) {
		this.commentTime = TimeUtil.dateTimeFormatToString(commentTime);
	}
	public BigDecimal getCommentScore() {
		return commentScore;
	}
	public void setCommentScore(BigDecimal commentScore) {
		this.commentScore = commentScore;
	}
}
