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
	<!-- index.jsp -->
	<%@ include file="/inc/header.jsp" %>
	
	<section class="main-section">
		
		<c:if test="${ empty id }">
			<h1>환영합니다</h1>
			<div>로그인 후 이용해주세요.</div>
			<div>회원이 아니시면 회원가입 후 로그인 부탁드립니다.</div>
		</c:if>
		<c:if test="${ not empty id }">
			<h1>${ id }님 환영합니다!</h1>
			<div>본 게시판은 실명제 게시판입니다.</div>
			<div>우리모두 바른말 고운말을 씁시다.</div>
		</c:if>
	</section>	
	
	
	<%@ include file="/inc/init.jsp" %>
	<script>
		
	</script>
</body>
</html>















