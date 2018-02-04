package com.lbs.nettyserver.model.request.my;

import java.math.BigInteger;

/**
 * 我的-获取我得到的评论列表请求类
 * @author visual
 *
 */
public class MyGetGetCommentListRequest {

	/**
	 * 用户id
	 */
	private String userId;
	
	/**
	 * 分页查询获取的数据条数
	 */
	private BigInteger limit;
	/**
	 * 分页查询获取的数据起点
	 */
	private BigInteger offset;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
