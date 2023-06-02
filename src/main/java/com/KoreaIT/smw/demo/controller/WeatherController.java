package com.KoreaIT.smw.demo.controller;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.smw.demo.service.WeatherService;
import com.KoreaIT.smw.demo.vo.AreaRequestDTO;
import com.KoreaIT.smw.demo.vo.WeatherDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Controller
public class WeatherController
{
	@Autowired
    private WeatherService weatherService;

    @GetMapping("/usr/home/weather")
    public String openWeatherPage()
    {
        return "/usr/home/weather";
    }

    /* 날씨 api로 검색한 지역의 날씨를 조회하는 url */
    @PostMapping(value = "/board/getWeather.do")
    @ResponseBody
    public List<WeatherDTO> getWeatherInfo(@ModelAttribute AreaRequestDTO areaRequestDTO) throws JsonMappingException, JsonProcessingException, UnsupportedEncodingException, URISyntaxException
    {
    	/* getAreaCode함수로 정보를 가져온다. */
        AreaRequestDTO coordinate = weatherService.getCoordinate(areaRequestDTO.getAreacode());
        areaRequestDTO.setNx(coordinate.getNx());
        areaRequestDTO.setNy(coordinate.getNy());

        List<WeatherDTO> weatherList = weatherService.getWeather(areaRequestDTO);
        return weatherList;
    }


    @PostMapping(value = "/board/weatherStep.do")
    @ResponseBody
    public List<AreaRequestDTO> getAreaStep(@RequestParam Map<String, String> params)
    {
        return weatherService.getArea(params);
    }
}