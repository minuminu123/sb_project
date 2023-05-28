<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/head.jspf"%>
<link rel="stylesheet" href="/resource/css/main2.css" />
<c:set var="pageTitle" value="채팅" />


<div class="bg-main3">

		<c:if test="${rq.isLogined() }">
				<div id="username-page" style="position: absolute; left: 600px; top: 200px;">
						<div class="username-page-container">
								<form id="usernameForm" name="usernameForm">
										<div class="form-group">
												<input type="hidden" value="${rq.loginedMember.nickname }" id="nickname" name="nickname" />
												<input type="hidden" value="${rq.loginedMemberId }" id="memberId" name="memberId" />
										</div>
										<div class="form-group">
												<button type="submit" class="btn btn-active username-submit" style="margin-top: 50px;">참여하기</button>
										</div>
								</form>
						</div>
				</div>
		</c:if>

		<c:if test="${!rq.isLogined() }">
				<div id="username-page" style="position: absolute; left: 600px; top: 200px;">
						<div class="username-page-container">
								<form id="usernameForm" method="post" action="../member/doLogin">
										<br />
										<div style="display: inline-block; text-align: left;">
												<div class="form-group" style="font-size: 15px; font-weight: bold;">
														아이디
														<br />
														<input class="form-control" type="text" placeholder="아이디" name="loginId" autocomplete="off" required />
												</div>
												<br />
												<div class="form-group" style="font-size: 15px; font-weight: bold;">
														비밀번호
														<br />
														<input class="form-control" type="password" placeholder="비밀번호" name="loginPw" autocomplete="off" required />
												</div>
												<br />
												<div class="form-group">
														<button type="submit" class="accent username-submit" style="padding: 0 40px;">로그인</button>
												</div>
										</div>
								</form>
						</div>
				</div>
		</c:if>

		<div id="chat-page" class="hidden flex justify-center" style="background-color: white; top: 100px;">
				<div class="dropdown">
						<button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown"
								aria-haspopup="true" aria-expanded="false">참가한 유저</button>
						<div id="list" class="dropdown-menu" aria-labelledby="dropdownMenuButton"></div>
				</div>
				<div class="chat-container">
						<div class="chat-header">
								<h2>${room.roomName}</h2>
						</div>
						<div class="connecting">연결중..</div>
						<ul id="messageArea">

						</ul>

						<form id="messageForm" name="messageForm" class>
								<div class="form-group">
										<div class="input-group clearfix">
												<input type="text" id="message" placeholder="Type a message..." autocomplete="off" class="form-control" />
												<button type="submit" class="primary">전송</button>
										</div>
								</div>
						</form>
				</div>
		</div>

</div>



<script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.4.0/sockjs.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
<!-- 실행되는동안 로드가 안됐으므로 아래에 위치 -->
<script src="/resource/js/main.js"></script>
<%@ include file="../common/foot.jspf"%>