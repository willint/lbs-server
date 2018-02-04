package com.lbs.nettyserver.model.request.common;

/**
 * 用户登录请求类
 * @author visual
 *
 */
public class UserLoginRequest {
	/**
	 * 用户登录时的电话号码
	 */
	private String phone;
	/**
	 * 用户登录时的密码
	 */
	private String password;
	
	
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
	
}
