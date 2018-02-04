package com.lbs.nettyserver.model.common;

import java.util.ArrayList;

/**
 * 智囊-发表评论的内容结构
 * @author visual
 *
 */
public class SmartMessageCommentContent {

	/**
	 * 评论的contentMsg类型：纯文本、文本+表情等
	 */
	private String commentType;
	/**
	 * 评论的内容
	 */
	private String contentMsg;
	
	/**
	 * 图片路径,如果有
	 */
	private ArrayList<String> imgPathList;
	
	
	public String getCommentType() {
		return commentType;
	}
	public void setCommentType(String commentType) {
		this.commentType = commentType;
	}
	public String getContentMsg() {
		return contentMsg;
	}
	public void setContentMsg(String contentMsg) {
		this.contentMsg = contentMsg;
	}
	public ArrayList<String> getImgPathList() {
		return imgPathList;
	}
	public void setImgPathList(ArrayList<String> imgPathList) {
		this.imgPathList = imgPathList;
	}
}
