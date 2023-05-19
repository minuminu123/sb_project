<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.List" %>
<%@ page import="com.KoreaIT.smw.demo.vo.Reply" %>
<%@ page import="com.KoreaIT.smw.demo.vo.ReactionPoint" %>

<c:set var="pageTitle" value="ARTICLE DETAIL" />
<%
int loginedMemberId = (int) request.getAttribute("loginedMemberId");
int reactionPointsCount = (int) request.getAttribute("reactionPointsCount");
int repliesCount = (int) request.getAttribute("repliesCount");
%>
<%
List<ReactionPoint> reactionPoints = (List<ReactionPoint>) request.getAttribute("reactionPoints");
List<Reply> replies = (List<Reply>) request.getAttribute("replies");
%>
<%@ include file="../common/head.jspf"%>
<hr />
<!-- <iframe src="http://localhost:8081/usr/article/doIncreaseHitCountRd?id=2" frameborder="0"></iframe> -->
<script>
	const params = {}
	params.id = parseInt('${param.id}');
	params.memberId = parseInt('${loginedMemberId}');
	var actorCanCancelGoodReaction = ${actorCanCancelGoodReaction};
	var actorCanCancelBadReaction = ${actorCanCancelBadReaction};

</script>

<!-- 조회수 관련 localStorage에 저장해서 다시 들어가도 조회수 오르지 않게 설정 -->
<script>
	function ArticleDetail__increaseHitCount() {
		const localStorageKey = 'article__' + params.id + '__alreadyView';
		if (localStorage.getItem(localStorageKey)) {
			return;
		}
		localStorage.setItem(localStorageKey, true);
		$.get('../article/doIncreaseHitCountRd', {
			id : params.id,
			ajaxMode : 'Y'
		}, function(data) {
			$('.article-detail__hit-count').empty().html(data.data1);
		}, 'json');
	}
	$(function() {
		// 실전코드
		// 		ArticleDetail__increaseHitCount();
		// 연습코드
		setTimeout(ArticleDetail__increaseHitCount, 2000);
	})
	
	function checkAddRpBefore() {
		
    <!-- 변수값에 따라 각 id가 부여된 버튼에 클래스 추가(이미 눌려있다는 색상 표시) -->
		if (actorCanCancelGoodReaction == true) {
			$('.btn-good').addClass('active');
		} else if (actorCanCancelBadReaction == true) {
			$('.btn-bad').addClass('active');
		} 
		else {
			return;
		}
	};
	
	function checkAddRpBefore2() {
		<%-- JSP 스크립트릿을 사용하여 JavaScript 변수에 값을 할당 --%>
		<% for (int j = 0; j < reactionPointsCount; j++) { %>
		var reactionPointRelId = <%= reactionPoints.get(j).getRelId() %>;
	    	<% for (int i = 0; i < repliesCount; i++) { %>
        	var replyId = <%= replies.get(i).getId() %>;
	    	if ((reactionPointRelId == replyId)) {
	        	$('.btn-reply-good' + replyId).addClass('active');
	    	}
	    <% } %>
	    <%}%>
	}
</script>

<script>

	$(function() {
		checkAddRpBefore();
		if(params.memberId) {
			checkAddRpBefore2();
		}
		
	});
	
	/* 댓글 좋아요 관련 : doGoodReaction에서 성공하고 난 후 S-로 시작하면 좋아요 클래스에 눌렀다는 표시와 갯수를 하나 늘린다.
	
	그리고 이미싫어요를 눌렀다면 싫어요에있던 눌렀다는 표시를 없애주고 좋아요에 옮겨 준다. */
	function doGoodReaction2(replyId) {
		 if(params.memberId==0) {
		        if(confirm('로그인이 필요한 기능입니다. 로그인 페이지로 이동하시겠습니까?')) {
		        	 var currentUri = encodeURIComponent(window.location.href);
		            window.location.href = '../member/login?afterLoginUri=' + currentUri; // 로그인 페이지 URI로 이동
		        }
		        return;
		    }
	        $.ajax({
	            url: '/usr/reactionPoint/doGoodReaction',
	            type: 'POST',
	            data: {relTypeCode: 'reply', relId: replyId},
	            dataType: 'json',
	            success: function(data) {
	                if (data.resultCode.startsWith('S-')) {
	                    var likeButton = $('.btn-reply-good' + replyId);
	                    var likeCount = $('.replygood' + replyId);
	
	                    if (data.resultCode == 'S-1') {
	                        likeButton.removeClass('active');
	                        likeCount.text(parseInt(likeCount.text()) - 1);
	                    } 
	                    else {
	                         likeButton.addClass('active');
	                         likeButton.addClass('animated');

	                        likeCount.text(parseInt(likeCount.text()) + 1);
	                    }
	                } 
	                else {
	                    alert(data.msg);
	                }
	            },
	            error: function(jqXHR, textStatus, errorThrown) {
	                alert('오류가 발생했습니다: ' + textStatus);
	            }
	        });
	    }
	
	<!-- 게시글 좋아요, 싫어요 관련 -->		
	 function doGoodReaction(articleId) {
		 if(params.memberId==0) {
		        if(confirm('로그인이 필요한 기능입니다. 로그인 페이지로 이동하시겠습니까?')) {
		        	 var currentUri = encodeURIComponent(window.location.href);
		            window.location.href = '../member/login?afterLoginUri=' + currentUri; // 로그인 페이지 URI로 이동
		        }
		        return;
		    }
	        $.ajax({
	            url: '/usr/reactionPoint/doGoodReaction',
	            type: 'POST',
	            data: {relTypeCode: 'article', relId: articleId},
	            dataType: 'json',
	            success: function(data) {
	                if (data.resultCode.startsWith('S-')) {
	                    var likeButton = $('.btn-good');
	                    var likeCount = $('.articlegood');
	                    var DislikeButton = $('.btn-bad');
	                    var DislikeCount = $('.articlebad');
	
	                    if (data.resultCode == 'S-1') {
	                        likeButton.removeClass('active');
	                        likeCount.text(parseInt(likeCount.text()) - 1);
	                    } 
	                    else if (data.resultCode == 'S-2') {
	                    	DislikeButton.removeClass('active');
	                         DislikeCount.text(parseInt(DislikeCount.text()) - 1);
	                         likeButton.addClass('active');
	                         likeCount.text(parseInt(likeCount.text()) + 1);
	                    }
	                    else {
	                         likeButton.addClass('active');
	                        likeCount.text(parseInt(likeCount.text()) + 1);
	                    }
	                } 
	                else {
	                    alert(data.msg);
	                }
	            },
	            error: function(jqXHR, textStatus, errorThrown) {
	                alert('오류가 발생했습니다: ' + textStatus);
	            }
	        });
	    }
		
	function doBadReaction(articleId) {
		if(params.memberId==0) {
	        if(confirm('로그인이 필요한 기능입니다. 로그인 페이지로 이동하시겠습니까?')) {
	        	 var currentUri = encodeURIComponent(window.location.href);
	            window.location.href = '../member/login?afterLoginUri=' + currentUri; // 로그인 페이지 URI로 이동
	        }
	        return;
	    }
	      $.ajax({
	          url: '/usr/reactionPoint/doBadReaction',
	          type: 'POST',
	          data: {relTypeCode: 'article', relId: articleId},
	          dataType: 'json',
	          success: function(data) {
	              if (data.resultCode.startsWith('S-')) {
	            	  var likeButton = $('.btn-good');
	                    var likeCount = $('.articlegood');
	                    var DislikeButton = $('.btn-bad');
	                    var DislikeCount = $('.articlebad');
	                  
	                  if (data.resultCode == 'S-1') {
	                  	DislikeButton.removeClass('active');
	                    DislikeCount.text(parseInt(DislikeCount.text()) - 1);
	                  } else if (data.resultCode == 'S-2') {
	                     	likeButton.removeClass('active');
	                     	likeCount.text(parseInt(likeCount.text()) - 1);
	                     	DislikeButton.addClass('active');
	                         DislikeCount.text(parseInt(DislikeCount.text()) + 1);
	                   } else {
	                  	DislikeButton.addClass('active');
	                    DislikeCount.text(parseInt(DislikeCount.text()) + 1);
	                  }
	              } else {
	                  alert(data.msg);
	              }
	          },
	          error: function(jqXHR, textStatus, errorThrown) {
	              alert('오류가 발생했습니다: ' + textStatus);
	          }
	      });
	  }

	/* 댓글수정을 ajax로 수행 */
	function ReplyModify__getForm(replyId, i) {
		
		$.get('../reply/getModifyForm', {
			id: replyId,
			ajaxMode: 'Y'
		}, function(data) {
			let modifyForm = $('#' + i);
			
			modifyForm.empty().html("");
			
			let addHtml = `
				<form action="../reply/doModify" method="POST" onsubmit="ReplyWrite__submitForm(this); return false;">
					<input type="hidden" name="id" value="` + data.data1.id + `" />
					<input type="hidden" name="replaceUri" value="${rq.currentUri }" />
					<div class="mt-2 p-4 rounded-lg border border-gray-400 text-base" style="margin-right: 70px;">
						<div class="mb-2"><span>작성자 : ` + data.data1.extra__writer +`</span></div>
						<textarea class="textarea textarea-bordered w-full" name="body" rows="2" placeholder="댓글 수정">` + data.data1.body + `</textarea>
						<div class="flex justify-end">
							<a href="detail?id="` + data.data1.relId + `" class="btn btn-active btn-ghost btn-sm mr-2">취소</a>
							<button class="btn btn-active btn-ghost btn-sm">등록</button>
				</form>
			</div>
		</div>`;
			
		modifyForm.append(addHtml);
		}, 'json');
		
		
	}
	
</script>
<div class="bg-detail">
<article class="mt-36 text-xl">
		<div class="container px-3" style="width: 800px; margin-left: 45px;">
				<div class="table-box-type-2">
						<table border="1" style="background-color: white;">
								<colgroup>
										<col width="200" />
								</colgroup>

								<tbody>
										<tr>
												<th>번호</th>
												<td>
														<div class="badge">${article.id}</div>
												</td>

										</tr>
										<tr>
												<th>작성날짜</th>
												<td>${article.regDate }</td>
										</tr>
										<tr>
												<th>수정날짜</th>
												<td>${article.updateDate }</td>
										</tr>
										<tr>
												<th>작성자</th>
												<td>${article.extra__writer }</td>
										</tr>
										<tr>
												<th>조회수</th>
												<td>
														<span class="article-detail__hit-count">${article.hitCount }</span>
												</td>
										</tr>
										<tr>
											<th>첨부 이미지</th>
										<td>
											<img class="w-full rounded-xl" src="${rq.getImgUri(article.id)}"
												onerror="${rq.profileFallbackImgOnErrorHtml}" alt="" />
												<div>${rq.getImgUri(article.id)}</div>
											</td>
										</tr>
										<tr>
												<th>추천</th>
												<td>
																											
														<div>
																<span>
																		<span>&nbsp;</span>

																		
																				<div class="body">
																						<button class="btn-good like-button" onclick="doGoodReaction(${param.id})">
																						<span class="articlegood">${article.goodReactionPoint }</span>
																								<?xml version="1.0" encoding="utf-8"?>
																								<svg width="20" height="20" viewBox="0 0 1792 1792" class="bi bi-hand-thumbs-down-fill" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
																										<path
																												d="M320 1344q0-26-19-45t-45-19q-27 0-45.5 19t-18.5 45q0 27 18.5 45.5t45.5 18.5q26 0 45-18.5t19-45.5zm160-512v640q0 26-19 45t-45 19h-288q-26 0-45-19t-19-45v-640q0-26 19-45t45-19h288q26 0 45 19t19 45zm1184 0q0 86-55 149 15 44 15 76 3 76-43 137 17 56 0 117-15 57-54 94 9 112-49 181-64 76-197 78h-129q-66 0-144-15.5t-121.5-29-120.5-39.5q-123-43-158-44-26-1-45-19.5t-19-44.5v-641q0-25 18-43.5t43-20.5q24-2 76-59t101-121q68-87 101-120 18-18 31-48t17.5-48.5 13.5-60.5q7-39 12.5-61t19.5-52 34-50q19-19 45-19 46 0 82.5 10.5t60 26 40 40.5 24 45 12 50 5 45 .5 39q0 38-9.5 76t-19 60-27.5 56q-3 6-10 18t-11 22-8 24h277q78 0 135 57t57 135z" /></svg>
																						</button>
																						<span>&nbsp;</span>
																						<button class="btn-bad like-button2" onclick="doBadReaction(${param.id})">
																						<span class="articlebad">${article.badReactionPoint }</span>
																								<?xml version="1.0" encoding="utf-8"?>
																								<svg width="20" height="20" viewBox="0 0 16 16" class="bi bi-hand-thumbs-down-fill" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
  <path d="M6.956 14.534c.065.936.952 1.659 1.908 1.42l.261-.065a1.378 1.378 0 0 0 1.012-.965c.22-.816.533-2.512.062-4.51.136.02.285.037.443.051.713.065 1.669.071 2.516-.211.518-.173.994-.68 1.2-1.272a1.896 1.896 0 0 0-.234-1.734c.058-.118.103-.242.138-.362.077-.27.113-.568.113-.856 0-.29-.036-.586-.113-.857a2.094 2.094 0 0 0-.16-.403c.169-.387.107-.82-.003-1.149a3.162 3.162 0 0 0-.488-.9c.054-.153.076-.313.076-.465a1.86 1.86 0 0 0-.253-.912C13.1.757 12.437.28 11.5.28H8c-.605 0-1.07.08-1.466.217a4.823 4.823 0 0 0-.97.485l-.048.029c-.504.308-.999.61-2.068.723C2.682 1.815 2 2.434 2 3.279v4c0 .851.685 1.433 1.357 1.616.849.232 1.574.787 2.132 1.41.56.626.914 1.28 1.039 1.638.199.575.356 1.54.428 2.591z"/></svg>
																						</button>
																				</div>

																		
																</span>
														</div>
														
												</td>
										</tr>

										<tr>
												<th>제목</th>
												<td>${article.title }</td>
										</tr>
										<tr>
												<th>내용</th>
												<td>${article.body }</td>
										</tr>
										<tr>
												<th></th>
												<td>
												<button class="btn-text-link btn btn-active btn-ghost btn-sm" type="button" onclick="history.back();">뒤로가기</button>
												<c:if test="${article.actorCanModify }">
													<a class="btn-text-link btn btn-active btn-ghost btn-sm" href="../article/modify?id=${article.id }">수정</a>	
												</c:if>
												<c:if test="${article.actorCanDelete }">
													<a class="btn-text-link btn btn-active btn-ghost btn-sm" onclick="if(confirm('정말 삭제하시겠습니까?')==false) return false;"
													href="../article/doDelete?id=${article.id }">삭제</a>
												</c:if>
												</td>
										</tr>
								</tbody>

						</table>
				</div>
				<div class="btns flex justify-around ml-24" style="width: 600px;">
						
						<div style="width: 300px;"></div>
						
				</div>
		</div>
</article>
</div>
<!-- 댓글 관련 -->
<script type="text/javascript">
	let ReplyWrite__submitFormDone = false;
	function ReplyWrite__submitForm(form) {
		if (ReplyWrite__submitFormDone) {
			return;
		}
		form.body.value = form.body.value.trim();
		if (form.body.value.length < 3) {
			alert('3글자 이상 입력하세요');
			form.body.focus();
			return;
		}
		ReplyWrite__submitFormDone = true;
		form.submit();
	}
</script>
<div class="bg-detail">
<div style="height: 100px;"></div>

<article style="top: 1150px; height: 300px; width: 850px; left: 200px;">

	<div class="container mx-auto px-3">
		<h1 class="text-3xl">댓글 리스트(${repliesCount })</h1>
		<c:if test="${rq.logined }">
				<form action="../reply/doWrite" method="POST" onsubmit="ReplyWrite__submitForm(this); return false;">
					<input type="hidden" name="relTypeCode" value="article" />
					<input type="hidden" name="relId" value="${article.id }" />
					<input type="hidden" name="replaceUri" value="${rq.currentUri }" />
					<div class="mt-4 p-4 rounded-lg border border-gray-400 text-base" style="background-color: wheat;">
						<div class="mb-2"><span>${rq.loginedMember.nickname }</span></div>
						<textarea class="textarea textarea-bordered w-full" name="body" rows="2" placeholder="댓글을 작성하세요"></textarea>
						<div class="flex justify-end"><button class="btn btn-active btn-ghost btn-sm btn-text-link">등록</button></div>
					</div>
				</form>
			</c:if>
			<c:if test="${rq.notLogined }">
				<div style="background-color: white;" class="flex justify-center">
					<a class="btn-text-link btn btn-active btn-ghost" href="${rq.loginUri }">로그인</a> 
					<div class="text-2xl" style="margin-top: 10px;">후 댓글을 작성할 수 있습니다</div>
				</div>
			</c:if>
		<div style="height: 100px;"></div>
		<c:forEach var="reply" items="${replies }" varStatus="status">
			<div id="${status.count }" class="py-2 pl-16 text-base" style="background-color: white;">
				<div class="font-semibold"><span>${reply.extra__writer }</span></div>
				<div><span style="word-wrap: break-word;">${reply.body }</span></div>
				<div style="border: 2px solid; display: inline-block; background-color: gray; margin-top: 10px;">
				<button class="btn-reply-good${reply.id } like-button3" onclick="doGoodReaction2(${reply.id})">
							<span class="replygood${reply.id }">${reply.goodReactionPoint }</span>
								<?xml version="1.0" encoding="utf-8"?>
								<svg width="20" height="20" viewBox="0 0 1792 1792" class="bi bi-hand-thumbs-down-fill" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
							<path
								d="M320 1344q0-26-19-45t-45-19q-27 0-45.5 19t-18.5 45q0 27 18.5 45.5t45.5 18.5q26 0 45-18.5t19-45.5zm160-512v640q0 26-19 45t-45 19h-288q-26 0-45-19t-19-45v-640q0-26 19-45t45-19h288q26 0 45 19t19 45zm1184 0q0 86-55 149 15 44 15 76 3 76-43 137 17 56 0 117-15 57-54 94 9 112-49 181-64 76-197 78h-129q-66 0-144-15.5t-121.5-29-120.5-39.5q-123-43-158-44-26-1-45-19.5t-19-44.5v-641q0-25 18-43.5t43-20.5q24-2 76-59t101-121q68-87 101-120 18-18 31-48t17.5-48.5 13.5-60.5q7-39 12.5-61t19.5-52 34-50q19-19 45-19 46 0 82.5 10.5t60 26 40 40.5 24 45 12 50 5 45 .5 39q0 38-9.5 76t-19 60-27.5 56q-3 6-10 18t-11 22-8 24h277q78 0 135 57t57 135z" /></svg>
				</button>
				</div>
				<div class="text-sm text-gray-400"><span>${reply.getForPrintRegDateType1() }</span></div>
				<div class="flex justify-end">
				<c:if test="${reply.actorCanModify }">
					<a class="btn btn-active btn-ghost btn-sm btn-text-link" onclick="ReplyModify__getForm(${reply.id },${status.count });">수정</a>							
				</c:if>
				<c:if test="${reply.actorCanDelete }">
					<a class="btn btn-active btn-ghost btn-sm btn-text-link ml-4" onclick="if(confirm('정말 삭제하시겠습니까?')==false) return false;"
					href="../reply/doDelete?id=${reply.id }&replaceUri=${rq.encodedCurrentUri}">삭제</a>
				</c:if>
				</div>
			</div>
			<hr />
		</c:forEach>
	</div>
	
</article>
</div>




<script src="/resource/detail.js" defer="defer"></script>
<%@ include file="../common/foot.jspf"%>
