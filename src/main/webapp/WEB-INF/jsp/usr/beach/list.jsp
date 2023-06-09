<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="com.KoreaIT.smw.demo.vo.ReactionPoint"%>
<c:set var="pageTitle" value="Beach List" />
<%@ include file="../common/head.jspf"%>

<%
List<String[]> data = (List<String[]>) request.getAttribute("data");
int searchType = (int) request.getAttribute("searchType");
String searchKeyword = (String) request.getAttribute("searchKeyword");
int totalCount = (int) request.getAttribute("totalCount");
int pageNo = (int) request.getAttribute("pageNo");
int pageSize = (int) request.getAttribute("pageSize");
%>

<meta charset="UTF-8">
<hr/>
<div class="bg-beach">
<div style="height: 200px;"></div>
		<h1 class="absolute text-center" style="width: 100px; top: 100px; left: 150px; font-size: 2rem;">Beach List</h1>
		<form class="flex justify-between" style="width: 700px; margin-left: auto; margin-right: auto; top: 300px;">
				<select name="searchType" class="select select-bordered" style="width: 150px; ">
						<option value="0" ${searchType == 0 ? 'selected' : ''}>해수욕장 이름</option>
						<option value="1" ${searchType == 1 ? 'selected' : ''}>위치</option>
				</select>
				<div class="flex-grow"></div>
				<input name="searchKeyword" type="text" class="ml-2 w-96 input input-borderd"
						 placeholder="검색어를 입력해주세요" maxlength="20" value="${searchKeyword}" />
				<button type="submit" class="btn btn-ghost" style="background-color: wheat;">검색</button>
		</form>



		<table class="table table-zebra z-10 mt-36" id="table" style="width: 700px; left: 240px;">
				<thead>
						<tr>
								<th>해수욕장 이름</th>
								<th>위치</th>
								<th>지도에서 보기</th>
						</tr>
				</thead>
				<tbody>
						<%
						for (String[] row : data) {
						%>
						<tr>
								<th>
										<a href="/usr/beach/getBeach?name=<%=row[1]%>&id=<%=row[0]%>"><%=row[1]%></a>
								</th>
								<th><%=row[6]%></th>
								<th>
										<a href="/usr/home/MapSearch?value=<%=row[1]%>">클릭</a>
								</th>

						</tr>
						<%
						}
						%>
				</tbody>
		</table>

		<c:if test="${data.size() > 1 }">
				<div id="nearestLocation" class="absoulte ml-auto mr-auto mt-8" style="width: 300px; color: wheat; font-size: 2rem;">현재페이지에서
						가장 가까운 해수욕장은~~</div>
		</c:if>

		<div class="pagination flex justify-center mt-12">
				<div class="btn-group">


						<c:choose>
								<c:when test="${totalCount > pageSize}">
										<c:set var="maxPage" value="${(totalCount + pageSize - 1) / pageSize}" />
										<c:set var="startPage" value="${((pageNo - 1) / 10 * 10) + 1}" />
										<c:set var="endPage" value="${startPage + 9}" />
										<c:if test="${startPage > 6}">
												<a class="btn-text-link btn btn-outline btn-xl btn-active ml-2"
														href="?searchType=${searchType}&searchKeyword=${searchKeyword}&pageNo=1">처음</a>
										</c:if>
										<c:if test="${startPage > 1}">
												<a class="btn-text-link btn btn-outline btn-xl btn-active ml-2"
														href="?searchType=${searchType}&searchKeyword=${searchKeyword}&pageNo=${(startPage - 1).intValue()}">이전</a>
										</c:if>

										<c:forEach begin="${startPage}" end="${endPage}" varStatus="loop">
												<c:choose>
														<c:when test="${loop.index <= maxPage}">
																<c:if test="${loop.index == pageNo}">
																		<strong class="btn-text-link btn btn-outline btn-xl active btn-active ml-2">${loop.index}</strong>
																</c:if>
																<c:if test="${loop.index != pageNo}">
																		<a class="btn-text-link btn btn-outline btn-xl btn-active"
																				href="?searchType=${searchType}&searchKeyword=${searchKeyword}&pageNo=${loop.index}">${loop.index}</a>
																</c:if>
														</c:when>
												</c:choose>
										</c:forEach>
										<c:if test="${endPage < maxPage}">
												<a class="btn-text-link btn btn-outline btn-xl btn-active ml-2"
														href="?searchType=${searchType}&searchKeyword=${searchKeyword}&pageNo=${(endPage + 1).intValue()}">다음</a>
										</c:if>
										<c:if test="${pageNo < maxPage && maxPage - pageNo >= 10}">
												<a class="btn-text-link btn btn-outline btn-xl btn-active ml-2"
														href="?searchType=${searchType}&searchKeyword=${searchKeyword}&pageNo=${(maxPage).intValue()}">마지막</a>
										</c:if>


								</c:when>
								<c:otherwise>
										<a href="?searchType=${searchType}&searchKeyword=${searchKeyword}&pageNo=1">1</a>
								</c:otherwise>
						</c:choose>
				</div>
		</div>
</div>

<script>
navigator.geolocation.getCurrentPosition(function(position) {
	  var latitude = position.coords.latitude;  // 위도
	  var longitude = position.coords.longitude;  // 경도
	  var locations = [
	    <%for (String[] row : data) {%>
	      { location: '<%=row[1]%>', gyeongdo: <%=row[4]%>, wido: <%=row[5]%> },
	    <%}%>
	  ];
	  var distances = [];

	  locations.forEach(function(location) {
	    var gyeongdo = location.gyeongdo;
	    var wido = location.wido;

	    var distance = calculateDistance(longitude, latitude, gyeongdo, wido);
	    distances.push(distance);
	  });

	  var minDistance = Math.min(...distances);
	  var index = distances.indexOf(minDistance);
	  var nearestLocation = locations[index].location;

	  console.log("가장 가까운 거리: " + minDistance);
	  console.log("가장 가까운 위치: " + nearestLocation);

	  document.getElementById('nearestLocation').innerText += nearestLocation;
	});

/**
 * 두 지점 간의 거리를 계산하는 함수
 * @param {number} lat1 - 출발 지점의 위도
 * @param {number} lon1 - 출발 지점의 경도
 * @param {number} lat2 - 도착 지점의 위도
 * @param {number} lon2 - 도착 지점의 경도
 * @returns {number} - 두 지점 간의 거리 (단위: km)
 */
function calculateDistance(lat1, lon1, lat2, lon2) {
  var earthRadius = 6371; // 지구의 반지름 (단위: km)

  // 위도, 경도 차이 계산
  var latDiff = toRadians(lat2 - lat1);
  var lonDiff = toRadians(lon2 - lon1);

  // Haversine 공식을 이용한 거리 계산
  var a =
    Math.sin(latDiff / 2) * Math.sin(latDiff / 2) +
    Math.cos(toRadians(lat1)) * Math.cos(toRadians(lat2)) *
    Math.sin(lonDiff / 2) * Math.sin(lonDiff / 2);
  var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
  var distance = earthRadius * c;

  return distance;
}

	function toRadians(degree) {
	  return degree * (Math.PI / 180);
	}
</script>
<%@ include file="../common/foot.jspf"%>