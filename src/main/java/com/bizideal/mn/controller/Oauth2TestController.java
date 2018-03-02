package com.bizideal.mn.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bizideal.mn.utils.HttpClientUtils;
import com.google.gson.JsonObject;

/**
 * @author : liulq
 * @date: 创建时间：2017年8月30日 下午2:22:23
 * @version: 1.0
 * @Description:
 */
@Controller
@RequestMapping("/oauth")
public class Oauth2TestController {

	@Value("${github.client_id}")
	private String clictId;
	@Value("${github.client_secret}")
	private String clientSecret;
	@Value("${github.redirect_url}")
	private String redirectUrl;

	@GetMapping("/product/{id}")
	@ResponseBody
	public String getProduct(@PathVariable String id) {
		// for debug
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return "product id : " + id;
	}

	@GetMapping("/order/{id}")
	@ResponseBody
	public String getOrder(@PathVariable String id) {
		// for debug
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return "order id : " + id;
	}

	// github三方登陆回调地址
	@RequestMapping("/github")
	public String github(String code, String state) throws Exception {
		// 获取token
		String url = "https://github.com/login/oauth/access_token?client_id=CLIENT_ID&client_secret=CLIENT_SECRET&code=CODE";
		url = url.replace("CLIENT_ID", clictId).replace("CLIENT_SECRET", clientSecret).replace("CODE", code);
		JsonObject httpRequest = HttpClientUtils.httpRequest(url, "GET", null);
		System.out.println(httpRequest.toString());
		String accessToken = httpRequest.get("access_token").getAsString();
		// 用token获取用户信息
		url = "https://api.github.com/user?access_token=ACCESS_TOKEN";
		url = url.replace("ACCESS_TOKEN", accessToken);
		JsonObject httpRequest2 = HttpClientUtils.httpRequest(url, "GET", null);
		System.out.println(httpRequest2.toString());
		System.out.println(httpRequest2.get("login"));
		System.out.println(httpRequest2.get("name"));
		System.out.println(httpRequest2.get("company"));
		System.out.println(httpRequest2.get("email"));
		return "success";
	}
}
