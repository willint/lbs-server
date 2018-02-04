package com.lbs.nettyserver.model.pojo;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 智囊-智囊消息评论实体类
 * @author visual
 *
 */
@Entity
@Table(name="t_smart_message_comment")
public class SmartMessageComment {

	/**
	 * 主键
	 */
	@Id
    @Column(name = "comment_id")
	private String commentId;
	/**
	 * 智囊消息ID
	 */
    @Column(name = "sm_id")
	private String smId;
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
	 * 评论得分
	 */
    @Column(name = "comment_score")
	private BigDecimal commentScore;
    
    /**
	 * 地理位置名称
	 */
    @Column(name = "location_name")
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

	public Date getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(Date commentTime) {
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
