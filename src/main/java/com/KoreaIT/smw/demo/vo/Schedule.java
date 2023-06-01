package com.KoreaIT.smw.demo.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {
	int memberId;
	int schedule_idx;
	int schedule_num;
	String schedule_subject;
	String schedule_desc;
	Date schedule_startdate;
	Date schedule_enddate;
	String color;

 
}