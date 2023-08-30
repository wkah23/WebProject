<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>myapp</title>

<%@ include file="/inc/asset.jsp" %>

<style>

	.table th { width: 120px; }
	.table td { width: 680px; }
	
	.table #content { height: 300px; }
	
</style>

</head>
<body>
	<!-- board/template.jsp > add.jsp -->
	<%@ include file="/inc/header.jsp" %>
	
	<section class="main-section">
		
		<h1>Reference <small>Add</small></h1>
		
		<form method="POST" enctype="multipart/form-data" action="/myapp/ref/addok.do">
			<table class="table table-bordered">
				<tr>
					<th>제목</th>
					<td><input type="text" name="subject" id="subject" class='form-control' required /></td>
				</tr>		
				<tr>
					<th>내용</th>
					<td>
						<textarea name="content" id="content" class="form-control" required ></textarea>
					</td>
				</tr>		
				<tr>
					<th>첨부파일</th>
					<td>
						<input type="file" name="ofile" class="form-control short"/>
					</td>
				</tr>		 
			</table>
			
			<div class="btns">
				<button type="submit" class="btn btn-primary">글쓰기</button>
				<button type="button" class="btn btn-default"
					onclick="location.href='/myapp/ref/list.do';">돌아가기</button>
			</div>
			
			<input type="hidden" name="reply" value="${ reply }" />
			<input type="hidden" name="thread" value="${ thread }" />
			<input type="hidden" name="depth" value="${ depth }" />
		</form>

	</section>	
	
	<%@ include file="/inc/init.jsp" %>
	<script>
		
	</script>
</body>
</html>















