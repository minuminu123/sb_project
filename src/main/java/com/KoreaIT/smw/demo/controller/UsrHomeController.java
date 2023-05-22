package com.KoreaIT.smw.demo.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.KoreaIT.smw.demo.vo.Weather;
import com.KoreaIT.smw.demo.vo.WeatherResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	
	@RequestMapping("/usr/home/ex3")
	public String showMaina3() {
		return "usr/home/ex3";
	}

	@RequestMapping("/usr/home/news")
	public String TWeather(String text, Model model) {
		// 네이버 뉴스 검색 API 요청
		String clientId = "FTz2f8retxTdxp8Vssbr";
		String clientSecret = "AaaoVfoyAY";

		// String apiURL = "https://openapi.naver.com/v1/search/blog?query=" + text; //
		// JSON 결과
		URI uri = UriComponentsBuilder.fromUriString("https://openapi.naver.com").path("/v1/search/news.json")
				.queryParam("query", text.trim()).queryParam("display", 10).queryParam("start", 1).queryParam("sort", "sim")
				.encode().build().toUri();

		// Spring 요청 제공 클래스
		RequestEntity<Void> req = RequestEntity.get(uri).header("X-Naver-Client-Id", clientId)
				.header("X-Naver-Client-Secret", clientSecret).build();
		// Spring 제공 restTemplate
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> resp = restTemplate.exchange(req, String.class);

		// JSON 파싱 (Json 문자열을 객체로 만듦, 문서화)
		ObjectMapper om = new ObjectMapper();
		WeatherResult resultVO = null;

		try {
			resultVO = om.readValue(resp.getBody(), WeatherResult.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		List<Weather> WeatherList = resultVO.getItems(); // weatherList를 list.html에 출력 -> model 선언
		model.addAttribute("WeatherList", WeatherList);

		return "/usr/home/news";
	}

}