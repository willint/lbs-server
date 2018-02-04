package com.lbs.nettyserver.model.request.common;

/**
 * 检查验证码是否正确请求参数类
 * @author visual
 *
 */
public class CheckVerificationCodeIsRightRequest {

	/**
	 * 电话号码
	 */
	private String phone;
	/**
	 * 验证码
	 */
	private String checkNum;
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
