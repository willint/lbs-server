package com.lbs.nettyserver.model.response.my;

import java.util.Date;

import com.lbs.nettyserver.utils.sysutils.TimeUtil;

/**
 * 我的-获取我得到的评论列表响应类
 * @author visual
 *
 */
public class MyGetGetCommentListResponse {

	/**
	 * 现场消息id
	 */
	private String lmId;
	/**
	 * 现场消息内容
	 */
	private String liveMessageContent;
	
	
	/**
	 * 现场消息是否已删除-0未删除，1已删除
	 */
	private Character isDelete;
	
	/**
	 * 评论的内容
	 */
	private String commentContent;
	/**
	 * 评论id
	 */
	private String commentId;
	/**
	 * 评论时间
	 */
	private String commentTime;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 头像
	 */
	private String headImg;
	/**
	 * 性别
	 */
	private Character sex;
	/**
	 * 昵称
	 */
	private String nickName;

	public String getLmId() {
		return lmId;
	}

	public void setLmId(String lmId) {
		this.lmId = lmId;
	}

	public String getLiveMessageContent() {
		return liveMessageContent;
	}

	public void setLiveMessageContent(String liveMessageContent) {
		this.liveMessageContent = liveMessageContent;
	}
	
	

	public Character getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Character isDelete) {
		this.isDelete = isDelete;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public String getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(Date commentTime) {
		this.commentTime = TimeUtil.dateTimeFormatToString(commentTime);
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

	public Character getSex() {
		return sex;
	}

	public void setSex(Character sex) {
		this.sex = sex;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	
}
