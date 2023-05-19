package com.KoreaIT.smw.demo.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.KoreaIT.smw.demo.vo.AreaRequestDTO;
import com.KoreaIT.smw.demo.vo.WeatherDTO;

@Mapper
public interface WeatherRepository
{
    List<AreaRequestDTO> selectArea(Map<String, String> params);
    AreaRequestDTO selectCoordinate(String areacode);

    List<WeatherDTO> selectSameCoordinateWeatherList(AreaRequestDTO areaRequestDTO);

    void insertWeatherList(List<WeatherDTO> weatherList);
}