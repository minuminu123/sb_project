<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="pageTitle" value="날씨"></c:set>
<%@ include file="../common/head.jspf"%>
<%@ page import="org.json.JSONObject"%>
<%@ page import="org.json.JSONArray"%>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="https://code.jquery.com/ui/1.13.0/jquery-ui.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.13.0/themes/base/jquery-ui.css">



<title>Weather</title>



<br />

<div class="mt-16 ml-24">매시 30분 기준으로 업데이트</div>

<hr />
<br />
<form class="form-horizontal ml-24 mt-24">
		<div class="form-group">
				<select id="step1" class="emptyCheck" title="시/도">
						<option id="city" value="">시/도</option>
				</select>

				<select id="step2">
						<option id="county" value="">시/군/구</option>
				</select>

				<select id="step3">
						<option id="town" value="">읍/면/동</option>
				</select>

				<span style="display: inline-block;">
						날짜 선택:
						<input type="text" id="datepicker" disabled="disabled" class="emptyCheck" title="날짜">
				</span>

				<select id="time" class="emptyCheck" title="시간">
						<option value="" selected>시간</option>
						<option value="0">0시</option>
						<option value="1">1시</option>
						<option value="1">2시</option>
						<option value="1">3시</option>
						<option value="1">4시</option>
						<option value="1">5시</option>
						<option value="1">6시</option>
						<option value="1">7시</option>
						<option value="1">8시</option>
						<option value="1">9시</option>
						<option value="1">10시</option>
						<option value="1">11시</option>
						<option value="1">12시</option>
						<option value="1">13시</option>
						<option value="1">14시</option>
						<option value="1">15시</option>
						<option value="1">16시</option>
						<option value="1">17시</option>
						<option value="1">17시</option>
						<option value="1">18시</option>
						<option value="1">19시</option>
						<option value="1">20시</option>
						<option value="1">21시</option>
						<option value="1">22시</option>
						<option value="23">23시</option>
				</select>


				<button type="button" class="btn-text-link btn btn-outline btn-xs" onclick="getWeather()">
						<span>실행</span>
				</button>

				<div>
						<table id="resultWeather" class="table w-3/6"></table>
				</div>
		</div>
</form>




<script>
	/* 달력을 표시하는 함수 */
	window.onload = function() {
		$('#datepicker')
				.datepicker(
						{
							showOn : 'button',
							buttonImage : 'http://jqueryui.com/resources/demos/datepicker/images/calendar.gif',
							buttonImageOnly : true,
							showButtonPanel : true,
							dateFormat : 'yymmdd',
							minDate : "-1D",
							maxDate : 0,
							closeText : "닫기",
							currentText : "오늘",
							prevText : '이전 달',
							nextText : '다음 달',
							monthNames : [ '1월', '2월', '3월', '4월', '5월', '6월',
									'7월', '8월', '9월', '10월', '11월', '12월' ],
							monthNamesShort : [ '1월', '2월', '3월', '4월', '5월',
									'6월', '7월', '8월', '9월', '10월', '11월', '12월' ],
							dayNames : [ '일', '월', '화', '수', '목', '금', '토' ],
							dayNamesShort : [ '일', '월', '화', '수', '목', '금', '토' ],
							dayNamesMin : [ '일', '월', '화', '수', '목', '금', '토' ],
							weekHeader : "주",
							yearSuffix : '년',
							showMonthAfterYear : true
						});
		// 추가된 코드
		loadArea('city');
	};
	/* step1에 들어있는 데이터가 바뀌면 loadArea 함수 실행 */
	$('#step1').on("change", function() {
		loadArea('county', $(this));
	});
	/* step2에 들어있는 데이터가 바뀌면 loadArea 함수 실행 */

	$('#step2').on("change", function() {
		loadArea('town', $(this));
	});
	/* 
	위에서 선택한 값을 value로 저장하고 data에 타입과 키워드를 넣어서 ajax시 data 값을 넘겨준다.
	"/board/weatherStep.do" 를 실행하고 성공할떄 만약 타입이 city면 step1의 value를 city의 areacode로 담아주고
	city.step1으로 해당 값에 들어있는 텍스트로 화면에 보여준다. 마찬가지로 county와town 실행
	sibling함수를 통해 다른 옵션에 있는 값들을 없앤다.
	
	*/
	function loadArea(type, element) {
		var value = $(element).find('option:selected').text();
		var data = {
			type : type,
			keyword : value
		};

		console.log(data);
		$.ajax({
			url : "/board/weatherStep.do",
			data : data,
			dataType : "JSON",
			method : "POST",
			success : function(res) {
				if (type == 'city') {
					res.forEach(function(city) {
						$('#step1').append(
								'<option value="'+city.areacode+'">'
										+ city.step1 + '</option>')
					});
				} else if (type == 'county') {
					$('#county').siblings().remove();
					$('#town').siblings().remove();
					res.forEach(function(county) {
						$('#step2').append(
								'<option value="'+county.areacode+'">'
										+ county.step2 + '</option>')
					});
				} else {
					$('#town').siblings().remove();
					res.forEach(function(town) {
						$('#step3').append(
								'<option value="'+town.areacode+'">'
										+ town.step3 + '</option>')
					});
				}
			},
			error : function(xhr) {
				alert(xhr.responseText);
			}
		});
	}

	/*]]>*/
</script>

<script>
/* .prop() 함수는 요소의 여러 가지 속성을 설정하거나 가져올 수 있다. 현재 날짜, 시간등을 변수에 저장 */
	$('#datepicker').on(
			"change",
			function() {
				$('#time option:eq(0)').prop("selected", true);
				var now = new Date();
				var currentHour = now.getHours();
				var month = (now.getMonth() + 1) < 10 ? "0"
						+ (now.getMonth() + 1) : (now.getMonth() + 1);
				var nowDate = now.getDate() < 10 ? "0" + now.getDate() : now
						.getDate();
				var today = now.getFullYear() + "" + month + "" + nowDate;
				var datepickerValue = $(this).val();

				/* 오늘 날짜라면 id가 "time"인 요소의 자식 요소 중에서 첫 번째 옵션을 제외한 나머지 옵션들이 제거하고 현재 업데이트된
				날짜의 시간이 아니라면 보여주는 옵션을 제한한다. (만약 오늘 12시에 해당 날씨를 조회하려고 한다면 12시 이전 까지의 시간을보여주게)*/
				$('#time').children('option:not(:first)').remove();
				var html = '';
				if (datepickerValue == today) {
					for (var i = 0; i <= currentHour; i++) {
						html += '<option value="'+ i +'">' + i + '시</option>';
					}
				}
				/* 어제였다면 모든 시간이표시되도록 설정*/
				else {
					if (currentHour != 23) {
						for (var i = currentHour + 1; i < 24; i++) {
							html += '<option value="'+ i +'">' + i
									+ '시</option>';
						}
					} else {
						alert(datepickerValue + " 날짜의 제공 가능한 날씨 데이터가 없습니다.")
						$(this).val('');
					}
				}
				$('#time').append(html);
			});
	/* 만약 값이 비어있다면 경고 메시지 출력하고 option:selected된 값들을 변수에 저장하고 ajax를 통해 해당 데이터들을 보내주고
		서버에서 보낸 데이터를 꺼내서 강수 형태에 따라 이미지를 다르게 출력해주고 반복하며 다른 정보들도 가져온다. */
	function getWeather() {
		var nullCheck = true;
		$('.emptyCheck').each(function() {
			if ('' == $(this).val()) {
				alert($(this).attr('title') + "을(를) 확인바람");
				nullCheck = false;
				return false; // 빈 값에서 멈춤
			}
		});

		if (nullCheck) {
			var time = $('#time').val() + '00';
			if ($('#time').val() < 10) {
				time = "0" + time;
			}
			var areacode = "";
			var cityCode = $('#step1 option:selected').val();
			var countyCode = $('#step2 option:selected').val();
			var townCode = $('#step3 option:selected').val();

			if (townCode == '' && countyCode == '') {
				areacode = cityCode;
			} else if (townCode == '' && countyCode != '') {
				areacode = countyCode;
			} else if (townCode != '') {
				areacode = townCode;
			}

			var date = $('#datepicker').val();
			var data = {
				"areacode" : areacode,
				"baseDate" : date,
				"baseTime" : time
			};

			$
					.ajax({
						url : "/board/getWeather.do",
						data : data,
						dataType : "JSON",
						method : "POST",
						success : function(res) {
							console.log(res);
							if (res[0].resultCode != null) {
								alert(res[0].resultMsg);
							} else {
								var html = "";
								$("#resultWeather").empty();
								$
										.each(
												res,
												function(index, item) {
													if (index === 0) {
														item.category = "강수형태";

														// item.obsrValue 값에 따라 이미지 업로드
														if (parseFloat(item.obsrValue) == 0) {
															html += "<tr><td>"
																	+"오늘의 날씨"
																	+"</td><td>"
																	+ "<img src='https://cdn-icons-png.flaticon.com/128/5014/5014975.png' alt='맑음' class='ml-auto mr-auto'>"
																	+ " 맑음</td></tr>";
														} else if (parseFloat(item.obsrValue) == 1 || parseFloat(item.obsrValue) == 5) {
															html += "<tr><td>"
																	+ item.category
																	+ "</td><td>"
																	+ "<img src='https://cdn-icons-png.flaticon.com/128/5828/5828341.png' alt='비' class='ml-auto mr-auto'>"
																	+ item.obsrValue
																	+ " 비"
																	+ "</td></tr>";
														} else if (parseFloat(item.obsrValue) == 3 || parseFloat(item.obsrValue) == 7){
															html += "<tr><td>"
																	+ item.category
																	+ "</td><td>"
																	+ "<img src='https://cdn-icons-png.flaticon.com/128/1163/1163578.png' alt='눈' class='ml-auto mr-auto'>"
																	+ item.obsrValue
																	+ " 눈"
																	+ "</td></tr>";
														} else {
															html += "<tr><td>"
																+ item.category
																+ "</td><td>"
																+ "<img src='https://cdn-icons-png.flaticon.com/128/2204/2204342.png' alt='비/눈' class='ml-auto mr-auto'>"
																+ item.obsrValue
																+ " 비/눈"
																+ "</td></tr>";
														}

													} else if (index === 1) {
														item.category = "습도(%)";
													} else if (index === 2) {
														item.category = "1시간 강수량(mm)";
													}
													if (index === 3) {
														item.category = "기온(℃)";
													} else if (index === 4) {
														item.category = "동서바람성분(%)";
													} else if (index === 5) {
														item.category = "풍향(deg)";
													} else if (index === 6) {
														item.category = "남북바람성분(%)";
													} else if (index === 7) {
														item.category = "풍속(m/s)";
													}

													html += "<tr><td>"
															+ item.category
															+ "</td><td>"
															+ item.obsrValue
															+ "</td></tr>";
												});

								html += "</tbody>";
								$("#resultWeather").append(html);
							}
						},
						error : function(xhr) {
							alert("업데이트 전입니다");
						}
					});
		}
	}
</script>