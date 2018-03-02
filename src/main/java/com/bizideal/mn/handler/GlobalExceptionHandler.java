package com.bizideal.mn.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author : liulq
 * @date: 创建时间：2017年8月26日 下午3:58:09
 * @version: 1.0
 * @Description:
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	public static final String DEFAULT_ERROR_VIEW = "error";

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
		e.printStackTrace();
		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", e);
		mav.addObject("url", req.getRequestURL());
		mav.setViewName(DEFAULT_ERROR_VIEW);
		return mav;
	}
}
