<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="관련 블로그" />
<%
List<String> imageUrls = (List<String>) request.getAttribute("imageUrls");
int loginedMemberId = (int) request.getAttribute("loginedMemberId");
%>
<%@ include file="../common/head.jspf"%>
<!-- <script src="/resource/common2.js" defer="defer"></script> -->

<script>
	const params = {}
	params.id = parseInt('${param.id}');
	params.memberId = parseInt('${loginedMemberId}');
	var actorCanCancelGoodReaction = ${actorCanCancelGoodReaction};

	function checkAddRpBefore() {
		
	    <!-- 변수값에 따라 각 id가 부여된 버튼에 클래스 추가(이미 눌려있다는 색상 표시) -->
			if (actorCanCancelGoodReaction == true) {
				$('.btn-good').addClass('active');
			}
			else {
				return;
			}
		};
	
		
		$(function() {
			checkAddRpBefore();
		});
</script>

<script>

function doGoodReaction(beachId) {
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
           data: {relTypeCode: 'beach', relId: beachId},
           dataType: 'json',
           success: function(data) {
               if (data.resultCode.startsWith('S-')) {
                   var likeButton = $('.btn-good');

                   if (data.resultCode == 'S-1') {
                       likeButton.removeClass('active');
                   } 
                   else {
                        likeButton.addClass('active');
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

</script>

<script src="https://cdn.jsdelivr.net/gh/hmongouachon/rgbKineticSlider/js/libs/pixi-filters.min.js"></script>
<script src="https://cdn.jsdelivr.net/gh/hmongouachon/rgbKineticSlider/js/libs/TweenMax.min.js"></script>
<script src="https://cdn.jsdelivr.net/gh/hmongouachon/rgbKineticSlider/js/libs/imagesLoaded.pkgd.min.js"></script>
<script src="https://cdn.jsdelivr.net/gh/hmongouachon/rgbKineticSlider/js/libs/pixi.min.js"></script>
<script src="https://cdn.jsdelivr.net/gh/hmongouachon/rgbKineticSlider/js/libs/pixi-filters.min.js"></script>
<script src="https://cdn.jsdelivr.net/gh/hmongouachon/rgbKineticSlider/js/rgbKineticSlider.js"></script>

<div class="bg-getBeach">

		<h1 class="absolute text-center" style="top: 100px; left: 150px; width: 200px; font-size: 2rem; color: wheat;">관련
				블로그</h1>

		<form id="searchForm" action="/usr/beach/getBeach" method="GET" class="flex">
				<select name="sortType" id="sortType" class="select select-ghost absolute"
						style="width: 200px; top: 100px; left: 600px;" data-value="${param.sortType }">
						<option value="sim">정확도순</option>
						<option value="date">날짜순</option>
				</select>
				<input type="hidden" name="name" value="${param.name }" />
				<input type="submit" value="검색" class="btn btn-active absolute ml-12"
						style="background-color: wheat; width: 100px; top: 100px; left: 800px;">
		</form>



		<table class="table table-zebra z-10 mt-48 ml-24" id="table" style="width: 700px;">
				<thead>
						<tr>
								<td>게시 날짜</td>
								<td>블로그 제목</td>
								<td>블로그내용 요약</td>
						</tr>
				</thead>
				<tbody>
						<!-- 수정 후 코드 -->
						<c:forEach var="beach" items="${BeachDetails }">
								<c:set var="formattedDate" value="${beach.postdate.substring(0, 8)}" />
								<c:set var="year" value="${formattedDate.substring(0, 4)}" />
								<c:set var="month" value="${formattedDate.substring(4, 6)}" />
								<c:set var="day" value="${formattedDate.substring(6, 8)}" />
								<c:set var="formattedDateString" value="${year}-${month}-${day}" />
								<tr class="hover">
										<td>${formattedDateString}</td>
										<td>
												<a href="${beach.link }">${beach.title}</a>
										</td>
										<td>${beach.description.substring(0,40)}...</td>
								</tr>
						</c:forEach>
				</tbody>
		</table>

		<div>
				<span>
						<span>&nbsp;</span>


						<div class="body" style="background-color: wheat; width: 100px; border-radius: 10%; margin-left: auto; margin-right: auto;">
								<button class="btn-good like-button" onclick="doGoodReaction(${param.id})">
										<?xml version="1.0" encoding="utf-8"?>
										<svg width="20" height="20" viewBox="0 0 1792 1792" class="bi bi-hand-thumbs-down-fill" fill="currentColor"
												xmlns="http://www.w3.org/2000/svg">
																										<path
														d="M320 1344q0-26-19-45t-45-19q-27 0-45.5 19t-18.5 45q0 27 18.5 45.5t45.5 18.5q26 0 45-18.5t19-45.5zm160-512v640q0 26-19 45t-45 19h-288q-26 0-45-19t-19-45v-640q0-26 19-45t45-19h288q26 0 45 19t19 45zm1184 0q0 86-55 149 15 44 15 76 3 76-43 137 17 56 0 117-15 57-54 94 9 112-49 181-64 76-197 78h-129q-66 0-144-15.5t-121.5-29-120.5-39.5q-123-43-158-44-26-1-45-19.5t-19-44.5v-641q0-25 18-43.5t43-20.5q24-2 76-59t101-121q68-87 101-120 18-18 31-48t17.5-48.5 13.5-60.5q7-39 12.5-61t19.5-52 34-50q19-19 45-19 46 0 82.5 10.5t60 26 40 40.5 24 45 12 50 5 45 .5 39q0 38-9.5 76t-19 60-27.5 56q-3 6-10 18t-11 22-8 24h277q78 0 135 57t57 135z" /></svg>
								</button>
								<span>&nbsp;</span>

						</div>


				</span>
		</div>
</div>

<div class="bg-getBeach">
		<h1 class="absolute text-center" style="top: 80%; left: 150px; width: 200px; font-size: 2rem; color: wheat;">관련
				이미지</h1>
		<div class="container2 ml-36">
				<div id="rgbKineticSlider" class="rgbKineticSlider"></div>
				<a href="#" class="menu">
						<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" fill="none"
								stroke-linecap="round" stroke-linejoin="round">
            <path stroke="none" d="M0 0h24v24H0z" fill="none" />
            <line x1="4" y1="8" x2="40" y2="8" />
            <line x1="0" y1="16" x2="40" y2="16" />
        </svg>
				</a>
				<div class="wrapper">

						<nav>
								<a href="#" class="main-nav prev" data-nav="previous">
										<div class="slider prev">
												<svg fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.2"
																d="M17 8l4 4m0 0l-4 4m4-4H3"></path>
                    </svg>
										</div>
								</a>
								<a href="#" class="main-nav next" data-nav="next">
										<div class="slider next">
												<svg fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.2"
																d="M17 8l4 4m0 0l-4 4m4-4H3"></path>
                    </svg>
										</div>
								</a>
						</nav>
				</div>
		</div>
</div>

<%--  이미지 가져오기 예시   <%
for (String imageUrl : imageUrls) {
%>
<img src="<%=imageUrl%>" alt="Image">
<%
}
%> --%>

<script>
	const images = [];
	<%for (String imageUrl : imageUrls) {%>
	images.push("/proxy-image?imageUrl=<%=imageUrl%>");
<%}%>
	console.log(images);

	const texts = [ [ "", "\"이미지위에서 스와이프시 슬라이드 가능\"" ],
			[ "", "\"이미지위에서 스와이프시 슬라이드 가능\"" ],
			[ "", "\"이미지위에서 스와이프시 슬라이드 가능\"" ],
			[ "", "\"이미지위에서 스와이프시 슬라이드 가능\"" ] ];

	rgbKineticSlider = new rgbKineticSlider(
			{
				slideImages : images,
				itemsTitles : texts,

				backgroundDisplacementSprite : "https://i.ibb.co/N246LxD/map-9.jpg",
				cursorDisplacementSprite : "https://i.ibb.co/KrVr51f/displace-circle.png",
				cursorImgEffect : true,
				cursorTextEffect : true,
				cursorScaleIntensity : 0.65,
				cursorMomentum : 0.14,

				swipe : true,
				swipeDistance : window.innerWidth * 0.4,
				swipeScaleIntensity : 2,

				slideTransitionDuration : 1, // transition duration
				transitionScaleIntensity : 30, // scale intensity during transition
				transitionScaleAmplitude : 160, // scale amplitude during transition

				nav : true, // enable navigation
				navElement : ".main-nav", // set nav class

				imagesRgbEffect : true,
				imagesRgbIntensity : 0.9,
				navImagesRgbIntensity : 80,

				textsDisplay : true,
				textsSubTitleDisplay : true,
				textsTiltEffect : true,
				googleFonts : [ "Playfair Display:700", "Roboto:400" ],
				buttonMode : false,
				textsRgbEffect : true,
				textsRgbIntensity : 0.03,
				navTextsRgbIntensity : 15,

				textTitleColor : "white",
				textTitleSize : 125,
				mobileTextTitleSize : 80,
				textSubTitleColor : "white",
				textSubTitleSize : 21,
				mobileTextSubTitleSize : 16,
				textSubTitleLetterspacing : 2,
				textSubTitleOffsetTop : 90,
				mobileTextSubTitleOffsetTop : 90
			});
</script>


