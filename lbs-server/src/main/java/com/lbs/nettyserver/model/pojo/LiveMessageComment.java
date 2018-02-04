package com.lbs.nettyserver.model.pojo;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 现场-现场消息评论实体类
 * @author visual
 *
 */
@Entity
@Table(name="t_live_message_comment")
public class LiveMessageComment {
	/**
	 * 主键
	 */
	@Id
    @Column(name = "comment_id")
	private String commentId;
	/**
	 * 现场消息ID
	 */
    @Column(name = "lm_id")
	private String lmId;
    /**
	 * 用户ID
	 */
    @Column(name = "user_id")
	private String userId;
    /**
	 * 评论内容
	 */
    @Column(name = "content")
	private String content;
    /**
	 * 经度
	 */
    @Column(name = "jd")
	private BigDecimal jd;
    /**
	 * 纬度
	 */
    @Column(name = "wd")
	private BigDecimal wd;
    /**
	 * 评论的父节点ID（评论中的评论）
	 */
    @Column(name = "pid")
	private String pId;
    /**
	 * 预留
	 */
    @Column(name = "free0")
	private String free0;
    /**
	 * 预留
	 */
    @Column(name = "free1")
	private String free1;
    /**
	 * 评论时间
	 */
    @Column(name = "comment_time")
	private Date commentTime;
    /**
	 * 消息真实性评分
	 */
    @Column(name = "real_score")
	private BigDecimal realScore;
    /**
	 * 消息清晰性评分
	 */
    @Column(name = "clear_score")
	private BigDecimal clearScore;
    /**
	 * 消息发布及时性评分
	 */
    @Column(name = "use_score")
	private BigDecimal useScore;
    
    /**
	 * 评论综合得分
	 */
    @Column(name = "comment_score")
	private BigDecimal commentScore;
    
    /**
	 * 地理位置名称
	 */
    @Column(name = "location_name")
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
	public Date getCommentTime() {
		return commentTime;
	}
	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
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
	public BigDecimal getCommentScore() {
		return commentScore;
	}
	public void setCommentScore(BigDecimal commentScore) {
		this.commentScore = commentScore;
	}
    
    
	
}
