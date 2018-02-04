package com.lbs.nettyserver.model.request.live;

import java.math.BigDecimal;

/**
 * 现场-获取轨迹现场消息请求类
 * @author visual
 *
 */
public class LiveGetPathMessageRequest {

	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 用户经度
	 */
	private BigDecimal userJd;
	/**
	 * 用户纬度
	 */
	private BigDecimal userWd;
	
	/**
	 * 路径的纬度数组
	 */
	private double[] pathWdArray;
	
	/**
	 * 路径的经度数组
	 */
	private double[] pathJdArray;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public BigDecimal getUserJd() {
		return userJd;
	}

	public void setUserJd(BigDecimal userJd) {
		this.userJd = userJd;
	}

	public BigDecimal getUserWd() {
		return userWd;
	}

	public void setUserWd(BigDecimal userWd) {
		this.userWd = userWd;
	}

	public double[] getPathWdArray() {
		return pathWdArray;
	}

	public void setPathWdArray(double[] pathWdArray) {
		this.pathWdArray = pathWdArray;
	}

	public double[] getPathJdArray() {
		return pathJdArray;
	}

	public void setPathJdArray(double[] pathJdArray) {
		this.pathJdArray = pathJdArray;
	}

	
}
