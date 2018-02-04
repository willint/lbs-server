package com.lbs.nettyserver.model.sys;

/**
 * 验证码参数常量类
 * @author visual
 *
 */
public class VerificationCodeConstants {

	/**
	 * 生成验证码长度
	 */
	public static Integer checkNum_length=6;
	/**
	 * 验证码有效时间：秒
	 */
	public static Long checkNum_effective_time=600L;
	/**
	 * 第三方发送验证码的服务地址
	 */
	public static String serverUrl="http://gw.api.taobao.com/router/rest";
	/**
	 * 第三方发送验证码的appkey
	 */
	public static String appKey="24441956";
	/**
	 * 第三方发送验证码的appSecret
	 */
	public static String appSecret="ef6dd7ec8237320391ccf248df97c9ba";
	/**
	 * 第三方发送验证码的信息类型
	 */
	public static String smsType="normal";
	/**
	 * 第三方发送验证码签名
	 */
	public static String smsFreeSignName="知瓣";
	/**
	 * 第三方发送验证码模板编号
	 */
	public static String smsTemplateCode="SMS_121980034";
}
