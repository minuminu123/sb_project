<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="관련 블로그" />
<%@ include file="../common/head.jspf"%>

<div class="bg-getBeach">

		<h1 class="absolute text-center" style="top: 100px; left: 150px; width: 100px;">관련 블로그</h1>

		<form id="searchForm" action="/usr/beach/getBeach" method="GET" class="flex">
				<select name="sortType" id="sortType" class="select select-ghost absolute" style="width: 200px; top: 100px; left: 600px;"
						data-value="${param.sortType }">
						<option value="sim">정확도순</option>
						<option value="date">날짜순</option>
				</select>
				<input type="hidden" name="name" value="${param.name }"/>
				<input type="submit" value="검색" class="btn btn-active absolute ml-12" style="background-color: wheat; width: 100px; top: 100px; left: 800px;">
		</form>



		<table class="table table-zebra z-10 mt-48" id="table" style="width: 700px;">
				<thead>
						<tr>
								<td>게시 날짜</td>
								<td>블로그 제목</td>
								<td>블로그내용 요약</td>
						</tr>
				</thead>
				<tbody>
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
										<td>${beach.description.substring(0,40)}...</td>
								</tr>
						</c:forEach>
				</tbody>
		</table>

</div>


