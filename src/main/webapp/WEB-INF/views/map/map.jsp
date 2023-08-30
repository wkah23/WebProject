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
	<!-- template.jsp > map.jsp -->
	<%@ include file="/inc/header.jsp" %>
	
	<section class="main-section">
		
		<h1>Map API</h1>
		
		<button type="button" class="btn btn-default" 
			onclick="location.href='/myapp/map/map.do?no=ex01';">1번 예제</button>
		<button type="button" class="btn btn-default" 
			onclick="location.href='/myapp/map/map.do?no=ex02';">2번 예제</button>
		<button type="button" class="btn btn-default" 
			onclick="location.href='/myapp/map/map.do?no=ex03';">3번 예제</button>
		<button type="button" class="btn btn-default" 
			onclick="location.href='/myapp/map/map.do?no=ex04';">4번 예제</button>
		<button type="button" class="btn btn-default" 
			onclick="location.href='/myapp/map/map.do?no=ex05';">5번 예제</button>
		
	</section>	
	
	
	<%@ include file="/inc/init.jsp" %>
	<script>
		
	</script>
</body>
</html>















