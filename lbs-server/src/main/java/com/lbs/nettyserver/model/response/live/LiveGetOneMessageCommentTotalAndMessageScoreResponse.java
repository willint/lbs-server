package com.lbs.nettyserver.model.response.live;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 现场-消息评论总数和消息质量得分响应类
 * @author visual
 *
 */
public class LiveGetOneMessageCommentTotalAndMessageScoreResponse {

	/**
	 * 评论总数
	 */
	private BigInteger commentTotal;
	/**
	 * 消息质量得分
	 */
	private BigDecimal messageScore;
	
	public BigInteger getCommentTotal() {
		return commentTotal;
	}
	public void setCommentTotal(BigInteger commentTotal) {
		this.commentTotal = commentTotal;
	}
	public BigDecimal getMessageScore() {
		return messageScore;
	}
	public void setMessageScore(BigDecimal messageScore) {
		this.messageScore = messageScore;
	}
	
	
}
