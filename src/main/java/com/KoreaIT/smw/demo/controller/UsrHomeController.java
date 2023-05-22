package com.KoreaIT.smw.demo.controller;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
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
	public String showMain(Model model) {
		List<String[]> data = new ArrayList<>();

	    /* /csv/beach2.csv에 있는 데이터를 읽어오는 방식 */
	    /* InputStream 클래스는 바이트 기반의 입력 스트림을 읽기 위한 추상 클래스
	     * . 이 클래스는 다양한 소스로부터 바이트 데이터를 읽어들이는 기능을 제공. */
	    try (InputStream inputStream = getClass().getResourceAsStream("/csv/beach2.csv");
	         InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
	    		/* BufferedReader는 입출력 성능을 향상시키기 위해 버퍼링을 사용한다.
	    		 *  버퍼링은 읽기 동작을 한 번에 여러 바이트 또는 문자열로 처리하여 시스템 호출 횟수를 줄이고 성능을 향상시킨다.
	    		 *   이를 통해 데이터를 효율적으로 읽을 수 있다. */
	         BufferedReader br = new BufferedReader(inputStreamReader);
	    		/* 
					ByteArrayOutputStream 클래스는 바이트 배열에 데이터를 쓰기 위한 출력 스트림이다. 
					데이터를 바이트 배열로 모으는 데 사용된다. */
	         ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    		/* OutputStreamWriter는 주로 텍스트 파일,
	    		 *  네트워크 통신 등에서 문자 데이터를 바이트 스트림으로 변환하여 출력할 때 사용된다. */
	         OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {

	        String line;
	        while ((line = br.readLine()) != null) {
	        	/* csv파일을 ,로 나눠서 data에 저장한다. */
	            String[] row = line.split(",");
	            data.add(row);
	        }

	        for (String[] row : data) {
	        	/* ,에 row와 개행문자를 넣어서  outputStreamWriter 에 작성한다. */
	            String joinedRow = String.join(",", row) + "\n";
	            outputStreamWriter.write(joinedRow);
	        }
	        /* flush() 메서드는 OutputStreamWriter가 내부적으로 유지하는 버퍼에 쌓여 있는 모든 문자 데이터를 출력 스트림으로 플러시(비우기)하는 역할을 한다. */
	        outputStreamWriter.flush();

	        /* UTF-8 인코딩의 BOM(Byte Order Mark) 값인 (byte) 0xEF, (byte) 0xBB, (byte) 0xBF를 포함하도록 초기화 */
	        byte[] bom = {(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};
	        /* toByteArray() 메서드를 사용하여 ByteArrayOutputStream에 기록된 데이터를 바이트 배열로 추출 */
	        byte[] csvBytes = outputStream.toByteArray();

	        // BOM 추가
	        /* BOM 바이트 배열(bom)과 CSV 데이터의 바이트 배열(csvBytes)을 합쳐서 하나의 결과 바이트 배열(result)을 생성하는 역할 */
	        byte[] result = new byte[bom.length + csvBytes.length];
	        /* System.arraycopy() 메서드를 사용하여 bom 배열의 내용을 result 배열의 처음부터 복사 */
	        System.arraycopy(bom, 0, result, 0, bom.length);
	        /* csvBytes 배열의 내용을 result 배열의 BOM 이후부터 복사 */
	        System.arraycopy(csvBytes, 0, result, bom.length, csvBytes.length);


	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    model.addAttribute("data", data);

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
	
	@RequestMapping("/usr/home/ex4")
	public String showMaina4() {
		return "usr/home/ex4";
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