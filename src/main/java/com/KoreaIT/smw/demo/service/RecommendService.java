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
public class RecommendService {
    private static String News_URL = "https://search.naver.com/search.naver?query=%ED%95%B4%EC%88%98%EC%9A%95%EC%9E%A5&sm=tab_nmr&where=influencer";

    @PostConstruct
    public List<Recommend> getNewsDatas() throws IOException {
        List<Recommend> newsList = new ArrayList<>();
        Document document = Jsoup.connect(News_URL).get();

        Elements contents = document.select(".keyword_challenge_wrap ul > li ");

        for (Element content : contents) {
        	Recommend recommend = Recommend.builder()
//        			.image("https://picsum.photos/200/300?random=1")
//                    .image("/resource/main-beach.jpg") // 이미지
                    .subject(content.select("a.name_link._foryou_trigger").text())		// 제목
                    .url(content.select(".dsc_area > a").attr("abs:href"))		// 링크
                    .build();
            newsList.add(recommend);
        }

        return newsList;
    }
}