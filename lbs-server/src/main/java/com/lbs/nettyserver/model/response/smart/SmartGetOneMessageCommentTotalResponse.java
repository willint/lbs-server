package com.lbs.nettyserver.model.response.smart;

import java.math.BigInteger;

/**
 * 获取某一条智囊消息的评论总数响应类
 * @author visual
 *
 */
public class SmartGetOneMessageCommentTotalResponse {

	/**
	 * 评论总数
	 */
	private BigInteger smartCommentCount;

	public BigInteger getSmartCommentCount() {
		return smartCommentCount;
	}

	public void setSmartCommentCount(BigInteger smartCommentCount) {
		this.smartCommentCount = smartCommentCount;
	}
}
