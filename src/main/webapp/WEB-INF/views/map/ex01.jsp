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
		
		<h1>Map API <small>기본 지도</small></h1>
		
		<div id="map" style="width: 100%; height: 400px;"></div>
		
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
		
	</script>
</body>
</html>















