<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="JOIN" />
<%@ include file="../common/head.jspf"%>
<hr />
<!-- lodash debounce -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/lodash.js/4.17.21/lodash.min.js"></script>

<script>
	let submitJoinFormDone = false;
	let validLoginId = "";
	let validEmail = "";
	let validLoginPw = "";
	let validCellphoneNum = "";
	
	function submitJoinForm(form) {
		if (submitJoinFormDone) {
			alert('처리중입니다');
			return;
		}
		form.loginId.value = form.loginId.value.trim();
		if (form.loginId.value == 0) {
			alert('아이디를 입력해주세요');
			return;
		}
		if (form.loginId.value != validLoginId) {
			alert('사용할 수 없는 아이디야');
			form.loginId.focus();
			return;
		}

		form.loginPw.value = form.loginPw.value.trim();
		if (form.loginPw.value == 0) {
			alert('비밀번호를 입력해주세요');
			return;
		}
		form.loginPwConfirm.value = form.loginPwConfirm.value.trim();
		if (form.loginPwConfirm.value == 0) {
			alert('비밀번호 확인을 입력해주세요');
			return;
		}
		if (form.loginPwConfirm.value != form.loginPw.value) {
			alert('비밀번호가 일치하지 않습니다');
			form.loginPw.focus();
			return;
		}
		form.name.value = form.name.value.trim();
		if (form.name.value == 0) {
			alert('이름을 입력해주세요');
			return;
		}
		form.nickname.value = form.nickname.value.trim();
		if (form.nickname.value == 0) {
			alert('닉네임을 입력해주세요');
			return;
		}
		form.email.value = form.email.value.trim();
		if (form.email.value == 0) {
			alert('이메일을 입력해주세요');
			return;
		}
		form.cellphoneNum.value = form.cellphoneNum.value.trim();
		if (form.cellphoneNum.value == 0) {
			alert('전화번호를 입력해주세요');
			return;
		}
		submitJoinFormDone = true;
		form.submit();
	}
	
	function checkLoginIdDup(el) {
		$('.checkDup-msg').empty();
		const form = $(el).closest('form').get(0);
		if (form.loginId.value.length == 0) {
			validLoginId = '';
			return;
		}
		if(validLoginId == form.loginId.value){
			return;
		}
		$.get('../member/getLoginIdDup', {
			isAjax : 'Y',
			loginId : form.loginId.value
		}, function(data) {
			if (data.success) {
				$('.checkDup-msg').html('<div class="mt-2">' + data.msg + '</div>')
				validLoginId = data.data1;
			} else {
				$('.checkDup-msg').html('<div class="mt-2 text-red-500">' + data.msg + '</div>')
				validLoginId = '';
			}
		}, 'json');
	}
	
	const checkLoginIdDupDebounced = _.debounce(checkLoginIdDup, 600);
	
	function checkEmailDup(el) {
		$('.checkDup-msg2').empty();
		const form = $(el).closest('form').get(0);
		if (form.email.value.length == 0) {
			validEmail = '';
			return;
		}
		if(validEmail == form.email.value){
			return;
		}
		$.get('../member/getEmailDup', {
			isAjax : 'Y',
			email : form.email.value
		}, function(data) {
			if (data.success) {
				$('.checkDup-msg2').html('<div class="mt-2">' + data.msg + '</div>')
				validEmail = data.data1;
			} else {
				$('.checkDup-msg2').html('<div class="mt-2 text-red-500">' + data.msg + '</div>')
				validEmail = '';
			}
		}, 'json');
	}
	
	const checkEmailDupDebounced = _.debounce(checkEmailDup, 600);
	
	function chkcellPhoneNum(el) {
		$('.checkDup-msg4').empty();
		const form = $(el).closest('form').get(0);
		if (form.cellphoneNum.value.length == 0) {
			validCellphoneNum = '';
			return;
		}
		if(validCellphoneNum == form.cellphoneNum.value){
			return;
		}
		$.get('../member/chkcellPhoneNum', {
			isAjax : 'Y',
			cellphoneNum : form.cellphoneNum.value
		}, function(data) {
			if (data.success) {
				$('.checkDup-msg4').html('<div class="mt-2">' + data.msg + '</div>')
				validCellphoneNum = data.data1;
			} else {
				$('.checkDup-msg4').html('<div class="mt-2 text-red-500">' + data.msg + '</div>')
				validCellphoneNum = '';
			}
		}, 'json');
	}
	
	const chkcellPhoneNumDebounced = _.debounce(chkcellPhoneNum, 600);
	
	function checkLoginPw(el) {
		$('.checkDup-msg3').empty();
		const form = $(el).closest('form').get(0);
		if (form.loginPw.value.length == 0) {
			validLoginPw = '';
			return;
		}
		if(validLoginPw == form.loginPw.value){
			return;
		}
		$.get('../member/getLoginPwDup', {
			isAjax : 'Y',
			loginPw : form.loginPw.value
		}, function(data) {
			if (data.success) {
				$('.checkDup-msg3').html('<div class="mt-2">' + data.msg + '</div>')
				validLoginPw = data.data1;
			} else {
				$('.checkDup-msg3').html('<div class="mt-2 text-red-500">' + data.msg + '</div>')
				validLoginPw = '';
			}
		}, 'json');
	}
	
	const checkLoginPwDebounced = _.debounce(checkLoginPw, 600);
	
	// DOMContentLoaded 이벤트 리스너 등록
	  document.addEventListener('DOMContentLoaded', function () {
	// 입력칸과 에러 메시지 요소 가져오기
	  var passwordInput = document.getElementById('password');
	  var confirmPasswordInput = document.getElementById('confirmPassword');
	  var errorMessage = document.getElementById('error-message');

	  // 입력칸 변경 이벤트 리스너 등록
	  confirmPasswordInput.addEventListener('blur', validatePassword);

	  // 비밀번호 확인 함수
	  function validatePassword() {
	    var password = passwordInput.value;
	    var confirmPassword = confirmPasswordInput.value;

	    // 비밀번호와 비밀번호 확인이 일치하지 않으면 에러 메시지 표시
	    if (password !== confirmPassword) {
	      errorMessage.textContent = '비밀번호가 일치하지 않습니다.';
	    } else {
	      errorMessage.textContent = ''; // 일치하면 에러 메시지 제거
	    }
	  }
	// confirmPassord 입력칸의 변경 이벤트 리스너 등록
	    confirmPasswordInput.addEventListener('input', function () {
	      // confirmPassword 입력칸이 비워지면 에러 메시지 숨기기
	      if (confirmPasswordInput.value === '') {
	        errorMessage.textContent = '';
	      }
	    });
	  });
</script>


<div style="margin-top: 100px;">
		<div class="panel shadow1">
				<form class="login-form top-20" action="../member/doJoin" method="POST" onsubmit="submitJoinForm(this); return false;">
						<input type="hidden" name="afterLoginUri" value="${param.afterLoginUri}" />

						<div class="panel-switch animate__animated animate__fadeIn">
								<h2 class="animated fadeInUp animate1 text-center" id="title-login" style="color: white; font-size: 2rem;">회원가입</h2>

						</div>
						<h1 class="animate__animated animate__fadeInUp animate1 text-center" id="title-login">Welcome!</h1>
						<fieldset id="login-fieldset">
								<input onkeyup="checkLoginIdDupDebounced(this);" class="login animate__animated animate__fadeInUp animate2 text-center"
										style="margin: 0 40px; height: 40px;" type="text" placeholder="아이디" name="loginId" autocomplete="off" />
										<div class="checkDup-msg text-center"></div>
								<input onkeyup="checkLoginPwDebounced(this);" class="login animate__animated animate__fadeInUp animate3 text-center"
										style="margin: 0 40px; height: 40px; margin-top: 30px;" type="password" placeholder="비밀번호" name="loginPw"
										autocomplete="off" id="password"/>
										<div class="checkDup-msg3 text-center"></div>
								<input name="loginPwConfirm" id="confirmPassword" class="login animate__animated animate__fadeInUp animate3 text-center" placeholder="비밀번호 확인 (8자이상 특수문자1개 이상)" type="password" style="margin: 0 40px; height: 40px; margin-top: 30px;"/>
										<div id="error-message" style="color: red; text-align: center; margin-top: 7px;"></div>
								<input name="name" class="login animate__animated animate__fadeInUp animate3 text-center" placeholder="이름을 입력해주세요" style="margin: 0 40px; height: 40px; margin-top: 30px;"/>
								<input name="nickname" class="login animate__animated animate__fadeInUp animate3 text-center" placeholder="닉네임을 입력해주세요" style="margin: 0 40px; height: 40px; margin-top: 30px;"/>
								<input name="cellphoneNum" onkeyup="chkcellPhoneNumDebounced(this)" class="login animate__animated animate__fadeInUp animate3 text-center" placeholder="전화번호를 입력해주세요" style="margin: 0 40px; height: 40px; margin-top: 30px;"/>
									<div class="checkDup-msg4 text-center"></div>
								<input onkeyup="checkEmailDupDebounced(this);" name="email" class="login animate__animated animate__fadeInUp animate3 text-center" placeholder="이메일을 입력해주세요(gmail만 가능)" style="margin: 0 40px; height: 40px; margin-top: 30px;"/>
									<div class="checkDup-msg2 text-center"></div>
						</fieldset>

						<input type="submit" id="login-form-submit" class="login_form button animate__animated animate__fadeInUp animate4"
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