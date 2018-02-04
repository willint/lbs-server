package com.lbs.nettyserver.model.request.live;

import java.math.BigDecimal;

import com.lbs.nettyserver.model.common.LiveMessageCommentContent;

/**
 * 现场-发布现场消息评论请求类
 * @author visual
 *
 */
public class LivePublishMessageCommentRequest {

	/**
	 * 现场消息ID
	 */
	private String lmId;

	/**
	 * 用户ID
	 */
	private String userId;

	/**
	 * 评论内容结构
	 */
	private LiveMessageCommentContent liveMessageCommentContent;

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
	 * 消息真实性得分
	 */
	private BigDecimal realScore;
	/**
	 * 消息清晰性评分
	 */
	private BigDecimal clearScore;
	/**
	 * 消息有用性得分
	 */
	private BigDecimal useScore;
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
	public String getLmId() {
		return lmId;
	}
	public void setLmId(String lmId) {
		this.lmId = lmId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public LiveMessageCommentContent getLiveMessageCommentContent() {
		return liveMessageCommentContent;
	}
	public void setLiveMessageCommentContent(LiveMessageCommentContent liveMessageCommentContent) {
		this.liveMessageCommentContent = liveMessageCommentContent;
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
	public BigDecimal getRealScore() {
		return realScore;
	}
	public void setRealScore(BigDecimal realScore) {
		this.realScore = realScore;
	}
	public BigDecimal getClearScore() {
		return clearScore;
	}
	public void setClearScore(BigDecimal clearScore) {
		this.clearScore = clearScore;
	}
	public BigDecimal getUseScore() {
		return useScore;
	}
	public void setUseScore(BigDecimal useScore) {
		this.useScore = useScore;
	}
}
