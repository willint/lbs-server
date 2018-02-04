package com.lbs.nettyserver.model.request.vomit;

import java.math.BigInteger;

/**
 * 吐槽-专题获取我创建的专题请求类
 * @author visual
 *
 */
public class VomitSpecialGetCreatedSpecialRequest {

	private String userId;
	
	private BigInteger limit;
	
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
