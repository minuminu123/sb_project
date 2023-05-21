<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="JOIN" />
<%@ include file="../common/head.jspf"%>
<title>네이버 블로그 검색</title>
</head>
<body>
		<h3>관련 블로그</h3>

		<table border="1">
				<tr>
						<td>게시 날짜</td>
						<td>블로그 제목</td>
						<td>블로그내용 요약</td>
				</tr>

				<!-- 수정 후 코드 -->
				<c:forEach var="beach" items="${BeachDetails }">
						<c:set var="formattedDate" value="${beach.postdate.substring(0, 8)}" />
						<c:set var="year" value="${formattedDate.substring(0, 4)}" />
						<c:set var="month" value="${formattedDate.substring(4, 6)}" />
						<c:set var="day" value="${formattedDate.substring(6, 8)}" />
						<c:set var="formattedDateString" value="${year}-${month}-${day}" />
						<tr class="hover">
								<td>${formattedDateString}</td>
								<td>
										<a href="${beach.link }">${beach.title}</a>
								</td>
								<td>${beach.description}</td>
						</tr>
				</c:forEach>
		</table>

</body>
