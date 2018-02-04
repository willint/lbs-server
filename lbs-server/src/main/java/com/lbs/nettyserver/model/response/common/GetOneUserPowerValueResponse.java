package com.lbs.nettyserver.model.response.common;

import java.math.BigDecimal;
import java.util.Date;

import com.lbs.nettyserver.utils.sysutils.TimeUtil;

/**
 * 获取用户体力能量响应类
 * @author visual
 *
 */
public class GetOneUserPowerValueResponse {

	/**
	 * 主键
	 */
	private String tpvId;
	/**
	 * 体力值
	 */
	private BigDecimal tlValue;
	/**
	 * 能量值
	 */
	private BigDecimal nlValue;
	/**
	 * 时间
	 */
	private String updateTime;

	public String getTpvId() {
		return tpvId;
	}

	public void setTpvId(String tpvId) {
		this.tpvId = tpvId;
	}

	public BigDecimal getTlValue() {
		return tlValue;
	}

	public void setTlValue(BigDecimal tlValue) {
		this.tlValue = tlValue;
	}

	public BigDecimal getNlValue() {
		return nlValue;
	}

	public void setNlValue(BigDecimal nlValue) {
		this.nlValue = nlValue;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = TimeUtil.dateTimeFormatToString(updateTime);
	}
	
	
	
	
}
