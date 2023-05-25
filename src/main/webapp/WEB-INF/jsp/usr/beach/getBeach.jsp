<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="관련 블로그" />
<%
List<String> imageUrls = (List<String>) request.getAttribute("imageUrls");
%>
<%@ include file="../common/head.jspf"%>
<!-- <script src="/resource/common2.js" defer="defer"></script> -->

<script src="https://cdn.jsdelivr.net/gh/hmongouachon/rgbKineticSlider/js/libs/pixi-filters.min.js"></script>
<script src="https://cdn.jsdelivr.net/gh/hmongouachon/rgbKineticSlider/js/libs/TweenMax.min.js"></script>
<script src="https://cdn.jsdelivr.net/gh/hmongouachon/rgbKineticSlider/js/libs/imagesLoaded.pkgd.min.js"></script>
<script src="https://cdn.jsdelivr.net/gh/hmongouachon/rgbKineticSlider/js/libs/pixi.min.js"></script>
<script src="https://cdn.jsdelivr.net/gh/hmongouachon/rgbKineticSlider/js/libs/pixi-filters.min.js"></script>
<script src="https://cdn.jsdelivr.net/gh/hmongouachon/rgbKineticSlider/js/rgbKineticSlider.js"></script>

<div class="bg-getBeach">

		<h1 class="absolute text-center" style="top: 100px; left: 150px; width: 200px; font-size: 2rem; color: wheat;">관련 블로그</h1>

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

</div>

<div class="bg-getBeach">
		<h1 class="absolute text-center" style="top: 80%; left: 150px; width: 200px; font-size: 2rem; color: wheat;">관련 이미지</h1>
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


