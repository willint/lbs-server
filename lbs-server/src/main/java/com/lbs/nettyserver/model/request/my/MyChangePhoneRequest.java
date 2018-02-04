package com.lbs.nettyserver.model.request.my;

/**
 * 我的-用户修改手机号码请求类
 * @author visual
 *
 */
public class MyChangePhoneRequest {

	private String userId;
	
	private String phone;
	/**
	 * 验证码
	 */
	private String checkNum;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCheckNum() {
		return checkNum;
	}

	public void setCheckNum(String checkNum) {
		this.checkNum = checkNum;
	}
	
}
