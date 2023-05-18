package com.KoreaIT.smw.demo.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WeatherController {

	@GetMapping("/usr/home/weather")
	public String restApiGetWeather(Model model) throws Exception {
		/*
		 * @ API LIST ~
		 * 
		 * getUltraSrtNcst 초단기실황조회 getUltraSrtFcst 초단기예보조회 getVilageFcst 동네예보조회
		 * getFcstVersion 예보버전조회
		 */
		String url = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst"
				+ "?serviceKey=VrARjVnAz0YAUHJLVqj8GZ7XpxHHlvVUKCE1vRY0pWvbSavB0Pl0zPRI1waC0B3ZsJm5N%2FTlAhDP06JVqUokdw%3D%3D"
				+ "&dataType=JSON" // JSON, XML
				+ "&numOfRows=10" // 페이지 ROWS
				+ "&pageNo=1" // 페이지 번호
				+ "&base_date=20230517" // 발표일자
				+ "&base_time=0800" // 발표시각
				+ "&nx=60" // 예보지점 X 좌표
				+ "&ny=127"; // 예보지점 Y 좌표

		HashMap<String, Object> resultMap = getDataFromJson(url, "UTF-8", "get", "");

		System.out.println("# RESULT : " + resultMap);

		model.addAttribute("result", resultMap);

		return "/usr/home/weather";
	}

	public HashMap<String, Object> getDataFromJson(String url, String encoding, String type, String jsonStr)
			throws Exception {
		boolean isPost = false;

		if ("post".equals(type)) {
			isPost = true;
		} else {
			url = "".equals(jsonStr) ? url : url + "?request=" + jsonStr;
		}

		return getStringFromURL(url, encoding, isPost, jsonStr, "application/json");
	}public HashMap<String, Object> getStringFromURL(String url, String encoding, boolean isPost, String parameter, String contentType) throws Exception {
        URL apiURL = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) apiURL.openConnection();
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);
        conn.setUseCaches(false);
        conn.setRequestProperty("Content-Type", contentType);
        conn.setRequestProperty("Accept-Charset", encoding);

        if (isPost) {
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(), encoding);
            osw.write(parameter);
            osw.flush();
            osw.close();
        }

        int responseCode = conn.getResponseCode();
        BufferedReader in;
        if (responseCode == HttpURLConnection.HTTP_OK) {
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), encoding));
        } else {
            in = new BufferedReader(new InputStreamReader(conn.getErrorStream(), encoding));
        }

        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("responseCode", responseCode);
        resultMap.put("responseMessage", conn.getResponseMessage());
        resultMap.put("responseBody", response.toString());

        return resultMap;
    }
}