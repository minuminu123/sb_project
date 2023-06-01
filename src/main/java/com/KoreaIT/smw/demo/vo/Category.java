package com.KoreaIT.smw.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
	private int id; 
	private String regDate; 
	private String updateDate; 
	private String name; 

}