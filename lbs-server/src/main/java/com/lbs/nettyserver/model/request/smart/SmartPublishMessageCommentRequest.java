package com.lbs.nettyserver.model.request.smart;

import java.math.BigDecimal;

import com.lbs.nettyserver.model.common.SmartMessageCommentContent;

/**
 * 智囊-发表评论请求类
 * @author visual
 *
 */
public class SmartPublishMessageCommentRequest {

	/**
	 * 智囊消息ID
	 */
	private String smId;

	/**
	 * 用户ID
	 */
	private String userId;

	/**
	 * 评论内容结构
	 */
	private SmartMessageCommentContent smartMessageCommentContent;

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
	 * 评论的分数
	 */
	private BigDecimal commentScore;
	
	/**
	 * 地理位置名称
	 */
	private String locationName;

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

	public SmartMessageCommentContent getSmartMessageCommentContent() {
		return smartMessageCommentContent;
	}

	public void setSmartMessageCommentContent(SmartMessageCommentContent smartMessageCommentContent) {
		this.smartMessageCommentContent = smartMessageCommentContent;
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
