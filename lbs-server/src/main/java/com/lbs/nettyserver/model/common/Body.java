package com.lbs.nettyserver.model.common;

import net.sf.json.JSONObject;

public class Body {
	
	private String resultCode;
	
	private String message;
	
	private String interfaceCode;
	
	private String interfaceName;
	
	private String interfaceEntity;
	
	private JSONObject data;

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getInterfaceCode() {
		return interfaceCode;
	}

	public void setInterfaceCode(String interfaceCode) {
		this.interfaceCode = interfaceCode;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getInterfaceEntity() {
		return interfaceEntity;
	}

	public void setInterfaceEntity(String interfaceEntity) {
		this.interfaceEntity = interfaceEntity;
	}

	public JSONObject getData() {
		return data;
	}

	public void setData(JSONObject data) {
		this.data = data;
	}

}
