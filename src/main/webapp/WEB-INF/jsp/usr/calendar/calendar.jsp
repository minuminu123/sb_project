<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/head.jspf"%>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<!-- jquery datepicker -->
<link rel="stylesheet" href="http://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css" type="text/css" />
<script src="http://code.jquery.com/ui/1.8.18/jquery-ui.min.js"></script>
<!-- jquery datepicker 끝 -->

<link rel="stylesheet" href="/resource/calendar.css" />

<hr/>

<div class="cal-con">
		<form name="calendarFrm" id="calendarFrm" action="" method="GET">
				<input type="hidden" name="year" value="${today_info.search_year}" />
				<input type="hidden" name="month" value="${today_info.search_month-1}" />
				<script>
					var message = "${message}";
					console.log(message);
					if (message != "") {
						alert(message);
					}
				</script>

				<script>
					function showForm() {
						var popup = document.getElementById("scheduleAddPopup");
						popup.style.display = "block";
					}
					function showEditForm(scheduleIdx) {
						var popup = document
								.getElementById("scheduleEditPopup");
						var scheduleIdxField = document
								.querySelector("input[name='schedule_idx']");
						scheduleIdxField.value = scheduleIdx;
						popup.style.display = "block";
					}
				</script>


				<div class="calendar">

						<!--날짜 네비게이션  -->
						<div class="navigation">
								<a class="before_after_year"
										href="./calendar.do?year=${today_info.search_year-1}&month=${today_info.search_month-1}">
										&lt;&lt;
										<!-- 이전해 -->
								</a>
								<a class="before_after_month"
										href="./calendar.do?year=${today_info.before_year}&month=${today_info.before_month}">
										&lt;
										<!-- 이전달 -->
								</a>
								<span class="this_month">
										&nbsp;${today_info.search_year}.
										<c:if test="${today_info.search_month<10}">0</c:if>${today_info.search_month}
								</span>
								<a class="before_after_month" href="/calendar.do?year=${today_info.after_year}&month=${today_info.after_month}">
										<!-- 다음달 -->
										&gt;
								</a>
								<a class="before_after_year"
										href="/calendar.do?year=${today_info.search_year+1}&month=${today_info.search_month-1}">
										<!-- 다음해 -->
										&gt;&gt;
								</a>
						</div>

						<div class="today_button_div mb-8">
								<input type="button" class="btn-text-link btn btn-outline btn-sm"
										onclick="javascript:location.href='/calendar.do'" value="go today" />
						</div>
						<table class="calendar_body">

								<thead>
										<tr bgcolor="#CECECE">
												<td class="day sun">일</td>
												<td class="day">월</td>
												<td class="day">화</td>
												<td class="day">수</td>
												<td class="day">목</td>
												<td class="day">금</td>
												<td class="day sat">토</td>
										</tr>
								</thead>
								<tbody>
										<tr>
												<c:forEach var="dateList" items="${dateList}" varStatus="date_status">
														<c:choose>
																<c:when test="${dateList.value=='today'}">
																		<c:if test="${date_status.index%7==0}">
																				<tr>
																		</c:if>
																		<td style="background-color: lightgray;" class="today">
																				<div class="date">
																</c:when>
																<c:when test="${date_status.index%7==6}">
																		<td class="sat_day">
																				<div class="sat">
																</c:when>
																<c:when test="${date_status.index%7==0}">
										</tr>
										<tr>
												<td class="sun_day">
														<div class="sun">
																</c:when>
																<c:otherwise>
																		<td class="normal_day">
																				<div class="date">
																</c:otherwise>
																</c:choose>
																${dateList.date}
														</div>
														<div>

																<c:forEach var="scheduleList" items="${dateList.schedule_data_arr}" varStatus="schedule_data_arr_status">
																		<c:if test="${scheduleList.memberId == rq.loginedMemberId}">
																				<p style="background-color: ${scheduleList.color};" class="date_subject">
																						<a href="#" class="schedule-link"
																								data-schedule='{"schedule_num": "${scheduleList.schedule_num}","schedule_idx": "${scheduleList.schedule_idx}", "start_date": "${scheduleList.schedule_startdate}", "end_date": "${scheduleList.schedule_enddate}", "schedule_subject": "${scheduleList.schedule_subject}", "schedule_desc": "${scheduleList.schedule_desc}"}'
																								onclick="showScheduleDetails(event)">${scheduleList.schedule_subject}</a>
																				</p>
																		</c:if>
																</c:forEach>

														</div>
												</td>
												</c:forEach>
								</tbody>

						</table>

						<div class="schudule_button_div">
								<button type="button" onclick="showForm()" class="btn-text-link btn btn-outline btn-sm">일정추가</button>

						</div>
				</div>
		</form>
</div>
<script>
	function showScheduleDetails(event) {
		event.preventDefault();
		var target = event.target;
		var schedule = JSON.parse(target.getAttribute("data-schedule"));

		var popup = document.getElementById("scheduleDetailsPopup");
		var popupContent = document.getElementById("scheduleDetailsContainer");

		var scheduleDetailsHtml = "<div class='close-button' onclick='closeScheduleDetailsPopup()'>&times;</div>"
				+ "<h3 style='color: blue;'>일정 정보</h3>"
				+ "<p class='input input-bordered w-full max-w-xs'>순번: "
				+ schedule.schedule_num
				+ "</p>"
				+ "<p class='input input-bordered w-full max-w-xs '>일정번호: "
				+ schedule.schedule_idx
				+ "</p>"
				+ "<p class='input input-bordered w-full max-w-xs datepicker'>시작일: "
				+ schedule.start_date
				+ "</p>"
				+ "<p class='input input-bordered w-full max-w-xs datepicker'>종료일: "
				+ schedule.end_date
				+ "</p>"
				+ "<p class='input input-bordered w-full max-w-xs'>제목: "
				+ schedule.schedule_subject
				+ "</p>"
				+ "<p class='input input-bordered w-full max-w-xs'>내용: "
				+ schedule.schedule_desc
				+ "</p>"
				+ "<a class='btn-text-link btn btn-outline btn-sm' style='margin: 10px;' onclick=\"if(confirm('정말 삭제하시겠습니까?') == false) return false;\" href='/usr/calender/delete?idx="
				+ schedule.schedule_idx
				+ "'>삭제</a>"
				+ "<a class='btn-text-link btn btn-outline btn-sm' style='margin: 10px;' href='#' onclick='showEditForm("
				+ schedule.schedule_idx + ")'>수정</a>";
		;

		popupContent.innerHTML = scheduleDetailsHtml;
		popup.style.display = "block";
	}

	function closeScheduleDetailsPopup() {
		var popup = document.getElementById("scheduleDetailsPopup");
		var popupContent = document.getElementById("scheduleDetailsContainer");
		popup.style.display = "none";
		popupContent.innerHTML = "";
	}
</script>


<script>
	$(function() {
		$("#testDatepicker").datepicker(
				{
					dateFormat : "yy-mm-dd",
					changeMonth : true,
					changeYear : true,
					dayNames : [ '월요일', '화요일', '수요일', '목요일', '금요일', '토요일',
							'일요일' ],
					dayNamesMin : [ '월', '화', '수', '목', '금', '토', '일' ],
					monthNamesShort : [ '1', '2', '3', '4', '5', '6', '7', '8',
							'9', '10', '11', '12' ],
					multiSelect : true
				// 여러 날짜 선택을 허용
				});
	});

	function scheduleAdd() {
		var schedule_add_form = document.schedule_add;
		if (schedule_add_form.schedule_date.value == ""
				|| schedule_add_form.schedule_date.value == null) {
			alert("날짜를 입력해주세요.");
			schedule_add_form.schedule_date.focus();
			return false;
		} else if (schedule_add_form.schedule_subject.value == ""
				|| schedule_add_form.schedule_subject.value == null) {
			alert("제목을 입력해주세요.");
			schedule_add_form.schedule_date.focus();
			return false;
		}
		schedule_add_form.submit();
	}
</script>
<script>
	$(function() {
		$("#endDatePicker").datepicker(
				{
					dateFormat : "yy-mm-dd",
					changeMonth : true,
					changeYear : true,
					dayNames : [ '월요일', '화요일', '수요일', '목요일', '금요일', '토요일',
							'일요일' ],
					dayNamesMin : [ '월', '화', '수', '목', '금', '토', '일' ],
					monthNamesShort : [ '1', '2', '3', '4', '5', '6', '7', '8',
							'9', '10', '11', '12' ],
					multiSelect : true
				// 여러 날짜 선택을 허용
				});
	});

	function scheduleAdd() {
		var schedule_add_form = document.schedule_add;
		if (schedule_add_form.schedule_startdate.value == ""
				|| schedule_add_form.schedule_startdate.value == null) {
			alert("시작일을 입력해주세요.");
			schedule_add_form.schedule_startdate.focus();
			return false;
		} else if (schedule_add_form.schedule_enddate.value == ""
				|| schedule_add_form.schedule_enddate.value == null) {
			alert("종료일을 입력해주세요.");
			schedule_add_form.schedule_enddate.focus();
			return false;
		} else if (schedule_add_form.schedule_subject.value == ""
				|| schedule_add_form.schedule_subject.value == null) {
			alert("제목을 입력해주세요.");
			schedule_add_form.schedule_subject.focus();
			return false;
		}
		schedule_add_form.submit();
	}
</script>
<script>
	$(function() {
		$(".datepicker").datepicker(
				{
					dateFormat : "yy-mm-dd",
					changeMonth : true,
					changeYear : true,
					dayNames : [ '월요일', '화요일', '수요일', '목요일', '금요일', '토요일',
							'일요일' ],
					dayNamesMin : [ '월', '화', '수', '목', '금', '토', '일' ],
					monthNamesShort : [ '1', '2', '3', '4', '5', '6', '7', '8',
							'9', '10', '11', '12' ],
					multiSelect : true
				});
	});

	function scheduleEdit() {
		var form = document.schedule_edit;
		if (form.schedule_startdate.value === ""
				|| form.schedule_startdate.value === null) {
			alert("시작일을 입력해주세요.");
			form.schedule_startdate.focus();
			return false;
		}
		if (form.schedule_enddate.value === ""
				|| form.schedule_enddate.value === null) {
			alert("종료일을 입력해주세요.");
			form.schedule_enddate.focus();
			return false;
		}
		if (form.schedule_subject.value === ""
				|| form.schedule_subject.value === null) {
			alert("제목을 입력해주세요.");
			form.schedule_subject.focus();
			return false;
		}
		form.submit();
	}
</script>
<!-- 이벤트 위임을 사용하여 일정 요소에 이벤트 핸들러를 등록 -->
<script>
	document.addEventListener("click", function(event) {
		var target = event.target;
		if (target.matches(".schedule-item")) {
			showScheduleDetails(event);
		}
	});
</script>

<script>
	function closeFormPopup() {
		var popup = document.getElementById("scheduleAddPopup");
		popup.style.display = "none";
	}

	function closeEditFormPopup() {
		var popup = document.getElementById("scheduleEditPopup");
		popup.style.display = "none";
	}
</script>

<div id="scheduleDetailsPopup" class="popup">
		<div id="scheduleDetailsContainer" class="popup-content"></div>
</div>

<div id="scheduleAddPopup" class="popup">
		<div class="popup-content">
				<div class="info"></div>
				<div style="text-align: right; cursor: pointer;" class="close-button" onclick="closeFormPopup()">&times;</div>
				<!-- 닫기 버튼 추가 -->
				<form name="schedule_add" action="schedule_add.do">
						<input type="hidden" name="year" value="${today_info.search_year}" />
						<input type="hidden" name="month" value="${today_info.search_month-1}" />

						<div class="contents">
								<ul>
										<li>
												<div class="text_subject"></div>
												<div class="text_desc">
														<input style="width: 100%;" type="hidden" name="memberId" value="${rq.loginedMemberId}" class="text_type1" />
												</div>
										</li>
										<li>
												<div class="text_desc">
														순번 :
														<input style="width: 100%;" type="text" name="schedule_num" class="input input-bordered w-full max-w-xs" />
												</div>
										</li>
										<li>
												<div class="text_desc">
														색상:
														<input type="color" name="color" class="input input-bordered w-full max-w-xs"
																onchange="changeBackgroundColor(this)">
												</div>
										</li>
										<li>
												<div class="text_desc">
														시작 :
														<input style="width: 100%;" type="text" name="schedule_startdate"
																class="input input-bordered w-full max-w-xs datepicker" readonly="readonly" />
												</div>
										</li>
										<li>
												<div class="text_desc">
														종료 :
														<input style="width: 100%;" type="text" name="schedule_enddate"
																class="input input-bordered w-full max-w-xs datepicker" readonly="readonly" />
												</div>
										</li>
										<li>
												<div class="text_desc">
														제목 :
														<input style="width: 100%;" type="text" name="schedule_subject"
																class="input input-bordered w-full max-w-xs" />
												</div>
										</li>
										<li>
												<div class="text_area_desc">
														내용 :
														<textarea style="width: 100%;" name="schedule_desc" class="input input-bordered w-full max-w-xs" rows="7"></textarea>
												</div>
										</li>
										<li class="button_li">
												<button type="button" class="btn-text-link btn btn-outline btn-sm" onclick="scheduleAdd();">일정등록</button>
										</li>
								</ul>
						</div>
				</form>
		</div>
</div>



<div id="scheduleEditPopup" class="popup">
		<div class="popup-content">
				<div class="info"></div>
				<div style="text-align: right; cursor: pointer;" class="close-button" onclick="closeEditFormPopup()">&times;</div>
				<!-- 닫기 버튼 추가 -->
				<form name="schedule_edit" action="../usr/calender/edit">
						<input type="hidden" id="scheduleIdxField" name="schedule_idx" />

						<div class="contents">
								<ul>
										<li>
												<div class="text_desc">
														순번 :
														<input style="width: 100%;" type="text" name="schedule_num" class="input input-bordered w-full max-w-xs" />
												</div>
										</li>
										<li>
												<div class="text_desc">
														색상:
														<input type="color" name="color" class="input input-bordered w-full max-w-xs"
																onchange="changeBackgroundColor(this)">
												</div>
										</li>
										<li>
												<div class="text_desc">
														시작 :
														<input style="width: 100%;" type="text" name="schedule_startdate"
																class="input input-bordered w-full max-w-xs datepicker" readonly="readonly" />
												</div>
										</li>
										<li>
												<div class="text_desc">
														종료 :
														<input style="width: 100%;" type="text" name="schedule_enddate"
																class="input input-bordered w-full max-w-xs datepicker" readonly="readonly" />
												</div>
										</li>
										<li>
												<div class="text_desc">
														제목 :
														<input style="width: 100%;" type="text" name="schedule_subject"
																class="input input-bordered w-full max-w-xs" />
												</div>
										</li>
										<li>
												<div class="text_area_desc">
														내용 :
														<textarea style="width: 100%;" name="schedule_desc" class="input input-bordered w-full max-w-xs" rows="7"></textarea>
												</div>
										</li>
										<li class="button_li">
												<button type="button" style="margin: 10px;" class="btn-text-link btn btn-outline btn-sm"
														onclick="scheduleEdit();">수정</button>
										</li>
								</ul>
						</div>
				</form>
		</div>
</div>

<script>
	function changeBackgroundColor(input) {
		var color = input.value; // 선택한 색상 코드 가져오기
		var hexColor = rgbToHex(color); // RGB 값을 HEX 값으로 변환
		var contents = document.querySelector('.contents');
		contents.style.backgroundColor = hexColor;
		updateColorValue(hexColor); // 선택한 HEX 색상 값을 전달하는 함수 호출
	}

	function rgbToHex(rgbColor) {
		// RGB 값을 HEX 값으로 변환하는 로직
		var rgb = rgbColor.match(/^rgb\((\d+),\s*(\d+),\s*(\d+)\)$/);
		function hex(x) {
			return ("0" + parseInt(x).toString(16)).slice(-2);
		}
		return "#" + hex(rgb[1]) + hex(rgb[2]) + hex(rgb[3]);
	}

	function updateColorValue(color) {
		var colorInput = document.querySelector('input[name="color"]');
		colorInput.value = color; // 선택한 HEX 색상 값을 input 요소에 설정
	}
</script>

<%@ include file="../common/foot.jspf"%>