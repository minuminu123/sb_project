package com.KoreaIT.smw.demo.repository;

import java.sql.Date;
import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.KoreaIT.smw.demo.vo.DateData;
import com.KoreaIT.smw.demo.vo.Schedule;

@Mapper
public interface ScheduleRepository {

	public int schedule_add(Schedule scheduleDto);
	public int before_schedule_add_search(Schedule scheduleDto);
	public ArrayList<Schedule> schedule_list(DateData scheduleDto);
	public Schedule getSchedule(int idx);
	public void deleteSchedule(int idx);
	public void editSchedule(int schedule_idx, String schedule_subject, String schedule_desc, Date schedule_startdate, Date schedule_enddate);
}