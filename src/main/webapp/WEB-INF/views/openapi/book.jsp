<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>myapp</title>

<%@ include file="/inc/asset.jsp" %>

<style>
 
	.table th:nth-child(1) { width: 120px; }
	.table th:nth-child(2) { width: auto; }
	.table th:nth-child(3) { width: 100px; }
	.table th:nth-child(4) { width: 110px; }
	.table th:nth-child(5) { width: 100px; }
	
</style>

</head>
<body>
	<!-- template.jsp > book.jsp -->
	<%@ include file="/inc/header.jsp" %>
	
	<section class="main-section">
	
		<h1>OpenAPI <small>네이버 책 검색</small></h1>
		
		<div class="well">
			<form method="GET" action="/myapp/openapi/book.do">
			<!-- <form>내에 텍스트 박스가 1개만 존재하면 그 텍스트 박스에서 엔터를 치면 submit 기능이 동작된다. -->
			<input type="text" name="query" class="form-control" placeholder="검색어를 입력하세요. " />
			</form>
		</div>
		
		<c:if test="${ not empty total }">
			<div class="alert alert-success">${ total }권이 책이 검색되었습니다.</div>
		</c:if>
		
		
		<table class="table table-bordered">
			<tr>
				<th>표지</th>
				<th>제목</th>
				<th>저자</th>
				<th>출판사</th>
				<th>가격</th>
			</tr>
			
			<c:if test="${ empty total }">
			<tr>
				<td colspan="5">검색어를 입력하세요.</td>
			</tr>
			</c:if>
			
			<c:if test="${ not empty total && total == 0 }">
			<tr>
				<td colspan="5">검색 결과가 없습니다.</td>
			</tr>
			</c:if>
			
			<c:if test="${ not empty total && total > 0 }">
			
				<c:forEach items="${ list }" var="book">
					<tr>
						<td><img src="${ book.image }"> </td>
						<td>
							<a href="${ book.link }" target="_blank">${ book.title }</a>
						</td>
						<td>${ book.author }</td>
						<td>${ book.publisher }</td>
						<td>
							<div style="text-decoration: line-through;">
								<fmt:formatNumber value="${ book.price }" maxFractionDigits="3" />원
							</div>
							<div>
								<fmt:formatNumber value="${ book.discount }" maxFractionDigits="3" />원
							</div>
						</td>
					</tr>
				</c:forEach>
				
			</c:if>
			
		</table>
		
	</section>	
	
	
	<%@ include file="/inc/init.jsp" %>
	<script>
		
	</script>
</body>
</html>















