<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="recommend" />
<%@ include file="../common/head.jspf"%>
<!-- <link href="https://cdn.jsdelivr.net/npm/daisyui@2.51.6/dist/full.css" rel="stylesheet" type="text/css" />
<link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2/dist/tailwind.min.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tailwindcss/2.2.7/tailwind.min.css" />
 -->
<div class="absolute text-center top-24 blinking-text" style="left: 320px;">최근 기준 네이버 인플루언서들이 추천하는 해수욕장 20개</div>

<svg class="svg-overlay" width="0" height="0" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
  			<filter id="water">
    		<feTurbulence type="fractalNoise" baseFrequency=".05 .05" numOctaves="1" result="noise1"></feTurbulence>
    		<feColorMatrix in="noise1" type="hueRotate" values="0" result="noise2">
      		<animate attributeName="values" from="0" to="360" dur="1s" repeatCount="indefinite" />
    		</feColorMatrix>
   	 		<feDisplacementMap xChannelSelector="R" yChannelSelector="G" scale="7" in="SourceGraphic" in2="noise2" />
  			</filter>
		</svg>

<div class="img2">
	<div class="background-image"></div>
	<div class="table-container">
		<table class="table table-zebra z-10 top-24" style="left: 270px; width: 800px;">
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
						<c:forEach var="recommend" items="${recommendList}">
								<tr class="hover">
										<td>
												<a href="${recommend.url}">
														<div class="image-container"></div>
												</a>
										</td>
										<td>
												<a href="${recommend.url}" class="block">
														<span class="block" style="white-space: normal;">${recommend.subject}</span>
												</a>
										</td>
								</tr>
						</c:forEach>
				</tbody>
			</table>
		</div>
</div>

<script>
	// 이미지 개수 설정
	var imageCount = ${recommendCount};

	// 랜덤 이미지 가져오기 및 HTML 표시
	for (var i = 0; i < imageCount; i++) {
		var imageUrl = "https://picsum.photos/300/300?random=" + i;
		var imageHtml = "<img style='margin-right: auto; margin-left: auto;' src='" + imageUrl + "' alt='랜덤 이미지'>";
		var imageContainer = document.querySelectorAll('.image-container');
		imageContainer[i].innerHTML = imageHtml;
	}
</script>


<script>
  // 배경 이미지의 높이를 자동으로 조정하는 함수
  function adjustBackgroundHeight() {
    const img2 = document.querySelector('.img2');
    const backgroundImg = document.querySelector('.background-image');
    const tableContainer = document.querySelector('.table-container');
    const tableHeight = tableContainer.offsetHeight;
    backgroundImg.style.height = tableHeight + 'px';
  }

  // 페이지 로드 완료 시 배경 이미지 높이 조정
  window.addEventListener('load', adjustBackgroundHeight);
</script>

<%@ include file="../common/foot.jspf"%>