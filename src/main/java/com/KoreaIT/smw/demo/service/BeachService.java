package com.KoreaIT.smw.demo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.KoreaIT.smw.demo.vo.Recommend;

@Service
public class BeachService {
    private static String News_URL = "";

    @PostConstruct
	public List<Recommend> getNewsDatas2() throws IOException {  
    	 
		News_URL = "https://search.naver.com/search.naver?where=image&sm=tab_jum&query=을왕리해수욕장";
		List<Recommend> newsList = new ArrayList<>();
        Document document = Jsoup.connect(News_URL).get();
        // 해당 html 구조를 입력
        Elements contents = document.select("#main_pack > section.sc_new.sp_nimage._prs_img._imageSearchPC > div > div.photo_group._listGrid > div.photo_tile._grid div.thumb");

        for (Element content : contents) {
        	Recommend recommend = Recommend.builder()
        			.image(content.select("a > img").attr("abs:src"))
        			.subject(content.select("a").text())		// 제목
                    .build();
            newsList.add(recommend);
        }

        return newsList;
	}
}
