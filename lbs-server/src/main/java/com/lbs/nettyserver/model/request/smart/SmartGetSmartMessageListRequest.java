package com.lbs.nettyserver.model.request.smart;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 智囊-获取智囊消息列表请求类
 * @author visual
 *
 */
public class SmartGetSmartMessageListRequest {

	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 地理位置经度
	 */
	private BigDecimal locationJd;
	/**
	 * 地理位置纬度
	 */
	private BigDecimal locationWd;
	/**
	 * 智囊接收范围
	 */
	private BigDecimal smartRange;
	
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
	public BigDecimal getLocationJd() {
		return locationJd;
	}
	public void setLocationJd(BigDecimal locationJd) {
		this.locationJd = locationJd;
	}
	public BigDecimal getLocationWd() {
		return locationWd;
	}
	public void setLocationWd(BigDecimal locationWd) {
		this.locationWd = locationWd;
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
	public BigDecimal getSmartRange() {
		return smartRange;
	}
	public void setSmartRange(BigDecimal smartRange) {
		this.smartRange = smartRange;
	}

	
}
