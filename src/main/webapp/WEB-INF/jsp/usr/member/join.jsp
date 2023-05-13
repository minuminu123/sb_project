<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="JOIN" />
<%@ include file="../common/head.jspf"%>

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

<article class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<form class="table-box-type-1" method="POST" action="../member/doJoin" onsubmit="submitJoinForm(this); return false;">
			<input type="hidden" name="afterLoginUri" value="${param.afterLoginUri}" />
			<table class="table table-zebra w-3/4 mt-24">
				<colgroup>
					<col width="200" />
				</colgroup>

				<tbody>
					<tr>
						<th>아이디</th>
						<td>
							<input onkeyup="checkLoginIdDupDebounced(this);" name="loginId" class="w-full input input-bordered  max-w-xs"
								placeholder="아이디를 입력해주세요" autocomplete="off" />
							<div class="checkDup-msg"></div>
						</td>
					</tr>
					<tr>
						<th>비밀번호</th>
						<td>
							<input onkeyup="checkLoginPwDebounced(this);" name="loginPw" id="password" class="w-full input input-bordered  max-w-xs" placeholder="(8자이상 특수문자1개 이상)" />
							<div class="checkDup-msg3"></div>
							
						</td>
					</tr>
					<tr>
						<th>비밀번호 확인</th>
						<td>
							<input name="loginPwConfirm" id="confirmPassword" class="w-full input input-bordered  max-w-xs" placeholder="(8자이상 특수문자1개 이상)" />
							<div id="error-message" style="color: red;"></div>
						</td>
					</tr>
					<tr>
						<th>이름</th>
						<td>
							<input name="name" class="w-full input input-bordered  max-w-xs" placeholder="이름을 입력해주세요" />
						</td>
					</tr>
					<tr>
						<th>닉네임</th>
						<td>
							<input name="nickname" class="w-full input input-bordered  max-w-xs" placeholder="닉네임을 입력해주세요" />
						</td>
					</tr>
					<tr>
						<th>전화번호</th>
						<td>
							<input name="cellphoneNum" onkeyup="chkcellPhoneNumDebounced(this)" class="w-full input input-bordered  max-w-xs" placeholder="전화번호를 입력해주세요" />
							<div class="checkDup-msg4"></div>
						</td>
					</tr>
					<tr>
						<th>이메일</th>
						<td>
							<input onkeyup="checkEmailDupDebounced(this);" name="email" class="w-full input input-bordered  max-w-xs" placeholder="이메일을 입력해주세요(gmail만 가능)" />
							<div class="checkDup-msg2"></div>
						</td>
					</tr>
					<tr>
						<th></th>
						<td>
							<button class="btn btn-active btn-ghost" type="submit" value="회원가입">회원가입</button>
						</td>
					</tr>
				</tbody>

			</table>
		</form>
	</div>

	<div class="container mx-auto btns">
		<button class="btn-text-link btn btn-active btn-ghost" type="button" onclick="history.back();">뒤로가기</button>
	</div>

</article>
<%@ include file="../common/foot.jspf"%>