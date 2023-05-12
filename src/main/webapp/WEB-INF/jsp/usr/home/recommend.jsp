<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="${board.code}" />
<%@ include file="../common/head.jspf"%>
<link href="https://cdn.jsdelivr.net/npm/daisyui@2.51.6/dist/full.css" rel="stylesheet" type="text/css" />
<link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2/dist/tailwind.min.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tailwindcss/2.2.7/tailwind.min.css" />

   <div id="image-container"></div>

<script>
const accessKey = 'K5qp5aMzr3o06hziCZzZbF1CD94ye-O0SRKrY5IO2MY';
const imageContainer = document.getElementById('image-container');
const requestInterval = 1000; // 1초 간격으로 요청
const maxRequests = 5; // 최대 5회 반복

let requestCount = 0; // 요청 횟수 카운트

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

      // 요청 횟수 카운트 증가
      requestCount++;

      // 최대 요청 횟수에 도달하면 종료
      if (requestCount === maxRequests) {
        return;
      }

      // 일정한 간격으로 재귀적으로 호출
      setTimeout(getUnsplashPhoto, requestInterval);
    })
    .catch(error => {
      console.log(error);
    });
}

// 초기 호출
getUnsplashPhoto();
</script>



<%-- <div class="carousel w-3/6 top-36">
		<c:forEach var="recommend" items="${recommendList }">

				<a href="${recommend.url}">
						<img src="${recommend.image}" style="width: 500px; height: 300px;">
				</a>

				<a href="${recommend.url}" class="block">
						<span class="block">${recommend.subject}</span>
				</a>

		</c:forEach>
		<!-- <div id="slide1" class="carousel-item relative w-full">
    <img src="/resource/test1.jpg" class="w-full" />
    <div class="absolute flex justify-between transform -translate-y-1/2 left-5 right-5 top-1/2">
      <a href="#slide4" class="btn btn-circle"><</a> 
      <a href="#slide2" class="btn btn-circle">></a>
    </div>
  </div>  -->
</div> --%>



<%-- <table class="table table-zebra z-10 top-36">
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
				<c:forEach var="recommend" items="${recommendList }">
						<tr class="hover">
								<td>
										<a href="${recommend.url}">
												<img src="${recommend.image}" style="width: 500px; height: 300px;">
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

</table> --%>

<%@ include file="../common/foot.jspf"%>