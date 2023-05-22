<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="MEMBER CheckPw" />
<%@ include file="../common/head.jspf"%>
<hr />

<div class="bg-checkPw">
		<article class="mt-36 text-xl">
				<div class="container mx-auto px-3">
						<div class="table-box-type-1">
								<form action="../member/doCheckPw" method="POST" class="ml-auto mr-auto" style=" width: 450px;">
										<input type="hidden" name="replaceUri" value="${param.replaceUri }" />
										<table border="1">
												<colgroup>
														<col width="200" />
												</colgroup>

												<tbody>
														<tr>
																<th style="background-color: wheat;">아이디</th>
																<td style="background-color: white;">${rq.loginedMember.loginId }</td>
														</tr>
														<tr>
																<th style="background-color: wheat;">비밀번호</th>
																<td style="background-color: white;">
																		<input required="required" class="input input-bordered w-full max-w-xs" autocomplete="off" type="text"
																				placeholder="비밀번호를 입력해주세요" name="loginPw" />
																</td>
														</tr>
														<tr>
																<th style="background-color: wheat;">버튼</th>
																<td style="background-color: white;">
																		<button type="submit" class="btn btn-active w-3/6">확인</button>
																</td>
														</tr>
												</tbody>
										</table>
								</form>
						</div>
						<div class="btns">
								<button class="btn-text-link btn btn-active btn-ghost" type="button" onclick="history.back();">뒤로가기</button>


						</div>
				</div>
		</article>
</div>


<%@ include file="../common/foot.jspf"%>