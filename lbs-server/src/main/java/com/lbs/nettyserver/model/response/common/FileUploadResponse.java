package com.lbs.nettyserver.model.response.common;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 文件上传响应通用类
 * @author visual
 *
 */
@Data
public class FileUploadResponse {
	private List<String> data;
	private String flag;
	private String code;
	private String msg;
	private int size;
	public List<String> getData() {
		return data;
	}
	public void setData(List<String> data) {
		this.data = data;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
}
