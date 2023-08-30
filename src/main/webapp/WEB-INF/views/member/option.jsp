<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>myapp</title>

<%@ include file="/inc/asset.jsp" %>

<style>

	.well > label {
		margin-right: 10px;	
	}
	
</style>

</head>
<body>
	<!-- option.jsp -->
	<%@ include file="/inc/header.jsp" %>
	
	<section class="main-section">
		
		<h1>Option</h1>
		
		<h2>Header Color</h2>
		
		<h3>Menu</h3>
		<div class="well well-sm">
		   <label><input type="radio" name="menucolor" class="menucolor" value="tomato"> <span style="background-color: tomato; display: inline-block; border-radius: 3px; width: 15px; height: 12px;">&nbsp;</span></label>
		   <label><input type="radio" name="menucolor" class="menucolor" value="cornflowerblue"> <span style="background-color: cornflowerblue; display: inline-block; border-radius: 3px; width: 15px; height: 12px;">&nbsp;</span></label>
		   <label><input type="radio" name="menucolor" class="menucolor" value="gold"> <span style="background-color: gold; display: inline-block; border-radius: 3px; width: 15px; height: 12px;">&nbsp;</span></label>
		</div>
		
		<h3>Member</h3>
		<div class="well well-sm">
		   <label><input type="radio" name="membercolor" class="membercolor" value="tomato"> <span style="background-color: tomato; display: inline-block; border-radius: 3px; width: 15px; height: 12px;">&nbsp;</span></label>
		   <label><input type="radio" name="membercolor" class="membercolor" value="cornflowerblue"> <span style="background-color: cornflowerblue; display: inline-block; border-radius: 3px; width: 15px; height: 12px;">&nbsp;</span></label>
		   <label><input type="radio" name="membercolor" class="membercolor" value="gold"> <span style="background-color: gold; display: inline-block; border-radius: 3px; width: 15px; height: 12px;">&nbsp;</span></label>
		</div>
		
	</section>	
	
	
	<%@ include file="/inc/init.jsp" %>
	<script>
	
		// Cookie ***
		// - 개인 설정 저장
		// - 장소(현재 PC에서만 읽기/쓰기 가능)
		// - 한번 저장 > 모든 페이지 접근 가능(****)
		// - 사용자 임의 삭제 가능(********) -> 중요한 데이터는 쿠키에 저장하지 말것!! -> 중요데이터는 DB로....
		
		// session ****
		// - 개인 설정 저장
		// - 장소(X) > 현재 접속 중 일때만 사용 가능
		// - 중요한 데이터를 저장가능 > 사용자 임의 삭제 불가능 > 서버 프로그램을 통해서만 삭제 가능
		// - 영구 보관 불가능(*******)
		
		// DB ******
		// - session과 cookie를 합친것보다 좋음
		// - 개인 설정 저장
		// - 장소 불문 > 어디서든 어떤 PC를 사용하든 모두 지속적으로 접근 가능
		// - 중요한 데이터 저장
		// - 영구 보관 가능
		// - 고 비용(***)
		
		$('.menucolor').eq(0).prop('checked', true);
		$('.membercolor').eq(1).prop('checked', true);
		
		$('.menucolor').change(function() {
			// 3가지 색상 중 누구를 선택했는지?
			$('.menucolor').each(function(index, item) {
				if ($(item).prop('checked')) {
					//alert($(item).val());
					$('.main-header > section > nav > ul > li').css('color', $(item).val());
					
					// 선택한 색상을 쿠키에 저장
					setCookie('menucolor', $(item).val(), 365);
				}
			});
		});

		$('.membercolor').change(function() {
			$('.membercolor').each(function(index, item) {
				if ($(item).prop('checked')) {
					$('.main-header > section > .auth > .btn-auth').css('color', $(item).val());
					setCookie('membercolor', $(item).val(), 365);
				} 
			});
		});
	
	</script>
</body>
</html>















