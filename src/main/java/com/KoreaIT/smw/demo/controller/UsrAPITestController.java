package com.KoreaIT.smw.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UsrAPITestController {

	/* 카카오 map openapi로 해변 검색을 위해 사용하는 url */
	@RequestMapping("/usr/home/MapSearch")
	public String APITest4(@RequestParam(defaultValue="해수욕장") String value, Model model) {
		model.addAttribute("value", value);
		return "usr/home/MapSearch";
	}

}
