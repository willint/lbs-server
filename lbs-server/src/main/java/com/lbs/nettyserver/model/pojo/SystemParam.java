package com.lbs.nettyserver.model.pojo;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 系统参数
 * @author will
 *
 */
@Entity
@Table(name="t_system_param")
public class SystemParam {

	/**
	 * 主键
	 */
	@Id
    @Column(name = "id")
	private String Id;
	
	/**
	 * 参数名
	 */
	@Column(name = "param_name")
	private String paramName;
	/**
	 * 参数值
	 */
	@Column(name = "param_value")
	private String paramValue;
	/**
	 * 备注
	 */
	@Column(name = "remark")
	private String remark;
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	public String getParamValue() {
		return paramValue;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
