package com.lbs.nettyserver.model.request.smart;

import java.math.BigInteger;

/**
 * 智囊-获取某一条消息的评论列表请求类
 * @author visual
 *
 */
public class SmartGetOneMessageCommentListRequest {

	/**
	 * 智囊消息id
	 */
	private String smId;
	
	/**
	 * 分页查询获取的数据条数
	 */
	private BigInteger limit;
	/**
	 * 分页查询获取的数据起点
	 */
	private BigInteger offset;
	public String getSmId() {
		return smId;
	}
	public void setSmId(String smId) {
		this.smId = smId;
	}
	public BigInteger getLimit() {
		return limit;
	}
	public void setLimit(BigInteger limit) {
		this.limit = limit;
	}
	public BigInteger getOffset() {
		return offset;
	}
	public void setOffset(BigInteger offset) {
		this.offset = offset;
	}
}
