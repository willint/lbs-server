package com.lbs.nettyserver.model.pojo;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户体力能量表
 * @author visual
 *
 */
@Entity
@Table(name="t_power_value")
public class PowerValue {

	/**
	 * 主键
	 */
	@Id
    @Column(name = "tpv_id")
	private String tpvId;
	
	/**
	 * 用户id
	 */
	@Column(name = "user_id")
	private String userId;
	
	/**
	 * 体力值
	 */
    @Column(name = "tl_value")
	private BigDecimal tlValue;
	
    /**
     * 能量值
     */
    @Column(name = "nl_value")
	private BigDecimal nlValue;
	
    /**
     * 更新时间
     */
    @Column(name = "update_time")
	private Date updateTime;

	

	public String getTpvId() {
		return tpvId;
	}

	public void setTpvId(String tpvId) {
		this.tpvId = tpvId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
    
    
}
