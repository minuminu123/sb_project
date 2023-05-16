<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="recommend" />
<%@ include file="../common/head.jspf"%>
<link href="https://cdn.jsdelivr.net/npm/daisyui@2.51.6/dist/full.css" rel="stylesheet" type="text/css" />
<link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2/dist/tailwind.min.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tailwindcss/2.2.7/tailwind.min.css" />

<div class="absolute text-center top-24 blinking-text" style="left: 320px;">최근 기준 네이버 인플루언서들이 추천하는 해수욕장 20개</div>

<div class="img2">

<svg width="0" height="0" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
  <filter id="water">
    <feTurbulence type="fractalNoise" baseFrequency=".05 .05" numOctaves="1" result="noise1"></feTurbulence>
    <feColorMatrix in="noise1" type="hueRotate" values="0" result="noise2">
      <animate attributeName="values" from="0" to="360" dur="1s" repeatCount="indefinite" />
    </feColorMatrix>
    <feDisplacementMap xChannelSelector="R" yChannelSelector="G" scale="7" in="SourceGraphic" in2="noise2" />
  </filter>
</svg>


<table class="table table-zebra z-10" style="left: 45px; width: 800px; margin-top: 100px;">
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


<!-- <img class="img" src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/467/hawaii-water%20small.jpeg"
		alt="Hawaiian water crashes against the rocks">
		 -->


<script>
    // 이미지 개수 설정
    var imageCount = ${recommendCount};

    // 이미지 가져오기 및 HTML 표시
    for (var i = 0; i < imageCount; i++) {
        var imageUrl = "https://picsum.photos/300/300?random=" + i;
        var imageHtml = "<img style='margin-right: auto; margin-left: auto;' src='" + imageUrl + "' alt='랜덤 이미지'>";
        var imageContainer = document.querySelectorAll('.image-container');
        imageContainer[i].innerHTML = imageHtml;
    }
</script>


<%@ include file="../common/foot.jspf"%>