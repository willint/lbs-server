package com.lbs.nettyserver.model.pojo;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 技能实体类
 * @author visual
 *
 */
@Entity
@Table(name="t_func_set")
public class FuncSet {

	/**
	 * 主键
	 */
	@Id
    @Column(name = "tfd_id")
	private String tfdId;
	
	/**
	 * 技能编码
	 */
	@Column(name = "code")
	private String code;
	
	/**
	 * 技能路径
	 */
	@Column(name = "path")
	private String path;
	
	/**
	 * 技能的攻击值
	 */
	@Column(name = "gjz")
	private BigDecimal gjz;
	
	/**
	 * 技能每天可发送的次数
	 */
	@Column(name = "count")
	private BigInteger count;
	
	/**
	 * 技能冷却时间，单位秒
	 */
	@Column(name = "cd")
	private BigInteger cd;
	
	/**
	 * 对应的吐槽等级
	 */
	@Column(name = "levl_val")
	private BigDecimal levelValue;
	/**
	 * 发送技能需要消耗的能量值
	 */
	@Column(name = "nl_val")
	private BigDecimal nlValue;
	/**
	 * 发送技能需要消耗的体力值
	 */
	@Column(name = "tl_val")
	private BigDecimal tlValue;
	
	/**
	 * 技能的名称
	 */
	@Column(name = "name")
	private String name;
	
	/**
	 * 技能媒体文件类型，图片，视频等
	 */
	@Column(name = "media_type")
	private String mediaType;
	
	/**
	 * 可用状态技能图标
	 */
	@Column(name = "usable_icon")
	private String usableIcon;
	
	/**
	 * 不可用状态技能图标
	 */
	@Column(name = "unusable_icon")
	private String unusableIcon;
	
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

	public String getTfdId() {
		return tfdId;
	}

	public void setTfdId(String tfdId) {
		this.tfdId = tfdId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public BigDecimal getGjz() {
		return gjz;
	}

	public void setGjz(BigDecimal gjz) {
		this.gjz = gjz;
	}

	public BigInteger getCount() {
		return count;
	}

	public void setCount(BigInteger count) {
		this.count = count;
	}

	public BigInteger getCd() {
		return cd;
	}

	public void setCd(BigInteger cd) {
		this.cd = cd;
	}

	public BigDecimal getLevelValue() {
		return levelValue;
	}

	public void setLevelValue(BigDecimal levelValue) {
		this.levelValue = levelValue;
	}

	public BigDecimal getNlValue() {
		return nlValue;
	}

	public void setNlValue(BigDecimal nlValue) {
		this.nlValue = nlValue;
	}

	public BigDecimal getTlValue() {
		return tlValue;
	}

	public void setTlValue(BigDecimal tlValue) {
		this.tlValue = tlValue;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
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

	public String getUsableIcon() {
		return usableIcon;
	}

	public void setUsableIcon(String usableIcon) {
		this.usableIcon = usableIcon;
	}

	public String getUnusableIcon() {
		return unusableIcon;
	}

	public void setUnusableIcon(String unusableIcon) {
		this.unusableIcon = unusableIcon;
	}
}
