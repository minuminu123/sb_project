package com.KoreaIT.smw.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Weather {
	private String title;
	private String link;
	private String originallink;
	private String description;
	private String pubDate;

}
