package com.lbs.nettyserver.model.sys;

/**
 * 服务端code状态码和消息常量类
 * @author visual
 *
 */
public class CodeAndMsgConstants {
	/**
	 * 服务器内部错误
	 */
	public static final String sys_error_code="9999";
	/**
	 * 服务器内部错误
	 */
	public static final String sys_error_msg="系统繁忙，请稍候再试";
	
	/**
	 * 成功
	 */
	public static final String success_code="0000";
	/**
	 * 成功
	 */
	public static final String success_code_msg="成功";

	/**
	 * 参数错误
	 */
	public static final String param_error_code="0001";
	/**
	 * 参数错误
	 */
	public static final String param_error_msg="参数错误";
	
	/**
	 * session失效
	 */
	public static final String session_invalid_code="0002";
	/**
	 * 参数错误
	 */
	public static final String session_invalid_msg="session失效";
	/**
	 * 请求头验证失败
	 */
	public static final String requestHeader_error_code="0003";
	/**
	 *请求头验证失败
	 */
	public static final String requestHeader_error_msg="请求头验证失败";
	/**
	 * 请求格式不正确
	 */
	public static final String requestFormat_error_code="0004";
	/**
	 * 请求格式不正确
	 */
	public static final String requestFormat_error_msg="请求格式不正确";
	/**
	 * 请求解析失败
	 */
	public static final String requestAnalysis_error_code="0004";
	/**
	 * 请求解析失败
	 */
	public static final String requestAnalysis_error_msg="请求解析失败";
}
