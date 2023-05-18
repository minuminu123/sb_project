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

@Controller
public class UsrBeachController {

	@RequestMapping("/usr/beach/list")
	public String index(Model model) {
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

	    model.addAttribute("data", data);
	    return "usr/beach/list";
	}
}