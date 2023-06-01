package com.KoreaIT.smw.demo.controller;


import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.KoreaIT.smw.demo.repository.ScheduleRepository;
import com.KoreaIT.smw.demo.util.Ut;
import com.KoreaIT.smw.demo.vo.DateData;
import com.KoreaIT.smw.demo.vo.Rq;
import com.KoreaIT.smw.demo.vo.Schedule;

@Controller
public class UsrCalendarController {
	@Autowired
	SqlSession sqlsession;
	@Autowired
	ScheduleRepository scheduleRepository;
	@Autowired
	Rq rq;

	@RequestMapping(value = "calendar.do", method = RequestMethod.GET)
	public String calendar(Model model, HttpServletRequest request, DateData dateData) {

		Calendar cal = Calendar.getInstance();
		DateData calendarData;
		// 검색 날짜
		if (dateData.getDate().equals("") && dateData.getMonth().equals("")) {
			dateData = new DateData(String.valueOf(cal.get(Calendar.YEAR)), String.valueOf(cal.get(Calendar.MONTH)),
					String.valueOf(cal.get(Calendar.DATE)), null, null);
		}

		Map<String, Integer> today_info = dateData.today_info(dateData);
		List<DateData> dateList = new ArrayList<DateData>();


		// 검색 날짜 end
		ScheduleRepository scheduleRepository = sqlsession.getMapper(ScheduleRepository.class);
		
		ArrayList<Schedule> Schedule_list = scheduleRepository.schedule_list(dateData);

		// 달력 데이터에 넣기 위한 배열 추가
		ArrayList<List<Schedule>> schedule_data_arr = new ArrayList<>();
		for (int i = 0; i < 32; i++) {
			schedule_data_arr.add(new ArrayList<>());
		}

		if (!Schedule_list.isEmpty()) {
			int j = 0;
			for (int i = 0; i < Schedule_list.size(); i++) {
				int startdate = Integer.parseInt(String.valueOf(Schedule_list.get(i).getSchedule_startdate())
						.substring(String.valueOf(Schedule_list.get(i).getSchedule_startdate()).length() - 2));
				int enddate = Integer.parseInt(String.valueOf(Schedule_list.get(i).getSchedule_enddate())
						.substring(String.valueOf(Schedule_list.get(i).getSchedule_enddate()).length() - 2));
				for (int date = startdate; date <= enddate; date++) {
					if (i > 0) {
						int date_before = Integer
								.parseInt(String.valueOf(Schedule_list.get(i - 1).getSchedule_startdate()).substring(
										String.valueOf(Schedule_list.get(i - 1).getSchedule_startdate()).length() - 2));
						if (date_before == date) {
							j = j + 1;
							schedule_data_arr.get(date).add(Schedule_list.get(i));
						} else {
							j = 0;
							schedule_data_arr.get(date).add(Schedule_list.get(i));
						}
					} else {
						schedule_data_arr.get(date).add(Schedule_list.get(i));
					}
				}
				Schedule schedule = Schedule_list.get(i);
			}
		}

		// 실질적인 달력 데이터 리스트에 데이터 삽입 시작.
		// 일단 시작 인덱스까지 아무것도 없는 데이터 삽입
		//재 월의 첫 번째 요일까지 빈 데이터를 dateList에 추가
		for (int i = 1; i < today_info.get("start"); i++) {
			calendarData = new DateData(null, null, null, null, null);
			dateList.add(calendarData);
		}

		// 날짜 삽입
		// 현재 월의 날짜 데이터를 dateList에 추가
		for (int i = today_info.get("startDay"); i <= today_info.get("endDay"); i++) {
			List<Schedule> schedule_data_arr3 = schedule_data_arr.get(i);

			if (i == today_info.get("today")) {
				calendarData = new DateData(String.valueOf(dateData.getYear()), String.valueOf(dateData.getMonth()),
						String.valueOf(i), "today", schedule_data_arr3.toArray(new Schedule[0]));
			} else {
				calendarData = new DateData(String.valueOf(dateData.getYear()), String.valueOf(dateData.getMonth()),
						String.valueOf(i), "normal_date", schedule_data_arr3.toArray(new Schedule[0]));
			}
			dateList.add(calendarData);
		}

		// 빈 데이터 삽입
		int index = 7 - dateList.size() % 7;
		if (dateList.size() % 7 != 0) {
			for (int i = 0; i < index; i++) {
				calendarData = new DateData(String.valueOf(dateData.getYear()), String.valueOf(dateData.getMonth()),
						null, "normal_date", null);
				dateList.add(calendarData);
			}
		}

		// 배열에 담음
		model.addAttribute("dateList", dateList); // 날짜 데이터 배열
		model.addAttribute("today_info", today_info);
		return "usr/calendar/calendar";
	}



	@RequestMapping(value = "schedule_add.do", method = RequestMethod.GET)
	public String schedule_add(HttpServletRequest request, Schedule schedule, RedirectAttributes rttr) {
		ScheduleRepository scheduleRepository = sqlsession.getMapper(ScheduleRepository.class);
		
		String message = "";
		String url = "redirect:calendar.do";

		scheduleRepository.schedule_add(schedule);
		message = "스케쥴이 등록되었습니다";

		rttr.addFlashAttribute("message", message);
		return url;
	}

	@RequestMapping("/usr/calender/delete")
	@ResponseBody
	public String doDelete(int idx) {

		Schedule schedule = scheduleRepository.getSchedule(idx);

		if (schedule == null) {
			return Ut.jsHistoryBack("F-D", "존재하지 않는 일정입니다");
		}

		scheduleRepository.deleteSchedule(idx);

		return Ut.jsReplace("S-1", "삭제완료", "/calendar.do");

	}
	@RequestMapping("/usr/calender/edit")
	@ResponseBody
	public String doEdit(int schedule_idx, String schedule_subject, String schedule_desc, Date schedule_startdate, Date schedule_enddate) {
		
		Schedule schedule = scheduleRepository.getSchedule(schedule_idx);
		
		if (schedule == null) {
			return Ut.jsHistoryBack("F-D", "존재하지 않는 일정입니다");
		}
		
		scheduleRepository.editSchedule(schedule_idx, schedule_subject, schedule_desc, schedule_startdate, schedule_enddate);
		
		return Ut.jsReplace("S-1", "수정완료", "/calendar.do");
		
	}

}