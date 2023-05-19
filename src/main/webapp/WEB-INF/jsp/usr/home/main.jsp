<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="MAIN" />
<%@ include file="../common/head.jspf"%>
<script src="/resource/common3.js" defer="defer"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/gsap/3.11.5/gsap.min.js"></script>

<script src="/resource/common1.js" defer="defer"></script>
<!-- <hr /> -->

<div class="page-wrap">
		<header class="page-header">

				<main class="main-first">
						<article id="hero-1" style="--i: 5">
								<div class="hero-info">
										<h1>즐거운 여름휴가</h1>
										<h3>정보를 바다가다.</h3>
								</div>
								<div class="hero-image hi-1"></div>
						</article>

						<article id="hero-2" style="--i: 4">
								<div class="hero-info">
										<h1>좋은사람들과의 추억</h1>
										<h3>즐거움을 바다가다.</h3>
								</div>
								<div class="hero-image hi-2"></div>

						</article>

						<article id="hero-3" style="--i: 3">
								<div class="hero-info">
										<h1>무더운 더위</h1>
										<h3>시원함을 바다가다.</h3>
								</div>
								<div class="hero-image hi-3"></div>

						</article>

						<article id="hero-4" style="--i: 2">
								<div class="hero-info">
										<h1>리얼 경험으로</h1>
										<h3>신뢰성을 바다가다.</h3>
								</div>
								<div class="hero-image hi-4"></div>

						</article>

						<article id="hero-5" style="--i: 1">
								<div class="hero-info">
										<h1>정확한 정보로</h1>
										<h3>편리함을 바다가다.</h3>
								</div>
								<div class="hero-image hi-5"></div>
						</article>
				</main>

		</header>

		<section>
				<ul class="level-1">
						<li>
								<h3>Destinations</h3>
								<ul class="level-2">
										<li>
												<p>Asia</p>
												<ul class="level-3">
														<li>Bali</li>
														<li>Cambodia</li>
														<li>Georgia</li>
														<li>India</li>
														<li>Indonesia</li>
														<li>Laos</li>
														<li>Malaysia</li>
														<li>Maldives</li>
														<li>Myanmar</li>
														<li>Philippines</li>
														<li>Singapore</li>
														<li>Sri Lanka</li>
														<li>Thailand</li>
														<li>Uzbekistan</li>
														<li>Vietnam</li>
												</ul>
										</li>
										<li>
												<p>Europe</p>
												<ul class="level-3">
														<li>Czech Republic</li>
														<li>France</li>
														<li>Georgia</li>
														<li>Greece</li>
														<li>Hungary</li>
														<li>Iceland</li>
														<li>Italy</li>
														<li>Malta</li>
														<li>Netherlands</li>
														<li>Poland</li>
														<li>Portugal</li>
														<li>Spain</li>
														<li>Turkey</li>
												</ul>
										</li>
										<li>
												<p>Africa</p>
												<ul class="level-3">
														<li>Egypt</li>
														<li>Maurtius</li>
														<li>Morocco</li>
												</ul>
										</li>
										<li>
												<p>Middle East</p>
												<ul class="level-3">
														<li>Egypt</li>
														<li>Jordan</li>
														<li>Oman</li>
														<li>Turkey</li>
												</ul>
										</li>
								</ul>
						</li>
						<li>
								<h3>Travel Tips</h3>
								<ul>
										<li>Going on a trip</li>
										<li>Travel Insurance</li>
										<li>Working abroad</li>
										<li>Saving</li>
										<li>Instagram tips</li>
								</ul>
								<p>
										<small>More tips...</small>
								</p>
						</li>
						<li>
								<h3>Resources</h3>
								<ul>
										<li>Personalised travel advice</li>
										<li>Where we book our travels</li>
										<li>Become a booking agent</li>
								</ul>
								<p>
										<small>More resources...</small>
								</p>
						</li>
						<li>
								<h3>About Us</h3>
								<ul>
										<li>Our story</li>
										<li>Work with us</li>
										<li>Instagram</li>
										<li>YouTube</li>
								</ul>
						</li>
				</ul>
		</section>

</div>

<input class="input input-bordered w-3/6 ml-auto mr-auto text-center absolute z-20" type="text" name="title"
		data-aos="fade-up" placeholder="내가 원하는 해수욕장 찾기" style="top: 500px;" />




<div class="clock">

		<div class="hour">
				<div class="hr" id="hr"></div>
		</div>

		<div class="min">
				<div class="mn" id="mn"></div>
		</div>

		<div class="sec">
				<div class="sc" id="sc"></div>
		</div>

</div>


<form action="news">
<input type="text" name="text" placeholder="기사 검색"/>
</form>



<div>
		<div class="bg-main2">
				<div class="main-asdf">
						<div class="bg-asdf" data-aos="fade-left"></div>
						<div class="main-div" data-aos="fade-right">
								<p class="lorem-text">무더운 여름날 휴가 계획을 정하고 싶다고? 사람들과 소통하며 좋은 해수욕장 찾아가자! 경상도? 강원도? 전라도? 충청도? 딱 정해!</p>
								<a href="/usr/article/list?boardId=2&page=1" class="btn btn-outline btn-info mt-12 blinking-text3">추천 게시판 바로가기</a>
						</div>
				</div>
		</div>
</div>

<div class="bg-main3">
	<div class="main-asdf">
			<div class="bg-asdf2" data-aos="fade-left"></div>
			<div class="main-div" data-aos="fade-right">
					<p class="lorem-text"> 카카오맵에서 검색해서 원하는 해수욕장 찾기 (각 해수욕장 사진과 해당 해수욕장 이름 밑에 띄우게 (ex에 있는 사진))</p>
						<a href="/usr/home/MapSearch" class="btn btn-outline btn-info mt-12 blinking-text3">검색 페이지 바로가기</a>
			</div>
	</div>
</div>

<div class="bg-main2">
	<div class="main-asdf">
			<div class="bg-asdf3" data-aos="fade-left"></div>
			<div class="main-div" data-aos="fade-right">
					<p class="lorem-text"> 네이버 인플루언서가 추천 해수욕장 둘러보기</p>
						<a href="/usr/home/recommend" class="btn btn-outline btn-info mt-12 blinking-text3">인플루언서 추천 페이지 바로가기</a>
			</div>
	</div>
</div>

<%@ include file="../common/foot.jspf"%>