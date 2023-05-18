<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="pageTitle" value="weather"></c:set>
<%@ include file="../common/head.jspf"%>
<c:out value="${result}" />
<head>
<meta charset="UTF-8">
<title>Weather Information</title>
<style>
table {
	border-collapse: collapse;
	width: 100%;
}
th, td {
	text-align: left;
	padding: 8px;
	border-bottom: 1px solid #ddd;
}
tr:hover {
	background-color: #f5f5f5;
}
</style>
</head>
<body>
		<h1>Weather Information</h1>
		<table>
				<thead>
						<tr>
								<th>Date</th>
								<th>Time</th>
								<th>Temperature</th>
								<th>Humidity</th>
						</tr>
				</thead>
				<tbody>
						<c:set var="result" value="${result}" />
						<c:forEach items="${result['response']['body']['items']['item']}" var="item">
								<tr>
										<td>
												<fmt:formatDate value="${item.baseDate}" pattern="yyyy-MM-dd" />
										</td>
										<td>
												<fmt:formatDate value="${item.baseTime}" pattern="HH:mm" />
										</td>
										<td>${item.ta}</td>
										<td>${item.reh}</td>
								</tr>
						</c:forEach>
				</tbody>
		</table>

		<%@ include file="../common/foot.jspf"%>