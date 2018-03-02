package com.bizideal.mn.exception;

/**
 * @author : liulq
 * @date: 创建时间：2017年8月28日 下午6:45:04
 * @version: 1.0
 * @Description: 自定义异常
 */
public class CommonException extends Exception {

	private static final long serialVersionUID = 5798884171577391873L;

	private Integer code;
	private String msg;

	public CommonException() {
		super();
	}

	public CommonException(String msg) {
		super();
		this.msg = msg;
	}

	public CommonException(Integer code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "{\"code\":\"" + code + "\", \"msg\":\"" + msg + "\"} ";
	}

}
