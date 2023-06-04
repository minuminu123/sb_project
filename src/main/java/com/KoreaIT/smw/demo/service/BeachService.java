package com.KoreaIT.smw.demo.service;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.nio.charset.StandardCharsets;
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
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.KoreaIT.smw.demo.vo.Blog;
import com.KoreaIT.smw.demo.vo.BlogResult;
import com.KoreaIT.smw.demo.vo.Recommend;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class BeachService {
	private static String News_URL = "";

	@PostConstruct
	public List<Recommend> getNewsDatas2() throws IOException {

		News_URL = "https://search.naver.com/search.naver?where=image&sm=tab_jum&query=%EC%9D%84%EC%99%95%EB%A6%AC+%ED%95%B4%EC%88%98%EC%9A%95%EC%9E%A5";
		List<Recommend> newsList = new ArrayList<>();
		Document document = Jsoup.connect(News_URL).get();
		// 해당 html 구조를 입력
		Elements contents = document.select(
				"#main_pack > section.sc_new.sp_nimage._prs_img._imageSearchPC > div > div.photo_group._listGrid > div.photo_tile._grid > div.til_item._item");

		for (Element content : contents) {
			Recommend recommend = Recommend.builder().image(content.select("img._image").attr("src"))
					.subject(content.select("a").text()) // 제목
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
		options.addArguments("--disable-popup-blocking"); // 팝업 안띄움
		options.addArguments("headless"); // 브라우저 안띄움
		options.addArguments("--disable-gpu"); // gpu 비활성화
//        options.addArguments("--blink-settings=imagesEnabled=false");   // 이미지 다운 안받음
		options.addArguments("--remote-allow-origins=*"); // 이거 붙여야함
		// 웹 드라이버 생성
		WebDriver driver = new ChromeDriver(options);

		try {
			// 웹 페이지 열기
			driver.get(URL);

			// 이미지 요소 선택을 위해 일정 시간 대기
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
					By.xpath("//html//img[contains(@class, '_image') and contains(@class, '_listImage')]")));

			// 이미지 요소 선택
			List<WebElement> imageElements = driver.findElements(
					By.xpath("//html//img[contains(@class, '_image') and contains(@class, '_listImage')]"));

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

	public List<Blog> getBlog(String name, String sortType) {
		// 네이버 블로그 검색 API 요청
		String clientId = "FTz2f8retxTdxp8Vssbr";
		String clientSecret = "AaaoVfoyAY";

		// String apiURL = "https://openapi.naver.com/v1/search/blog?query=" + text; //
		// JSON 결과
		URI uri = UriComponentsBuilder.fromUriString("https://openapi.naver.com").path("/v1/search/blog.json")
				.queryParam("query", name.trim()).queryParam("display", 10).queryParam("start", 1)
				.queryParam("sort", sortType).encode().build().toUri();

		// Spring 요청 제공 클래스
		RequestEntity<Void> req = RequestEntity.get(uri).header("X-Naver-Client-Id", clientId)
				.header("X-Naver-Client-Secret", clientSecret).build();
		// Spring 제공 restTemplate
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> resp = restTemplate.exchange(req, String.class);

		// JSON 파싱 (Json 문자열을 객체로 만듦, 문서화)
		ObjectMapper om = new ObjectMapper();
		BlogResult resultVO = null;

		try {
			resultVO = om.readValue(resp.getBody(), BlogResult.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return resultVO.getItems(); // weatherList를 list.html에 출력 -> model 선언
	}

	public List<String[]> getCsv() {
		List<String[]> data = new ArrayList<>();
		/* /csv/beach2.csv에 있는 데이터를 읽어오는 방식 */
		/*
		 * InputStream 클래스는 바이트 기반의 입력 스트림을 읽기 위한 추상 클래스 . 이 클래스는 다양한 소스로부터 바이트 데이터를
		 * 읽어들이는 기능을 제공.
		 */
		try (InputStream inputStream = getClass().getResourceAsStream("/csv/beach2.csv");
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
				/*
				 * BufferedReader는 입출력 성능을 향상시키기 위해 버퍼링을 사용한다. 버퍼링은 읽기 동작을 한 번에 여러 바이트 또는 문자열로
				 * 처리하여 시스템 호출 횟수를 줄이고 성능을 향상시킨다. 이를 통해 데이터를 효율적으로 읽을 수 있다.
				 */
				BufferedReader br = new BufferedReader(inputStreamReader);
				/*
				 * ByteArrayOutputStream 클래스는 바이트 배열에 데이터를 쓰기 위한 출력 스트림이다. 데이터를 바이트 배열로 모으는 데
				 * 사용된다.
				 */
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				/*
				 * OutputStreamWriter는 주로 텍스트 파일, 네트워크 통신 등에서 문자 데이터를 바이트 스트림으로 변환하여 출력할 때 사용된다.
				 */
				OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {

			String line;
			while ((line = br.readLine()) != null) {
				/* csv파일을 ,로 나눠서 data에 저장한다. */
				String[] row = line.split(",");
				data.add(row);
			}

			for (String[] row : data) {
				/* ,에 row와 개행문자를 넣어서 outputStreamWriter 에 작성한다. */
				String joinedRow = String.join(",", row) + "\n";
				outputStreamWriter.write(joinedRow);
			}
			/*
			 * flush() 메서드는 OutputStreamWriter가 내부적으로 유지하는 버퍼에 쌓여 있는 모든 문자 데이터를 출력 스트림으로
			 * 플러시(비우기)하는 역할을 한다.
			 */
			outputStreamWriter.flush();

			/*
			 * UTF-8 인코딩의 BOM(Byte Order Mark) 값인 (byte) 0xEF, (byte) 0xBB, (byte) 0xBF를
			 * 포함하도록 초기화
			 */
			byte[] bom = { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF };
			/* toByteArray() 메서드를 사용하여 ByteArrayOutputStream에 기록된 데이터를 바이트 배열로 추출 */
			byte[] csvBytes = outputStream.toByteArray();

			// BOM 추가
			/*
			 * BOM 바이트 배열(bom)과 CSV 데이터의 바이트 배열(csvBytes)을 합쳐서 하나의 결과 바이트 배열(result)을 생성하는
			 * 역할
			 */
			byte[] result = new byte[bom.length + csvBytes.length];
			/* System.arraycopy() 메서드를 사용하여 bom 배열의 내용을 result 배열의 처음부터 복사 */
			System.arraycopy(bom, 0, result, 0, bom.length);
			/* csvBytes 배열의 내용을 result 배열의 BOM 이후부터 복사 */
			System.arraycopy(csvBytes, 0, result, bom.length, csvBytes.length);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return data;
	}

	public List<String[]> getFilterdData(int searchType, String searchKeyword) {
		List<String[]> filteredData = new ArrayList<>();

		List<String[]> data = getCsv();

		if (!searchKeyword.isEmpty()) {
			for (String[] row : data) {
				String searchTarget;
				if (searchType == 0) {
					searchTarget = row[1]; // 해수욕장 이름 정보
				} else {
					searchTarget = row[6]; // 위치 정보
				}
				if (searchTarget.contains(searchKeyword)) {
					filteredData.add(row);
				}
			}
		} else {
			filteredData = data;
		}
		return filteredData;
	}

	public List<String[]> getMemberLike(int id) {
		String relId = Integer.toString(id);
		List<String[]> filteredData = new ArrayList<>();

		List<String[]> data = getCsv();

		for (String[] row : data) {
			String searchTarget = null;
			if (relId.equals(row[0])) {
				searchTarget = row[0]; // 해수욕장 이름 정보
			}
			if (searchTarget != null && searchTarget.contains(relId)) {
				filteredData.add(row);
			}
		}
		return filteredData;
	}
}
