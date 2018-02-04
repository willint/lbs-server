package com.lbs.nettyserver.model.response.common;

import org.springframework.beans.BeanUtils;

import com.lbs.nettyserver.model.pojo.User;

/**
 * 用户注册结果响应类
 * @author visual
 *
 */
public class UserRegistResponse extends User{
	
public UserRegistResponse(User user){
	BeanUtils.copyProperties(user, this);
	this.setPassword("");
}
}
