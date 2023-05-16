$('select[data-value]').each(function(index, el) {
	const $el = $(el);

	const defaultValue = $el.attr('data-value').trim();

	if (defaultValue.length > 0) {
		$el.val(defaultValue);
	}
});



/* ==================== 코드 합치는 중 ============== */

$('#menu').mouseover(function() {
	$('body span').css('filter', 'blur(10px)');
	$('body  main').css('filter', 'blur(10px)');
	$('body .top-control').css('filter', 'blur(10px)');
});

$('#menu').mouseleave(function() {
	$('body span').css('filter', 'blur(0)');
	$('body  main').css('filter', 'blur(0)');
	$('body .top-control').css('filter', 'blur(0)');
});

$(document).ready(function() {

	$("#sign_up").click(function() { // when click Sign Up button, hide the Log In elements, and display the Sign Up elements
		$("#title-login").toggleClass("hidden", true);
		$("#login-fieldset").toggleClass("hidden", true);
		$("#login-form-submit").toggleClass("hidden", true);
		$("#lost-password-link").toggleClass("hidden", true);
		$("#sign_up").toggleClass("active-button", false);
		$("#log_in").removeAttr("disabled");

		$("#title-signup").toggleClass("hidden", false);
		$("#signup-fieldset").toggleClass("hidden", false);
		$("#signup-form-submit").toggleClass("hidden", false);
		$("#log_in").toggleClass("active-button", true);
		$("#sign_up").prop('disabled', true);
	});

	$("#log_in").click(function() { // when click Log In button, hide the Sign Up elements, and display the Log In elements
		$("#title-login").toggleClass("hidden", false);
		$("#login-fieldset").toggleClass("hidden", false);
		$("#login-form-submit").toggleClass("hidden", false);
		$("#lost-password-link").toggleClass("hidden", false);
		$("#sign_up").toggleClass("active-button", true);
		$("#log_in").prop('disabled', true);

		$("#title-signup").toggleClass("hidden", true);
		$("#signup-fieldset").toggleClass("hidden", true);
		$("#signup-form-submit").toggleClass("hidden", true);
		$("#log_in").toggleClass("active-button", false);
		$("#sign_up").removeAttr("disabled");

	});

	const menu = document.querySelector('header');
	const menuHeight = menu.getBoundingClientRect().height;

	document.addEventListener('scroll', () => {
		if (window.scrollY > menuHeight) {
			menu.classList.add('actived');
			menu.style.transition = '.7s';
			menu.style.opacity = 1;
		} else {
			menu.classList.remove('actived');
		}
	});



});

$(window).on('load', function() {
	var height = document.body.scrollHeight,
		x = 0, y = height / 2,
		curveX = 10,
		curveY = 0,
		targetX = 0,
		xitteration = 0,
		yitteration = 0,
		menuExpanded = false;

	blob = $('#blob'),
		blobPath = $('#blob-path'),

		hamburger = $('.hamburger');

	$(this).on('mousemove', function(e) {
		x = e.clientX;

		y = e.clientY;
	});

	$('.hamburger, .menu-inner').on('mouseenter', function() {
		$(this).parent().addClass('expanded');
		$('.hamburger').css('visibility', 'hidden');
		$('#menu').css('opacity', 1);
		menuExpanded = true;
	});

	$('.menu-inner').on('mouseleave', function() {
		menuExpanded = false;
		$(this).parent().removeClass('expanded');
		$('.hamburger').css('visibility', 'visible');
		$('#menu').css('opacity', .5);
	});

	function easeOutExpo(currentIteration, startValue, changeInValue, totalIterations) {
		return changeInValue * (-Math.pow(2, -10 * currentIteration / totalIterations) + 1) + startValue;
	}

	var hoverZone = 150;
	var expandAmount = 20;

	function svgCurve() {
		if ((curveX > x - 1) && (curveX < x + 1)) {
			xitteration = 0;
		} else {
			if (menuExpanded) {
				targetX = 0;
			} else {
				xitteration = 0;
				if (x > hoverZone) {
					targetX = 0;
				} else {
					targetX = -(((60 + expandAmount) / 100) * (x - hoverZone));
				}
			}
			xitteration++;
		}

		if ((curveY > y - 1) && (curveY < y + 1)) {
			yitteration = 0;
		} else {
			yitteration = 0;
			yitteration++;
		}

		curveX = easeOutExpo(xitteration, curveX, targetX - curveX, 100);
		curveY = easeOutExpo(yitteration, curveY, y - curveY, 100);

		//올라오는 각도
		var anchorDistance = 200;
		var curviness = anchorDistance - 40;

		var newCurve2 = "M60," + height + "H0V0h60v" + (curveY - anchorDistance) + "c0," + curviness + "," + curveX + "," + curviness + "," + curveX + "," + anchorDistance + "S60," + (curveY) + ",60," + (curveY + (anchorDistance * 2)) + "V" + height + "z";

		blobPath.attr('d', newCurve2);

		//가져다 대는 영역 커지면 가까이 안가도 커짐
		blob.width(curveX + 60);

		//hamburger.css('transform', 'translate('+curveX+'px, '+curveY+'px)');

		/*$('h2').css('transform', 'translateY('+curveY+'px)');*/
		window.requestAnimationFrame(svgCurve);
	}

	window.requestAnimationFrame(svgCurve);

});

var ani3 = anime({
	targets: ['main table'],
	translateX: '200',
	autoplay: true
});
/*
var ani4 = anime({
	targets: 'main .bg-asdf',
	translateX: '270',
	delay: anime.stagger(300)

}); */

///////////////////////////////////////////////////////////////////



//document.querySelector('body').onload = ani3.play;
