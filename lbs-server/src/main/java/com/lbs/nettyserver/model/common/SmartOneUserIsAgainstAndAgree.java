package com.lbs.nettyserver.model.common;

import java.math.BigInteger;

/**
 * 智囊-某位用户是否已经反对和同意过某一条智囊消息
 * @author visual
 *
 */
public class SmartOneUserIsAgainstAndAgree {

	/**
	 * 是否同意过，0未同意过，>0同意过
	 */
	private BigInteger isAgree;
	/**
	 * 是否反对过，0未反对过，>0反对过
	 */
	private BigInteger isAgainst;

	public BigInteger getIsAgree() {
		return isAgree;
	}

	public void setIsAgree(BigInteger isAgree) {
		this.isAgree = isAgree;
	}

	public BigInteger getIsAgainst() {
		return isAgainst;
	}

	public void setIsAgainst(BigInteger isAgainst) {
		this.isAgainst = isAgainst;
	}
	
	
}
