package com.lbs.nettyserver.model.common;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * 现场发布的内容Content结构
 * @author visual
 *
 */
public class LiveMessageContent {

	/**
	 * 标题
	 */
	private String title;
	/**
	 * 简介
	 */
	private String describe;
	/**
	 * 媒体类型
	 */
	private String mediaType;
	/**
	 * 缩略图路径，如果有
	 */
	private String thumbPath;
	/**
	 * 视频时长
	 */
	private BigDecimal videoTime;
	/**
	 * 视频大小
	 */
	private BigDecimal videoSize;
	/**
	 * 图片路径
	 */
	private ArrayList<String> imgPathList;
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public String getMediaType() {
		return mediaType;
	}
	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}
	public String getThumbPath() {
		return thumbPath;
	}
	public void setThumbPath(String thumbPath) {
		this.thumbPath = thumbPath;
	}
	public BigDecimal getVideoTime() {
		return videoTime;
	}
	public void setVideoTime(BigDecimal videoTime) {
		this.videoTime = videoTime;
	}
	public BigDecimal getVideoSize() {
		return videoSize;
	}
	public void setVideoSize(BigDecimal videoSize) {
		this.videoSize = videoSize;
	}
	public ArrayList<String> getImgPathList() {
		return imgPathList;
	}
	public void setImgPathList(ArrayList<String> imgPathList) {
		this.imgPathList = imgPathList;
	} 

}
