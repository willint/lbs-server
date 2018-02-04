package com.lbs.nettyserver.model.pojo;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 例外接口
 * @author will
 *
 */
@Entity
@Table(name="t_system_case_method")
public class SystemCaseMethod {

	/**
	 * 主键
	 */
	@Id
    @Column(name = "id")
	private String Id;
	
	/**
	 * 方法编码
	 */
	@Column(name = "method_code")
	private String methodCode;
	/**
	 * 方法名
	 */
	@Column(name = "method_name")
	private String methodName;
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
	public String getMethodCode() {
		return methodCode;
	}
	public void setMethodCode(String methodCode) {
		this.methodCode = methodCode;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
