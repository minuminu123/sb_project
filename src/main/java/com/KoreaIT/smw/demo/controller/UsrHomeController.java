package com.KoreaIT.smw.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.smw.demo.vo.Article;

@Controller
public class UsrHomeController {

	@RequestMapping("/usr/home/main")
	public String showMain() {
		return "usr/home/main";
	}

	@RequestMapping("/")
	public String showRoot() {
		return "redirect:/usr/home/main";
	}

	@RequestMapping("/usr/home/ex")
	public String showMaina() {
		return "usr/home/ex";
	}
	
	@RequestMapping("/usr/home/ex2")
	public String showMaina2() {
		return "usr/home/ex2";
	}
}