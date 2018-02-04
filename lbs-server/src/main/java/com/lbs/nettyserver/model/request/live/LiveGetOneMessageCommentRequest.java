package com.lbs.nettyserver.model.request.live;

import java.math.BigDecimal;

/**
 * 现场-获取现场某一条消息的评论请求类
 * @author visual
 *
 */
public class LiveGetOneMessageCommentRequest {

	/**
	 * 消息id
	 */
	private String lmId;
	/**
	 * 请求多少条数据
	 */
	private BigDecimal limit;
	/**
	 * 从哪一条数据开始
	 */
	private BigDecimal offset;
	
	
	public String getLmId() {
		return lmId;
	}
	public void setLmId(String lmId) {
		this.lmId = lmId;
	}
	public BigDecimal getLimit() {
		return limit;
	}
	public void setLimit(BigDecimal limit) {
		this.limit = limit;
	}
	public BigDecimal getOffset() {
		return offset;
	}
	public void setOffset(BigDecimal offset) {
		this.offset = offset;
	}
}
