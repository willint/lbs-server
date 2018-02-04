package com.lbs.nettyserver.model.response.my;

/**
 * 我的-获取我的粉丝响应类
 * @author visual
 *
 */
public class MyGetFansListResponse {

	/**
	 * 用户id
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
	
	/**
	 * 关注我的时间
	 */
	private String attentionTime;
	
	/**
	 * 是否已经关注，0未关注，1已关注
	 */
	private String isAttention;

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

	public String getAttentionTime() {
		return attentionTime;
	}

	public void setAttentionTime(String attentionTime) {
		this.attentionTime = attentionTime;
	}

	public String getIsAttention() {
		return isAttention;
	}

	public void setIsAttention(String isAttention) {
		this.isAttention = isAttention;
	}

}
