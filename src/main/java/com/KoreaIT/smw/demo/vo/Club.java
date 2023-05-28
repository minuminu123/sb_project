package com.KoreaIT.smw.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Club {
	private int id; 
	private String regDate; 
	private String updateDate; 
	private int leaderId;
	private String name; 
	private int categoryId;
	private boolean delStatus;
	private String delDate;
	private String areacode;
	private String purpose;
	
	private int avgAge;
	private int membersCount;
	private String region_name;
	private String category_name;
}
