<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>myapp</title>

<%@ include file="/inc/asset.jsp" %>

<style>

	.main-section .table th { width: 120px; }
	.main-section .table td { width: 280px; text-align: left; }
	.main-section .table tr:nth-child(4) th { height: 300px; }
	
	#tblAddCommnet, #tblListComment { width: 700px; margin: 15px auto; }
	
	#tblAddComment { margin-top: 30px; }
	#tblAddComment td:nth-child(1) { width: 600px; }
	#tblAddComment td:nth-child(2) { width: 100px; }
	
	#tblListComment td:nth-child(1) { width: 600px; }
	#tblListComment td:nth-child(2) { width: 100px; }
	
	#tblListComment td {
		position: relative;
		left: 0;
		top: 0;
	}
	
	#tblListComment td span {
		position: absolute;
		right: 10px;
		bottom: 5px;
		color: #AAA;
		font-size: 11px;
	}
	
</style>

</head>
<body>
	<!-- board/template.jsp > add.jsp > view.jsp -->
	<%@ include file="/inc/header.jsp" %>
	
	<section class="main-section">
		
		<h1>Notice <small>View</small></h1>
		
		<table class="table table-bordered">
			<tr>
				<th>번호</th>
				<td>${ dto.seq }</td>
				<th>이름</th>
				<td>${ dto.name }(${ dto.id })</td>
			</tr>
			<tr>
				<th>날짜</th>
				<td>${ dto.regdate }</td>
				<th>조회수</th>
				<td>${ dto.readcount }</td>
			</tr>	
			<tr>
				<th>제목</th>
				<td colspan="3">${ dto.subject }</td>
			</tr>		
			<tr>
				<th>내용</th>
				<td colspan="3">${ dto.content }</td>
			</tr>		
		</table>
		
		<div class="btns">
			<c:if test="${ id eq 'admin' }">
				
				<c:if test="${ dto.id == id }"> <!-- 해당글의 글쓴 아이디와 로그인한 사람이 동일하다면.. -->
					<button type="button" class="btn btn-primary"
						onclick="location.href='/myapp/notice/edit.do?seq=${ dto.seq }';">수정하기</button>
					
					<button type="button" class="btn btn-primary"
						onclick="location.href='/myapp/notice/del.do?seq=${ dto.seq }';">삭제하기</button>
				</c:if>
			
			</c:if>
			
			<button type="button" class="btn btn-default"
				onclick="location.href='/myapp/notice/list.do?column=${ column }&search=${ search }';">돌아가기</button>
		</div>
		
	</section>	
	
	<%@ include file="/inc/init.jsp" %>
</body>
</html>















