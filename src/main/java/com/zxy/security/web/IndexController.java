package com.zxy.security.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	@RequestMapping(value= {"/home","/"})
	public String index() {
		return "home";
	}
}
