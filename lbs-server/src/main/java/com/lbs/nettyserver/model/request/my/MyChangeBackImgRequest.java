package com.lbs.nettyserver.model.request.my;

/**
 * 用户修改背景图片请求类
 * @author visual
 *
 */
public class MyChangeBackImgRequest {
	/**
	 * 背景图片相对路径
	 */
	private String backImgSrc;
	/**
	 * 用户id
	 */
	private String userId;
	public String getBackImgSrc() {
		return backImgSrc;
	}
	public void setBackImgSrc(String backImgSrc) {
		this.backImgSrc = backImgSrc;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	

}
