package com.bizideal.mn.controller;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bizideal.mn.enums.States;
import com.bizideal.mn.utils.RandomUtils;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author : liulq
 * @date: 创建时间：2017年8月26日 下午1:45:01
 * @version: 1.0
 * @Description:
 */
@Controller
@RequestMapping("/")
public class HelloController {

	public static final ThreadLocal<String> states = new ThreadLocal<>();

	@Value("${github.client_id}")
	private String clictId;
	@Value("${github.client_secret}")
	private String clientSecret;
	@Value("${github.redirect_url}")
	private String redirectUrl;
	@Value("${com.bizideal.name}")
	private String name;
	@Value("${com.bizideal.title}")
	private String title;
	@Value("${com.bizideal.desc}")
	private String desc;

	@ApiOperation(value = "get", notes = "通过id查询", httpMethod = "GET")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "id", required = true), @ApiImplicitParam(name = "name", value = "名字", required = false) })
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String get(@PathVariable Integer id) {
		return "Hello World";
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("name", name);
		return "index";
	}

	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		return "login";
	}

	// 引导用户跳转github授权页面
	@RequestMapping("/github")
	public String toGithubLoginPage(HttpServletRequest request, HttpServletResponse response) {
		String state = RandomUtils.get();
		states.set(state);
		String url = "https://github.com/login/oauth/authorize?client_id=CLIENT_ID&state=STATE&redirect_uri=REDIRECT_URI";
		url = url.replace("CLIENT_ID", clictId).replace("STATE", state).replace("REDIRECT_URI", redirectUrl);
		return "redirect:" + url;
	}
}
