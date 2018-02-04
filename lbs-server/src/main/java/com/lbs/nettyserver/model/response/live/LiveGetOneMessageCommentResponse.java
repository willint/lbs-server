package com.lbs.nettyserver.model.response.live;

import java.math.BigDecimal;
import java.util.Date;

import com.lbs.nettyserver.utils.sysutils.TimeUtil;

/**
 * 现场-获取现场某一条消息的评论响应类
 * @author visual
 *
 */
public class LiveGetOneMessageCommentResponse {

	/**
	 * 评论的id
	 */
	private String commentId;
	/**
	 * 消息的id
	 */
	private String lmId;
	/**
	 * 评论内容结构
	 */
	private String content;
	/**
	 * 经度
	 */
	private BigDecimal jd ;
	/**
	 * 纬度
	 */
	private BigDecimal wd ;
	/**
	 * 评论时间
	 */
	private String commentTime;
	/**
	 * 评论的综合得分
	 */
	private BigDecimal commentScore;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 头像
	 */
	private String headImg;
	/**
	 * 昵称
	 */
	private String nickName;
	/**
	 * 性别
	 */
	private Character sex;
	/**
	 * 用户现场得分
	 */
	private BigDecimal liveScore;
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
	public String getCommentId() {
		return commentId;
	}
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}
	public String getLmId() {
		return lmId;
	}
	public void setLmId(String lmId) {
		this.lmId = lmId;
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
}
