package com.KoreaIT.smw.demo.controller;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UsrBeachController {

	@RequestMapping("/usr/beach/list")
	public String index(Model model,
            @RequestParam(required = false, defaultValue = "") String searchKeyword,
            @RequestParam(required = false, defaultValue = "0") int searchType,
        	@RequestParam(required = false, defaultValue = "1") int pageNo,
        	@RequestParam(required = false, defaultValue = "10") int pageSize) {
	    List<String[]> data = new ArrayList<>();

	    try (InputStream inputStream = getClass().getResourceAsStream("/csv/beach2.csv");
	         InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
	         BufferedReader br = new BufferedReader(inputStreamReader);
	         ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	         OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {

	        String line;
	        while ((line = br.readLine()) != null) {
	            String[] row = line.split(",");
	            data.add(row);
	        }

	        for (String[] row : data) {
	            String joinedRow = String.join(",", row) + "\n";
	            outputStreamWriter.write(joinedRow);
	        }

	        outputStreamWriter.flush();

	        byte[] bom = {(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};
	        byte[] csvBytes = outputStream.toByteArray();

	        // BOM 추가
	        byte[] result = new byte[bom.length + csvBytes.length];
	        System.arraycopy(bom, 0, result, 0, bom.length);
	        System.arraycopy(csvBytes, 0, result, bom.length, csvBytes.length);

	        // 파일로 저장 또는 다른 처리
	        // ...

	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    List<String[]> filteredData = new ArrayList<>();

	    if (!searchKeyword.isEmpty()) {
	        for (String[] row : data) {
	        	String searchTarget;
                if (searchType == 0) {
                    searchTarget = row[6]; // 위치 정보
                } else {
                    searchTarget = row[1]; // 해수욕장 이름 정보
                }
	            if (searchTarget.contains(searchKeyword)) {
	                filteredData.add(row);
	            }
	        }
	    } else {
	        filteredData = data;
	    }

		// 페이지네이션 처리
		int totalCount = filteredData.size();
		int startIdx = (pageNo - 1) * pageSize;
		int endIdx = Math.min(startIdx + pageSize, totalCount);
		List<String[]> paginatedData = filteredData.subList(startIdx, endIdx);

		model.addAttribute("data", paginatedData);
		model.addAttribute("searchKeyword", searchKeyword);
		model.addAttribute("searchType", searchType);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("pageSize", pageSize);
	    return "usr/beach/list";
	}
}