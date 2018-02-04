package com.lbs.nettyserver.model.response.my;

import java.math.BigDecimal;

/**
 * 我的-获取用户关注的人的数量（我关注的人）响应类
 * @author visual
 *
 */
public class MyGetAttentionTotalResponse {

	private BigDecimal attentionTotal;

	public BigDecimal getAttentionTotal() {
		return attentionTotal;
	}

	public void setAttentionTotal(BigDecimal attentionTotal) {
		this.attentionTotal = attentionTotal;
	}
}
