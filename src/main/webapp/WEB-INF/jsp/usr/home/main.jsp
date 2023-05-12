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
										<h2>Travel the</h2>
										<h1>World</h1>
										<h3>Pragser Wildsee, Italy</h3>
								</div>
								<div class="hero-image hi-1"></div>
						</article>

						<article id="hero-2" style="--i: 4">
								<div class="hero-info">
										<h2>Savour the</h2>
										<h1>Journey</h1>
										<h3>Marignier, France</h3>
								</div>
								<div class="hero-image hi-2"></div>

						</article>

						<article id="hero-3" style="--i: 3">
								<div class="hero-info">
										<h2>Expand Your</h2>
										<h1>Horizons</h1>
										<h3>Hooker Valley Track, New Zealand</h3>
								</div>
								<div class="hero-image hi-3"></div>

						</article>

						<article id="hero-4" style="--i: 2">
								<div class="hero-info">
										<h2>Explore and</h2>
										<h1>Reflect</h1>
										<h3>Dolmites, Italy</h3>
								</div>
								<div class="hero-image hi-4"></div>

						</article>

						<article id="hero-5" style="--i: 1">
								<div class="hero-info">
										<h2>Change Your</h2>
										<h1>Perspective</h1>
										<h3>Phuket, Thailand</h3>
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

<svg width="0" height="0" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
  <filter id="water">
    <feTurbulence type="fractalNoise" baseFrequency=".05 .05" numOctaves="1" result="noise1"></feTurbulence>
    <feColorMatrix in="noise1" type="hueRotate" values="0" result="noise2">
      <animate attributeName="values" from="0" to="360" dur="1s" repeatCount="indefinite" />
    </feColorMatrix>
    <feDisplacementMap xChannelSelector="R" yChannelSelector="G" scale="7" in="SourceGraphic" in2="noise2" />
  </filter>
</svg>
<input class="input input-bordered w-3/6 ml-auto mr-auto text-center absolute z-20" type="text" name="title"
		data-aos="fade-up" placeholder="내가 원하는 해수욕장 찾기" style="top: 500px;" />
<img class="img" src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/467/hawaii-water%20small.jpeg"
		alt="Hawaiian water crashes against the rocks">



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






<div>
		<div class="bg-main">
				<div class="main-asdf">
						<div class="bg-asdf" data-aos="fade-left"></div>
						<div class="main-div" data-aos="fade-right">Lorem ipsum dolor sit amet, consectetur adipisicing
								elit. Nostrum suscipit autem dolorum pariatur officiis ex tempora atque esse quidem ea porro voluptatibus ad
								dignissimos ut perspiciatis delectus aut laudantium quibusdam.Lorem ipsum dolor sit amet, consectetur
								adipisicing elit. Voluptatibus et sint aut repudiandae ea velit culpa nulla corporis facere quae eaque ullam in
								debitis accusamus doloremque beatae dolore. Error voluptatem.Lorem ipsum dolor sit amet, consectetur adipisicing
								elit. Quae voluptatum dolor voluptates alias veritatis voluptatibus officia recusandae sunt incidunt autem porro
								repellat necessitatibus quia ducimus cumque delectus dolores rerum! Possimus.</div>

				</div>

				<!-- 				<div class="main-asdf">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Minima molestias a et
						consequatur dolor qui optio sapiente ex aliquid excepturi repudiandae quam fuga? Porro fugiat ad libero aliquam
						eveniet quod.</div> -->
		</div>
</div>

<div class="bg-main2"></div>
Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ducimus atque sint molestias distinctio eaque nisi hic! Sequi
explicabo quis ducimus animi deleniti praesentium cupiditate sapiente delectus inventore cum nobis temporibus! Lorem
ipsum dolor sit amet, consectetur adipisicing elit. Est incidunt odit dolorem porro explicabo doloremque cumque alias!
Suscipit aspernatur in harum repellat saepe fugit reiciendis similique obcaecati pariatur consequuntur eius. Lorem ipsum
dolor sit amet, consectetur adipisicing elit. Ducimus atque sint molestias distinctio eaque nisi hic! Sequi explicabo
quis ducimus animi deleniti praesentium cupiditate sapiente delectus inventore cum nobis temporibus! Lorem ipsum dolor
sit amet, consectetur adipisicing elit. Est incidunt odit dolorem porro explicabo doloremque cumque alias! Suscipit
aspernatur in harum repellat saepe fugit reiciendis similique obcaecati pariatur consequuntur eius. Lorem ipsum dolor
sit amet, consectetur adipisicing elit. Ducimus atque sint molestias distinctio eaque nisi hic! Sequi explicabo quis
ducimus animi deleniti praesentium cupiditate sapiente delectus inventore cum nobis temporibus! Lorem ipsum dolor sit
amet, consectetur adipisicing elit. Est incidunt odit dolorem porro explicabo doloremque cumque alias! Suscipit
aspernatur in harum repellat saepe fugit reiciendis similique obcaecati pariatur consequuntur eius. Lorem ipsum dolor
sit amet, consectetur adipisicing elit. Ducimus atque sint molestias distinctio eaque nisi hic! Sequi explicabo quis
ducimus animi deleniti praesentium cupiditate sapiente delectus inventore cum nobis temporibus! Lorem ipsum dolor sit
amet, consectetur adipisicing elit. Est incidunt odit dolorem porro explicabo doloremque cumque alias! Suscipit
aspernatur in harum repellat saepe fugit reiciendis similique obcaecati pariatur consequuntur eius. Lorem ipsum dolor
sit amet, consectetur adipisicing elit. Ducimus atque sint molestias distinctio eaque nisi hic! Sequi explicabo quis
ducimus animi deleniti praesentium cupiditate sapiente delectus inventore cum nobis temporibus! Lorem ipsum dolor sit
amet, consectetur adipisicing elit. Est incidunt odit dolorem porro explicabo doloremque cumque alias! Suscipit
aspernatur in harum repellat saepe fugit reiciendis similique obcaecati pariatur consequuntur eius. Lorem ipsum dolor
sit amet, consectetur adipisicing elit. Ducimus atque sint molestias distinctio eaque nisi hic! Sequi explicabo quis
ducimus animi deleniti praesentium cupiditate sapiente delectus inventore cum nobis temporibus! Lorem ipsum dolor sit
amet, consectetur adipisicing elit. Est incidunt odit dolorem porro explicabo doloremque cumque alias! Suscipit
aspernatur in harum repellat saepe fugit reiciendis similique obcaecati pariatur consequuntur eius.

<%@ include file="../common/foot.jspf"%>