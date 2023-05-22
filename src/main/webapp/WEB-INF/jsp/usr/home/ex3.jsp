<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="관련 블로그" />
<%@ include file="../common/head.jspf"%>

// 카카오 맵 API를 로드한 후 실행할 함수
<script>
function initMap() {
    // 사용자의 현재 위치를 받아오는 함수
    navigator.geolocation.getCurrentPosition(function(position) {
        // 위도(latitude)와 경도(longitude) 가져오기
        var latitude = position.coords.latitude;
        var longitude = position.coords.longitude;

        // 카카오 맵 생성 및 지도 중심 설정
        var map = new kakao.maps.Map(document.getElementById('map'), {
            center: new kakao.maps.LatLng(latitude, longitude),
            level: 3
        });

        // 카카오 맵 키워드 검색 서비스 객체 생성
        var places = new kakao.maps.services.Places();

        // 해수욕장 검색
        places.keywordSearch('해수욕장', function(result, status) {
            if (status === kakao.maps.services.Status.OK) {
                // 검색 결과 중 가장 가까운 해수욕장
                var nearestBeach = result[0];

                // 가장 가까운 해수욕장 마커 생성 및 지도에 표시
                var marker = new kakao.maps.Marker({
                    position: new kakao.maps.LatLng(nearestBeach.y, nearestBeach.x)
                });
                marker.setMap(map);

                // 가장 가까운 해수욕장 정보 출력
                var content = '<div style="padding:10px;">' +
                              '<h4>' + nearestBeach.place_name + '</h4>' +
                              '<p>' + nearestBeach.address_name + '</p>' +
                              '</div>';
                var infowindow = new kakao.maps.InfoWindow({
                    content: content
                });
                infowindow.open(map, marker);
            }
        });
    });
}
</script>