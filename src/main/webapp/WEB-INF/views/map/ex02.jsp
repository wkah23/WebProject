<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>myapp</title>

<%@ include file="/inc/asset.jsp" %>

<style>

	
</style>

</head>
<body>
	<!-- template.jsp > ex01.jsp -->
	<%@ include file="/inc/header.jsp" %>
	
	<section class="main-section">
		
		<h1>Map API <small>좌표 이동 + 레벨 변경</small></h1>
		
		<div id="map" style="width: 100%; height: 400px;"></div>
		
		<hr />
		
		<div>
			<div class="btn-group" role="group">
				<input type="button" class="btn btn-default" id="btn1" value="서울역으로 이동하기" />
				<input type="button" class="btn btn-default" id="btn2" value="역삼역으로 이동하기" />
			</div>
			<hr />
			<div class="btn-group" role="group">
				<input type="button" class="btn btn-default" id="btn3" value="확대하기" />
				<input type="button" class="btn btn-default" id="btn4" value="축소하기" />
			</div>
			<hr />
			<div class="btn-group" role="group">
				<input type="button" class="btn btn-default" id="btn5" value="드래그 On/Off" />
				<input type="button" class="btn btn-default" id="btn6" value="줌 On/Off" />
			</div>
		</div>
		
	</section>	
	
	
	<%@ include file="/inc/init.jsp" %>
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=284cc1441a3e8f29115d190d0f122171"></script>
	<script>
	
		var container = document.getElementById("map");
		var options = {
			center: new daum.maps.LatLng(37.411187, 126.711868), //지도의 중앙 좌표
			level: 3 // 지도의 레벨(확대, 축소)
		};
		
		var map = new daum.maps.Map(container, options); // 지도 생성

		// ------------------------------------------------------------------------------------------

		// 추가설정
		var mapTypeControl = new daum.maps.MapTypeControl();
		map.addControl(mapTypeControl, daum.maps.ControlPosition.TOPRIGHT);
		
		var zoomControl = new daum.maps.ZoomControl();
		//map.addControl(zoomControl, daum.maps.ControlPosition.RIGHT);
		
		// 지도 드래그(이동) 유무
		map.setDraggable(false);
		
		// 지도 확대/축소 유무
		map.setZoomable(false);
		
		
		// 서울역
 		$('#btn1').click(function() {
			// 이동할 좌표?
			var seoulStation = new daum.maps.LatLng(37.555023, 126.970884);
			map.setCenter(seoulStation);
		}); 
		
		// 역삼역
 		$('#btn2').click(function() {
			var yeoksamStation = new daum.maps.LatLng(37.500669, 127.036591);
			map.setCenter(yeoksamStation);
		}); 
		
		// 지도 확대/축소 제어하기
 		$('#btn3').click(function() {
			// 확대 : ZoomIn
			// map.setLevel(1);
			map.setLevel(map.getLevel() - 1);
		}); 
		
		$('#btn4').click(function() {
			// 축소 : ZoomOut
			// map.setLevel(14);
			map.setLevel(map.getLevel() + 1);
		}); 
		
		
		// 드래그 On/Off
		$('#btn5').click(function() {
			
			//alert(map.getDraggable());
			
			if (map.getDraggable()) {
				map.setDraggable(false);
				$(this).removeClass('btn-primary');
				$(this).addClass('btn-default');
			} else {
				map.setDraggable(true);
				$(this).removeClass('btn-default');
				$(this).addClass('btn-primary');
			}
		});
		
		
		// 줌 On/Off
		$('#btn6').click(function() {
			
			if (map.getZoomable()) {
				map.setZoomable(false);
				$(this).removeClass('btn-danger');
				$(this).addClass('btn-default');
			} else {
				map.setZoomable(true);
				$(this).removeClass('btn-default');
				$(this).addClass('btn-danger');
			}
		});
		
		
	</script>
</body>
</html>















