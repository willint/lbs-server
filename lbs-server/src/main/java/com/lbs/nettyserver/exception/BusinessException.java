package com.lbs.nettyserver.exception;

/**
 * 业务异常
 *
 * @author will
 * @date 2018/01/04
 * @since 1.0.0
 *
 */
public class BusinessException extends Exception {

	private static final long serialVersionUID = -3877684764394468799L;

	/** 参数错误码 */
	public static final String ERROR_CODE_PARAM_ERROR = "PE01";

	/** 业务错误码 */
	public static final String ERROR_CODE_BUSINESS_ERROR = "BE01";

	private String errorCode;

	public BusinessException() {
		super();
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException(String errorCode, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}

	protected BusinessException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public String getErrorCode() {
		return this.errorCode;
	}
}
