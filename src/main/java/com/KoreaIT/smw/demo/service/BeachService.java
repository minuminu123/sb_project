package com.KoreaIT.smw.demo.service;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import com.KoreaIT.smw.demo.vo.Recommend;

@Service
public class BeachService {
    private static String News_URL = "";

    @PostConstruct
	public List<Recommend> getNewsDatas2() throws IOException {  
    	
		News_URL = "https://search.naver.com/search.naver?where=image&sm=tab_jum&query=%EC%9D%84%EC%99%95%EB%A6%AC+%ED%95%B4%EC%88%98%EC%9A%95%EC%9E%A5";
		List<Recommend> newsList = new ArrayList<>();
        Document document = Jsoup.connect(News_URL).get();
        // 해당 html 구조를 입력
        Elements contents = document.select("#main_pack > section.sc_new.sp_nimage._prs_img._imageSearchPC > div > div.photo_group._listGrid > div.photo_tile._grid > div.til_item._item");

        for (Element content : contents) {
        	Recommend recommend = Recommend.builder()
        			.image(content.select("img._image").attr("src"))
        			.subject(content.select("a").text())		// 제목
                    .build();
            newsList.add(recommend);
        }

        return newsList;
	}

	public List<String> getImg(String name) {
		List<String> imageUrls = new ArrayList<>();
		String driverPath = "/Users/songminwoo/Downloads/chromedriver_mac64/chromedriver";
		String URL = "https://search.naver.com/search.naver?where=image&sm=tab_jum&query=" + name;
        // 크롬 드라이버 경로 설정
        System.setProperty("webdriver.chrome.driver", driverPath);

        // 크롬 옵션 설정
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-popup-blocking");   // 팝업 안띄움
        options.addArguments("headless");   // 브라우저 안띄움
        options.addArguments("--disable-gpu");  // gpu 비활성화
//        options.addArguments("--blink-settings=imagesEnabled=false");   // 이미지 다운 안받음
        options.addArguments("--remote-allow-origins=*");    // 이거 붙여야함
        // 웹 드라이버 생성
        WebDriver driver = new ChromeDriver(options);

        try {
            // 웹 페이지 열기
            driver.get(URL);

            // 이미지 요소 선택을 위해 일정 시간 대기
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("html img._image._listImage")));

            // 이미지 요소 선택
            List<WebElement> imageElements = driver.findElements(By.cssSelector("html img._image._listImage"));

            // 이미지 URL 가져오기
            for (int i = 0; i < 4 && i < imageElements.size(); i++) {
                WebElement imageElement = imageElements.get(i);
                String imageUrl = imageElement.getAttribute("src");
                imageUrls.add(imageUrl);
            }
        } finally {
            // 드라이버 종료
            driver.quit();
        }
        
        return imageUrls;
        
	}
}
