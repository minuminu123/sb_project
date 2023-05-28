<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/head.jspf"%>

<c:set var="pageTitle" value="채팅" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css"
		integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
		integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"
		integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"
		integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k" crossorigin="anonymous"></script>
<link rel="stylesheet" href="/resource/css/main.css" />

<c:if test="${rq.isLogined() }">
		<div id="username-page">
				<div class="username-page-container">
						<form id="usernameForm" name="usernameForm">
								<div class="form-group">
 										<input type="hidden" value="${rq.loginedMemberLoginId }" id="name" name="loginId" />
										<input type="hidden" value="${rq.loginedMemberId }" id="memberId" name="memberId" />
								</div>
								<div class="form-group">
										<button type="submit" class="accent username-submit mt-48 ml-48">참여하기</button>
								</div>
						</form>
				</div>
		</div>
</c:if>

<c:if test="${!rq.isLogined() }">
		<div id="username-page">
				<div class="username-page-container">
						<form id="usernameForm" method="post" action="../member/doLogin">
								<br /> 
								<div style="display: inline-block; text-align: left;">
										<div class="form-group" style="font-size: 15px; font-weight: bold;">
												아이디 <br /> <input class="form-control" type="text" placeholder="아이디" name="loginId" autocomplete="off"
														required />
										</div>
										<br />
										<div class="form-group" style="font-size: 15px; font-weight: bold;">
												비밀번호 <br /> <input class="form-control" type="password" placeholder="비밀번호" name="loginPw"
														autocomplete="off" required />
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

<div id="chat-page" class="hidden">
		<div class="dropdown">
				<button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown"
						aria-haspopup="true" aria-expanded="false">참가한 유저</button>
				<div id="list" class="dropdown-menu" aria-labelledby="dropdownMenuButton"> </div>
		</div>
		<div class="chat-container">
				<div class="chat-header">
						<h2>${room.roomName}</h2>
				</div>
				<div class="connecting">연결중..</div>
				<ul id="messageArea">

				</ul>
				<form id="messageForm" name="messageForm">
						<div class="form-group">
								<div class="input-group clearfix">
										<input type="text" id="message" placeholder="Type a message..." autocomplete="off" class="form-control" />
										<button type="submit" class="primary">전송</button>
								</div>
						</div>
				</form>
		</div>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.4.0/sockjs.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
<script src="/resource/js/main.js"></script>
<%@ include file="../common/foot.jspf"%>