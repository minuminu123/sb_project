<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="JOIN" />
<%@ include file="../common/head.jspf"%>
<title>Today Weather</title>
</head>
<body>
		<h3>네이버 오픈 API를 활용한 뉴스 기사 검색</h3>

		<table border="1">
				<tr>
						<td>오늘 날짜</td>
						<td>
							뉴스 제목
						</td>
						<td>기사내용 요약</td>
				</tr>
				<!-- 수정 후 코드 -->
				<c:forEach var="weather" items="${WeatherList }">
							<tr class="hover">
								<td>${weather.pubDate.substring(0,16)}</td>
								<td><a href="${weather.link }">${weather.title}</a></td>
								<td>${weather.description}</td>
							</tr>
				</c:forEach>
		</table>

</body>
