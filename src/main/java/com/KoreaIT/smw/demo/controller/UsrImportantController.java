package com.KoreaIT.smw.demo.controller;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.KoreaIT.smw.demo.service.ImportantService;
import com.KoreaIT.smw.demo.vo.Important;
import com.KoreaIT.smw.demo.vo.Rq;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

@Controller
public class UsrImportantController {
	   private final String CSV_FILE_PATH = "data.csv"; // CSV 파일 경로
	@Autowired
	private ImportantService importantService;

	@Autowired
	private Rq rq;



	@RequestMapping("/usr/important/list")
	public String showList(Model model) throws IOException {
		 File csvFile = new File(CSV_FILE_PATH);
	        FileReader fileReader = new FileReader(csvFile);
	        CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT);

	        List<CSVRecord> csvRecords = csvParser.getRecords();
	        model.addAttribute("csvRecords", csvRecords);

		return "usr/important/list";	
	}


	private List<Important> parseCsv(String string) throws IOException, CsvException {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("data.csv").getFile());
		String filePath = file.getAbsolutePath();
		 Reader reader = Files.newBufferedReader(Paths.get(filePath)); // CSV 파일 열기
	        CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build(); // CSVReader 생성
	        List<String[]> lines = csvReader.readAll(); // CSV 파일 읽기
	        List<Important> importants = new ArrayList<>(); // 게시글 정보를 저장할 리스트
	        for (String[] line : lines) {
	        	Important important = new Important(); // 게시글 객체 생성
	        	important.setTitle(line[0]); // CSV 파일의 첫 번째 열을 게시글 제목으로 설정
	        	important.setContent(line[1]); // CSV 파일의 두 번째 열을 게시글 내용으로 설정
	        	importants.add(important); // 리스트에 추가
	        }
	        return importants; // 게시글 정보 리스트 반환
	    }
	}