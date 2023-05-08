
<link rel="stylesheet" href="/resource/css/main.css" />
<script src="/resource/common2.js" defer="defer"></script>






<div class="container">
 <div id="rgbKineticSlider" class="rgbKineticSlider"></div>
 <a href="#" class="menu">
   <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round">
  <path stroke="none" d="M0 0h24v24H0z" fill="none"/>
  <line x1="4" y1="8" x2="40" y2="8" />
  <line x1="0" y1="16" x2="40" y2="16" />
</svg>
 </a>
 <div class="wrapper">
  <div class="separator"></div>
  <div class="separator-text">Tours</div>
  <div class="ticket">
   <div class="ticket-list">
    <div class="ticket-text__wrapper">
     <span class="ticket-title">Country</span>
     <span class="ticket-subtitle">Italy</span>
    </div>
    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-chevron-down">
     <path d="m6 9 6 6 6-6" />
    </svg>
   </div>
   <div class="ticket-list">
    <div class="ticket-text__wrapper">
     <span class="ticket-title">City</span>
     <span class="ticket-subtitle">Roma</span>
    </div>
    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-chevron-down">
     <path d="m6 9 6 6 6-6" />
    </svg>
   </div>
   <div class="ticket-list">
    <div class="ticket-text__wrapper">
     <span class="ticket-title">Departing</span>
     <span class="ticket-subtitle">19 June</span>
    </div>
    <svg class="returning" xmlns="http://www.w3.org/2000/svg" class="icon icon-tabler icon-tabler-switch-horizontal" width="44" height="44" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round">
     <path stroke="none" d="M0 0h24v24H0z" fill="none" />
     <polyline points="16 3 20 7 16 11" />
     <line x1="10" y1="7" x2="20" y2="7" />
     <polyline points="8 13 4 17 8 21" />
     <line x1="4" y1="17" x2="13" y2="17" />
    </svg>
    </svg>
   </div>
   <div class="ticket-list depart">
    <div class="ticket-text__wrapper">
     <span class="ticket-title">Returning</span>
     <span class="ticket-subtitle">27 June</span>
    </div>
    </svg>
   </div>
   <div class="ticket-list">
    <div class="ticket-text__wrapper">
     <span class="ticket-title">Travelers</span>
     <span class="ticket-subtitle">2 Adults</span>
    </div>
    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-chevron-down">
     <path d="m6 9 6 6 6-6" />
    </svg>
    </svg>
   </div>
   <div class="ticket-list">
    <svg class="settings" xmlns="http://www.w3.org/2000/svg" class="icon icon-tabler icon-tabler-adjustments-horizontal" width="44" height="44" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round">
     <path stroke="none" d="M0 0h24v24H0z" fill="none" />
     <circle cx="14" cy="6" r="2" />
     <line x1="4" y1="6" x2="12" y2="6" />
     <line x1="16" y1="6" x2="20" y2="6" />
     <circle cx="8" cy="12" r="2" />
     <line x1="4" y1="12" x2="6" y2="12" />
     <line x1="10" y1="12" x2="20" y2="12" />
     <circle cx="17" cy="18" r="2" />
     <line x1="4" y1="18" x2="15" y2="18" />
     <line x1="19" y1="18" x2="20" y2="18" />
    </svg>
    </svg>
    </svg>
   </div>
   <div class="search">
    <svg xmlns="http://www.w3.org/2000/svg" class="icon icon-tabler icon-tabler-search" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round">
  <path stroke="none" d="M0 0h24v24H0z" fill="none"/>
  <circle cx="10" cy="10" r="7" />
  <line x1="21" y1="21" x2="15" y2="15" />
</svg>
   </div>
  </div>
 </div>
 <nav>
  <a href="#" class="main-nav prev" data-nav="previous">
   <div class="slider prev">
    <svg fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
     <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.2" d="M17 8l4 4m0 0l-4 4m4-4H3"></path>
    </svg>
   </div>
  </a>
  <a href="#" class="main-nav next" data-nav="next">
   <div class="slider next">
    <svg fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
     <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.2" d="M17 8l4 4m0 0l-4 4m4-4H3"></path>
    </svg>
   </div>
  </a></a>
 </nav>
</div>

<!-- 차트 예시 -->
&nbsp;
&nbsp;
&nbsp;
&nbsp;
<div>
  <canvas id="myChart" style="margin-top: 30px;"></canvas>
</div>

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<script>
  const ctx = document.getElementById('myChart');

  new Chart(ctx, {
    type: 'doughnut',
    data: {
      labels: ['Red', 'Blue', 'Yellow', 'Green', 'Purple', 'Orange'],
      datasets: [{
        label: '# of Votes',
        data: [12, 19, 3, 5, 2, 3],
        borderWidth: 1,
        color: 'black',
        borderColor: 'black',
       	backgroundColor: ['red', 'blue', 'yellow', 'green', 'purple', 'orange']
      }]
    },
    options: {
      scales: {
        y: {
          beginAtZero: true
        }
      }
    }
  });
</script>
 

<script src="https://cdn.jsdelivr.net/gh/hmongouachon/rgbKineticSlider/js/libs/pixi-filters.min.js"></script>
<script src="https://cdn.jsdelivr.net/gh/hmongouachon/rgbKineticSlider/js/libs/TweenMax.min.js"></script>	
<script src="https://cdn.jsdelivr.net/gh/hmongouachon/rgbKineticSlider/js/libs/imagesLoaded.pkgd.min.js"></script>	
<script src="https://cdn.jsdelivr.net/gh/hmongouachon/rgbKineticSlider/js/libs/pixi.min.js"></script>
<script src="https://cdn.jsdelivr.net/gh/hmongouachon/rgbKineticSlider/js/libs/pixi-filters.min.js"></script>
<script src="https://cdn.jsdelivr.net/gh/hmongouachon/rgbKineticSlider/js/rgbKineticSlider.js"></script>