<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="MEMBER MYPAGE" />
<%@ include file="../common/head.jspf"%>
<%@ page import="java.util.List"%>
<%@ page import="com.KoreaIT.smw.demo.util.Ut"%>
<%
List<String[]> filteredData = (List<String[]>) request.getAttribute("filteredData");
%>
<hr />
<div class="bg-main3">
		<article class="mt-36 text-xl">
				<div class="container mx-auto px-3">
						<div class="table-box-type-1">
								<table border="1" style="background-color: white;">
										<colgroup>
												<col width="200" />
										</colgroup>

										<tbody>
												<tr>
														<th>가입일</th>
														<td>${rq.loginedMember.regDate }</td>
												</tr>
												<tr>
														<th>아이디</th>
														<td>${rq.loginedMember.loginId }</td>
												</tr>
												<tr>
														<th>이름</th>
														<td>${rq.loginedMember.name }</td>
												</tr>
												<tr>
														<th>닉네임</th>
														<td>${rq.loginedMember.nickname }</td>
												</tr>
												<tr>
														<th>전화번호</th>
														<td>${rq.loginedMember.cellphoneNum }</td>
												</tr>
												<tr>
														<th>이메일</th>
														<td>${rq.loginedMember.email }</td>
												</tr>
												<tr>
														<th></th>
														<td>
																<a href="../member/checkPw?replaceUri=${Ut.getEncodedUri('../member/modify') }"
																		class="btn btn-active btn-ghost">회원정보 수정</a>
														</td>
												</tr>
										</tbody>
								</table>
						</div>
						<div class="btns">
								<button class="btn-text-link btn btn-active btn-ghost" type="button" onclick="history.back();">뒤로가기</button>


						</div>
				</div>
		</article>
		<div style="height: 600px;"></div>
		<table class="table table-zebra z-10" style="margin-left: 70px;">
				<colgroup>
						<col width="70" />
						<col width="140" />
						<col width="140" />
						<col width="140" />
						<col width="70" />
						<col width="70" />
						<col width="70" />
				</colgroup>
				<thead>
						<tr>
								<th style="--tw-bg-opacity: 1; background-color: rgba(191, 219, 254, var(--tw-bg-opacity));">번호</th>
								<th style="--tw-bg-opacity: 1; background-color: rgba(191, 219, 254, var(--tw-bg-opacity));">날짜</th>
								<th style="--tw-bg-opacity: 1; background-color: rgba(191, 219, 254, var(--tw-bg-opacity));">제목</th>
								<th style="--tw-bg-opacity: 1; background-color: rgba(191, 219, 254, var(--tw-bg-opacity));">작성자</th>
								<th style="--tw-bg-opacity: 1; background-color: rgba(191, 219, 254, var(--tw-bg-opacity));">조회수</th>
								<th style="--tw-bg-opacity: 1; background-color: rgba(191, 219, 254, var(--tw-bg-opacity));">좋아요</th>
								<th style="--tw-bg-opacity: 1; background-color: rgba(191, 219, 254, var(--tw-bg-opacity));">싫어요</th>
						</tr>
				</thead>

				<tbody>
						<c:forEach var="article" items="${articles }">
								<tr class="hover">
										<td>
												<div class="badge">${article.id}</div>
										</td>
										<td>${article.regDate.substring(2,16)}</td>
										<td>
												<a class="hover:underline" href="${rq.getArticleDetailUriFromArticleList(article) }">
														<c:choose>
																<c:when test="${article.title.length() > 16}">
																		<span>${article.title.substring(0, 14)}...</span>
																		<span style="color: blue;">[${article.repliesCount }]</span>
																</c:when>
																<c:otherwise>
																		<span>${article.title}</span>
																		<span style="color: blue;">[${article.repliesCount }]</span>
																</c:otherwise>
														</c:choose>
												</a>
										</td>
										<td>${article.extra__writer}</td>
										<td style="border: none;">${article.hitCount}</td>
										<td style="border: none;">${article.goodReactionPoint}</td>
										<td style="border: none;">${article.badReactionPoint}</td>

								</tr>
						</c:forEach>
				</tbody>

		</table>
		<c:choose>
				<c:when test="${none }">
						<div><span>찜한 해수욕장이 존재하지 않습니다</span></div>
				</c:when>
				<c:otherwise>
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
										for (String[] row : filteredData) {
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
				</c:otherwise>
		</c:choose>

</div>




<%@ include file="../common/foot.jspf"%>