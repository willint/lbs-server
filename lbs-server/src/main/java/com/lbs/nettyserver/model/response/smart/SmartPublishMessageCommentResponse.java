package com.lbs.nettyserver.model.response.smart;

import java.math.BigDecimal;

/**
 * 智囊-评论响应类
 * @author visual
 *
 */
public class SmartPublishMessageCommentResponse {

	/**
	 * 主键
	 */
	private String commentId;
	/**
	 * 智囊消息ID
	 */
	private String smId;
    /**
	 * 用户ID
	 */
	private String userId;
    /**
	 * 评论内容
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
	 * 评论的父节点ID（评论中的评论）
	 */
	private String pId;
    /**
	 * 预留
	 */
	private String free0;
    /**
	 * 预留
	 */
	private String free1;
    /**
	 * 评论时间
	 */
	private String commentTime;
	/**
	 * 评论综合得分
	 */
	private BigDecimal commentScore;
	
	/**
	 * 地理位置名称
	 */
	private String locationName;

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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
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

	public String getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(String commentTime) {
		this.commentTime = commentTime;
	}

	public BigDecimal getCommentScore() {
		return commentScore;
	}

	public void setCommentScore(BigDecimal commentScore) {
		this.commentScore = commentScore;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

}
