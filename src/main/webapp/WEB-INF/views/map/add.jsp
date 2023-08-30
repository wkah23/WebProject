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
   <!-- template.jsp > ex01.jsp > ex04.jsp > add.jsp -->
   <%@ include file="/inc/header.jsp" %>
   
   <section class="main-section">
      
      <h1>Map API <small>추가하기</small></h1>
      
      <div id="map" style="width: 100%; height: 400px;"></div>
      
      <hr>
      
      <form method="POST" action="/myapp/map/addok.do">
         <table class="table table-bordered">
            <tr>
               <th style="width: 150px;">좌표</th>
               <td id="latlng" style="text-align: left;"></td>
            </tr>      
            <tr>
               <th>이름</th>
               <td><input type="text" name="name" class="form-control" required></td>
            </tr>
         </table>
         
         <input type="submit" class="btn btn-default" value="추가하기">
         <input type="button" class="btn btn-default" value="돌아가기"
            onclick="location.href='/myapp/map/map.do?no=ex04';">
            
         <input type="hidden" name="lat" id="lat">
         <input type="hidden" name="lng" id="lng">
      </form>
      
      
   </section>   
   
   
   <%@ include file="/inc/init.jsp" %>
   <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=c568aa74b3a9671ee3805242fef479b3"></script>
   <script>
   
      var container = document.getElementById('map'); //지도 태그 선택
      
      var options = {
         center: new daum.maps.LatLng(37.499331, 127.033181), //지도의 중앙 좌표
         level: 3 //지도의 레벨(확대,축소)
      };
      
      var map = new daum.maps.Map(container, options); //지도 생성(***)
      
      //----------------------------------------------------------------------
      
      let marker;
      
      daum.maps.event.addListener(map, 'click', function(evt) {
         
         //즐겨찾기 할 좌표 얻어오기
         //evt.latLng;
         
         //1. 사용자에게 출력하기
         //2. 지도에 마커로 표시하기
         //3. 서버로 전달하기 위한 밑작업
         
         //1.
         $('#latlng').text('위도(' + evt.latLng.getLat() + ') 경도(' + evt.latLng.getLng() + ')');
         
         //2.
         if (marker != null) {
            marker.setMap(null);
         }
         
         marker = new daum.maps.Marker({
            position: evt.latLng
         });
         
         marker.setMap(map);
         
         //3.
         $('#lat').val(evt.latLng.getLat());
         $('#lng').val(evt.latLng.getLng());
         
         
         
         $('input[name=name]').val('');
         $('input[name=name]').focus();
         
      });
   
   </script>
</body>
</html>














