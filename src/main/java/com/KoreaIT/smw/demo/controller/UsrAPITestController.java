package com.KoreaIT.smw.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UsrAPITestController {

	/* 카카오 map openapi로 해변 검색을 위해 사용하는 url */
	@RequestMapping("/usr/home/MapSearch")
	public String APITest4() {
		return "usr/home/MapSearch";
	}

}
