package com.lbs.nettyserver.model.response.my;

import java.util.Date;

import com.lbs.nettyserver.utils.sysutils.TimeUtil;

/**
 * 我的-获取我点出的赞的详情列表响应类
 * @author visual
 *
 */
public class MyGetPutZanListResponse {

	/**
	 * 赞的id
	 */
	private String zanId;
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
	 *我点赞的时间
	 */
	private String zanTime;
	/**
	 * 我赞的用户id
	 */
	private String userId;
	/**
	 * 昵称
	 */
	private String nickName;
	/**
	 * 性别
	 */
	private Character sex;
	/**
	 * 头像
	 */
	private String headImg;
	
	public String getZanId() {
		return zanId;
	}
	public void setZanId(String zanId) {
		this.zanId = zanId;
	}
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
	public String getZanTime() {
		return zanTime;
	}
	public void setZanTime(Date zanTime) {
		this.zanTime = TimeUtil.dateTimeFormatToString(zanTime);
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
	public Character getSex() {
		return sex;
	}
	public void setSex(Character sex) {
		this.sex = sex;
	}
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
}
