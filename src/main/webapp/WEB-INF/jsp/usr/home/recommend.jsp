<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="${board.code}" />
<%@ include file="../common/head.jspf"%>


<table class="table table-zebra z-10 top-36">
		<colgroup>
				<col width="280" />
				<col width="140" />

		</colgroup>
		<thead>
				<tr>
						<th style="--tw-bg-opacity: 1; background-color: rgba(191, 219, 254, var(--tw-bg-opacity));">사진</th>
						<th style="--tw-bg-opacity: 1; background-color: rgba(191, 219, 254, var(--tw-bg-opacity));">링크</th>
				</tr>
		</thead>

		<tbody>
				<c:forEach var="recommend" items="${recommendList }">
						<tr class="hover">
								<td>
										<a href="${recommend.url}">
												<img src="${recommend.image}" style="width: 500px; height: 300px;">
										</a>
								</td>
								<td>
										<a href="${recommend.url}" class="block">
												<span class="block">${recommend.subject}</span>
										</a>
								</td>

						</tr>
				</c:forEach>
		</tbody>

</table>
<%@ include file="../common/foot.jspf"%>