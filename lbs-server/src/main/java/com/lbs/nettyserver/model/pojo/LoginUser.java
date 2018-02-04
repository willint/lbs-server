package com.lbs.nettyserver.model.pojo;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户登录状态
 * @author visual
 *
 */
@Entity
@Table(name="t_login_user")
public class LoginUser {

	/**
	 * 主键
	 */
	@Id
    @Column(name = "login_id")
	private String loginId;
	
	/**
	 * 用户ID
	 */
	@Column(name = "user_id")
	private String userId;
	/**
	 * token
	 */
	@Column(name = "token")
	private String token;
	/**
	 * 客户端IP地址
	 */
	@Column(name = "ip")
	private String ip;
	/**
	 * 客户端端口号
	 */
	@Column(name = "port")
	private Integer port;
	/**
	 * 是否在线false不在线，true在线
	 */
	@Column(name = "is_online")
	private Boolean isOnline;
	/**
	 * 经度
	 */
	@Column(name = "jd")
	private BigDecimal jd;
	/**
	 * 纬度
	 */
	@Column(name = "wd")
	private BigDecimal wd;
	/**
	 * 吐槽接收范围
	 */
	@Column(name = "vomit_range")
	private BigDecimal vomitRange;
	/**
	 * 现场接收范围
	 */
	@Column(name = "live_range")
	private BigDecimal liveRange;	
	/**
	 * 最后一次更新状态时间
	 */
	@Column(name = "last_time")
	private Date lastTime;
	/**
	 * 预留字段
	 */
	@Column(name = "free0")
	private String free0;
	/**
	 * 预留字段
	 */
	@Column(name = "free1")
	private String free1;
	
	/**
	 * 是否是注册用户
	 */
	@Column(name = "is_regist")
	private Character isRegist;

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip=ip;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public Boolean getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(Boolean isOnline) {
		this.isOnline = isOnline;
	}

	public BigDecimal getJd() {
		return jd;
	}

	public void setJd(BigDecimal jd) {
		this.jd = jd;
	}

	public BigDecimal getWd() {
		return wd;
	}

	public void setWd(BigDecimal wd) {
		this.wd = wd;
	}

	public BigDecimal getVomitRange() {
		return vomitRange;
	}

	public void setVomitRange(BigDecimal vomitRange) {
		this.vomitRange = vomitRange;
	}

	public BigDecimal getLiveRange() {
		return liveRange;
	}

	public void setLiveRange(BigDecimal liveRange) {
		this.liveRange = liveRange;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public String getFree0() {
		return free0;
	}

	public void setFree0(String free0) {
		this.free0 = free0;
	}

	public String getFree1() {
		return free1;
	}

	public void setFree1(String free1) {
		this.free1 = free1;
	}

	public Character getIsRegist() {
		return isRegist;
	}

	public void setIsRegist(Character isRegist) {
		this.isRegist = isRegist;
	}
}
