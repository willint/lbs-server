package com.lbs.nettyserver.model.sys;

import java.math.BigDecimal;

public class UserInfoDefaultValueConstants {

	/**
	 * 吐槽初始等级
	 */
	public static BigDecimal VOMIT_LV_DEFAULT_VALUE=BigDecimal.valueOf(100);
	/**
	 * 现场得分初始值
	 */
	public static BigDecimal LIVE_SCORE_DEFAULT_VALUE=BigDecimal.ZERO;
	/**
	 * 用户个人中心默认背景图片
	 */
	public static String USER_BACKIMG_DEFAULT_VALUE="backImg.jpg";
	/**
	 * 用户默认头像
	 */
	public static String USER_HEADIMG_DEFAULT_VALUE="hedImg.jpg";
	/**
	 * 未注册用户标识0
	 */
	public static Character USER_IS_NO_REGIST='0';
	/**
	 * 已注册呢用户标识1
	 */
	public static Character USER_IS_REGIST='1';
	
	
}
