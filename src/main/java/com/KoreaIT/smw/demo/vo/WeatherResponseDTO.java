package com.KoreaIT.smw.demo.vo;

import lombok.Data;

@Data
public class WeatherResponseDTO
{
    private WeatherHeaderDTO header;

    private WeatherBodyDTO   body;
}