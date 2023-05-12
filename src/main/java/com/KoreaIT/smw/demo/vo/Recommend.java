package com.KoreaIT.smw.demo.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Recommend {
	private String image;
    private String subject;
    private String url;
}
