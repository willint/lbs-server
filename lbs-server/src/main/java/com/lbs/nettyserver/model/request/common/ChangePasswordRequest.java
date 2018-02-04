package com.lbs.nettyserver.model.request.common;

/**
 * 密码修改类
 * @author visual
 *
 */
public class ChangePasswordRequest {

	private String userId;
	
	private String newPws;
	
	private String oldPws;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNewPws() {
		return newPws;
	}

	public void setNewPws(String newPws) {
		this.newPws = newPws;
	}

	public String getOldPws() {
		return oldPws;
	}

	public void setOldPws(String oldPws) {
		this.oldPws = oldPws;
	}
}
