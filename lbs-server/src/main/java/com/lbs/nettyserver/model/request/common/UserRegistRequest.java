package com.lbs.nettyserver.model.request.common;

/**
 * 用户注册请求类
 * @author visual
 *
 */
public class UserRegistRequest {

	/**
	 * 用户头像图片路径
	 */
	private String headImg;
	
	/**
	 * 性别
	 */
	private Character sex;
	/**
	 * 电话号码
	 */
	private String phone;
	/**
	 * 密码密文
	 */
	private String password;
	/**
	 * 验证码
	 */
	private String checkNum;
	
	/**
	 * 用户id
	 */
	private String userId;
	
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCheckNum() {
		return checkNum;
	}
	public void setCheckNum(String checkNum) {
		this.checkNum = checkNum;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	

}
