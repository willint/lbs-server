package com.lbs.nettyserver.model.request.my;

/**
 * 用户修改头像请求类
 * @author visual
 *
 */
public class MyChangeHeaderImgRequest {

	/**
	 * 图像照片相对路径
	 */
	private String headerImgSrc;
	/**
	 * 用户id
	 */
	private String userId;
	
	public String getHeaderImgSrc() {
		return headerImgSrc;
	}
	public void setHeaderImgSrc(String headerImgSrc) {
		this.headerImgSrc = headerImgSrc;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
