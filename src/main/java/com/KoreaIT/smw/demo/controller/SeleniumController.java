package com.KoreaIT.smw.demo.controller;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SeleniumController {

    private String driverPath = "/Users/songminwoo/Downloads/chromedriver_mac64/chromedriver";
    private String URL = "https://search.naver.com/search.naver?where=image&sm=tab_jum&query=%EC%9D%84%EC%99%95%EB%A6%AC+%ED%95%B4%EC%88%98%EC%9A%95%EC%9E%A5";

    @GetMapping("/usr/home/asdf")
    public String getImageUrls(Model model) {
        List<String> imageUrls = new ArrayList<>();

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
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
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

        model.addAttribute("imageUrls", imageUrls);
        
        return "/usr/home/ex4";
    }
}