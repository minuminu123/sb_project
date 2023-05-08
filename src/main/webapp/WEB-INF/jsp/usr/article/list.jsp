<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="${board.code}" />
<%@ include file="../common/head.jspf"%>
<hr />

<article class="text-xl list">
		<div class="bg-list">
				<div class="container mx-auto px-3">
						<div class="table-box-type-1">
								<div class="flex mb-4">

										<div class="flex-grow"></div>
										<form action="">
												<input type="hidden" name="boardId" value="${param.boardId }" />
												<select data-value="${param.searchKeywordTypeCode }" name="searchKeywordTypeCode"
														class="select select-ghost" style="top: 130px; position: absolute; left: 500px;">
														<option value="title">제목</option>
														<option value="body">내용</option>
														<option value="title,body">제목 + 내용</option>
												</select>
												<input value="${param.searchKeyword }" maxlength="20" name="searchKeyword" class="input input-bordered"
														type="text" placeholder="검색어를 입력해주세요" style="top: 130px; position: absolute; left: 700px;"/>
												<button class="btn btn-ghost" style="top: 130px; position: absolute; left: 1000px;" type=submit>검색</button>
										</form>
								</div>
								<table class="table table-zebra z-10 mt-60">
										<colgroup>
												<col width="70" />
												<col width="140" />
												<col width="140" />
												<col width="140" />
												<col width="140" />
												<col width="140" />
												<col width="140" />
												<col width="140" />
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
																		<a class="hover:underline" href="${rq.getArticleDetailUriFromArticleList(article) }">${article.title}</a>
																</td>
																<td>${article.extra__writer}</td>
																<td>${article.hitCount}</td>
																<td>${article.goodReactionPoint}</td>
																<td>${article.badReactionPoint}</td>

														</tr>
												</c:forEach>
										</tbody>

								</table>
						</div>
						&nbsp;
						<c:if test="${rq.isLogined() }">
								<a class="btn-text-link btn btn-active btn-ghost a-middle" href="../article/write">게시글 쓰기</a>
						</c:if>
						&nbsp; &nbsp;
						<div class="pagination flex justify-center">
								<div class="btn-group">

										<c:set var="paginationLen" value="4" />
										<c:set var="startPage" value="${page - paginationLen >= 1 ? page - paginationLen : 1}" />
										<c:set var="endPage" value="${page + paginationLen <= pagesCount ? page + paginationLen : pagesCount}" />

										<c:set var="baseUri" value="?boardId=${boardId }" />
										<c:set var="baseUri" value="${baseUri }&searchKeywordTypeCode=${searchKeywordTypeCode}" />
										<c:set var="baseUri" value="${baseUri }&searchKeyword=${searchKeyword}" />

										<c:if test="${startPage > 1 }">
												<a class="btn" href="${baseUri }&page=1">1</a>
												<button class="btn btn-disabled">...</button>
										</c:if>

										<c:forEach begin="${startPage }" end="${endPage }" var="i">
												<a class="btn ${page == i ? 'btn-active' : '' }" href="${baseUri }&page=${i}">${i }</a>
										</c:forEach>

										<c:if test="${endPage < pagesCount }">
												<button class="btn btn-disabled">...</button>
												<a class="btn" href="${baseUri }&page=${pagesCount}">${pagesCount }</a>
										</c:if>
								</div>
						</div>
				</div>
		</div>

</article>

<%@ include file="../common/foot.jspf"%>