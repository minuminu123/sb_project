<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="MEMBER LOGIN" />
<%@ include file="../common/head.jspf"%>
<hr />

<script>
	
	function submitLoginForm(form) {
		form.loginId.value = form.loginId.value.trim();
		if (form.loginId.value == 0) {
			alert('아이디를 입력해주세요');
			return;
		}
		form.loginPw.value = form.loginPw.value.trim();
		if (form.loginPw.value == 0) {
			alert('비밀번호를 입력해주세요');
			return;
		}
		form.submit();
	}
</script>
	
<div style="margin-top: 100px;">
		<div class="panel shadow1">
				<form class="login-form top-20" action="../member/doLogin" method="POST" onsubmit="submitLoginForm(this); return false;">
						<c:if test="${afterLoginUri == null }">
										<c:set var="afterLoginUri" value="/" />
										<input type="hidden" name="afterLoginUri" value="${param.afterLoginUri }" />
								</c:if>

						<div class="panel-switch animate__animated animate__fadeIn">
								<!-- <button type="button" id="sign_up" class="active-button">회원가입</button> -->
								<h2 class="animated fadeInUp animate1 text-center" id="title-login" style="color: white; font-size: 2rem;">로그인</h2>

						</div>
						<h1 class="animate__animated animate__fadeInUp animate1 text-center" id="title-login">Welcome Back !</h1>
						<h1 class="animate__animated animate__fadeInUp animate1 hidden text-center" id="title-signup">Welcome !</h1>
						<fieldset id="login-fieldset">
								<input class="login animate__animated animate__fadeInUp animate2 text-center"
										style="margin: 0 40px; height: 40px;" type="text" placeholder="아이디" name="loginId" autocomplete="off" />
								<input class="login animate__animated animate__fadeInUp animate3 text-center"
										style="margin: 0 40px; height: 40px; margin-top: 30px;" type="password" placeholder="비밀번호" name="loginPw"
										autocomplete="off" />
								<c:set var="count" value="0"></c:set>
								<c:if test="${failCount != count }">
									<div class="mt-8" style="width: 150px; text-align: center; margin-left: auto; margin-right: auto; color: wheat;">로그인 남은 횟수: ${failCount }</div>
								</c:if>
								<c:if test="${failCount == count }">
									<div class="mt-8" style="width: 150px; text-align: center; margin-left: auto; margin-right: auto; color: wheat;">➩<a class="btn btn-active btn-ghost btn-text-link animate__animated animate__fadeIn animate5" href="${rq.findLoginIdUri }">아이디 찾기</a><div>➩<a class="btn btn-ghost btn-active btn-text-link animate__animated animate__fadeIn animate5" style="margin-top: 12px;" href="${rq.findLoginPwUri }">비밀번호찾기</a></div>
									</div>
								</c:if>
						</fieldset>

						<input type="submit" id="login-form-submit" class="login_form button animate__animated animate__fadeInUp animate4"
								style="margin: 0 40px; height: 40px; margin-top: 30px; left: 45px;" value="로그인">
						<input type="submit" id="signup-form-submit"
								class="login_form button animate__animated animate__fadeInUp animate4 hidden"
								style="margin: 0 40px; height: 40px; margin-top: 30px; left: 45px;" value="회원가입">

						<button class="login_form button animate__animated animate__fadeInUp animate4"
								style="margin: 0 40px; height: 40px; margin-top: 30px; left: 45px;" type="button" onclick="history.back();">뒤로가기</button>
				</form>
				<div class="mt-8 ml-24">
					<a class="btn btn-active btn-ghost btn-text-link animate__animated animate__fadeIn animate5" href="${rq.findLoginIdUri }">아이디 찾기</a>
					<a class="btn btn-ghost btn-active btn-text-link animate__animated animate__fadeIn animate5" style="margin-left: 20px;" href="${rq.findLoginPwUri }">비밀번호찾기</a>
				</div>
				
		</div>
</div>



<%@ include file="../common/foot.jspf"%>