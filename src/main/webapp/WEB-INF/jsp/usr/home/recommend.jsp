<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="${board.code}" />
<%@ include file="../common/head.jspf"%>
<link href="https://cdn.jsdelivr.net/npm/daisyui@2.51.6/dist/full.css" rel="stylesheet" type="text/css" />
<link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2/dist/tailwind.min.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tailwindcss/2.2.7/tailwind.min.css" />

<div class="absolute text-center top-24 blinking-text" style="left: 320px;">최근 기준 네이버 인플루언서들이 추천하는 해수욕장 20개</div>

<table class="table table-zebra z-10 top-36" style="left: 45px;">
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
                        <span class="block">${recommend.subject}</span>
                    </a>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<script>
    // 이미지 개수 설정
    var imageCount = ${recommendCount};

    // 이미지 가져오기 및 HTML 표시
    for (var i = 0; i < imageCount; i++) {
        var imageUrl = "https://picsum.photos/300/300?random=" + i;
        var imageHtml = "<img src='" + imageUrl + "' alt='랜덤 이미지'>";
        var imageContainer = document.querySelectorAll('.image-container');
        imageContainer[i].innerHTML = imageHtml;
    }
</script>

<!-- <script> unsplash api로 랜덤 사진 가져오기
const accessKey = 'K5qp5aMzr3o06hziCZzZbF1CD94ye-O0SRKrY5IO2MY';
const imageContainer = document.getElementById('image-container');
const requestInterval = 1000; // 1초 간격으로 요청

// Unsplash API 호출 함수
function getUnsplashPhoto() {
  const apiUrl = `https://api.unsplash.com/photos/random/?client_id=` + accessKey;

  fetch(apiUrl)
    .then(response => {
      if (!response.ok) {
        throw new Error('API request failed');
      }
      return response.json();
    })
    .then(data => {
      const imageUrl = data.urls.small;
      const imageElement = document.createElement('img');
      imageElement.src = imageUrl;
      imageContainer.appendChild(imageElement);
    })
    .catch(error => {
      console.log(error);
    })
    .finally(() => {
      // 일정한 간격으로 재귀적으로 호출
      setTimeout(getUnsplashPhoto, requestInterval);
    });
}

// 초기 호출
for(let i = 0; i < 5; i++) {
	getUnsplashPhoto();
}

</script> -->


<%@ include file="../common/foot.jspf"%>