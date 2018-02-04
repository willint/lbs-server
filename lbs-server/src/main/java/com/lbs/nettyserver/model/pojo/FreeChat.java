package com.lbs.nettyserver.model.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 吐槽-自由消息实体类
 * @author visual
 *
 */
@Entity
@Table(name="t_free_chat")
public class FreeChat {
	/**
	 * 主键
	 */
	@Id
    @Column(name = "fc_id")
	private String fcId;
	/**
	 * 用户ID
	 */
	@Column(name = "login_id")
	private String loginId;
	/**
	 * 经度
	 */
	@Column(name = "send_jd")
	private BigDecimal jd;
	/**
	 * 纬度
	 */
	@Column(name = "send_wd")
	private BigDecimal wd;
	/**
	 * 发布时间
	 */
	@Column(name = "send_time")
	private Date sendTime;
	/**
	 * 预留
	 */
	@Column(name = "free0")
	private String free0;
	/**
	 * 预留
	 */
	@Column(name = "free1")
	private String free1;
	/**
	 * 具体的消息内容
	 */
	@Column(name = "content")
	private String content;

	/**
	 *撤回标识
	 */
	@Column(name = "is_back")
	private boolean isBack;

	/**
	 *发送标识
	 */
	@Column(name = "is_sended")
	private boolean isSend;


	public String getFcId() {
		return fcId;
	}

	public void setFcId(String fcId) {
		this.fcId = fcId;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
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

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isBack() {
		return isBack;
	}

	public void setBack(boolean back) {
		isBack = back;
	}

	public boolean isSend() {
		return isSend;
	}

	public void setSend(boolean send) {
		isSend = send;
	}
}
