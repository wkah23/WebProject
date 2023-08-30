<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>myapp</title>

<%@ include file="/inc/asset.jsp" %>
<script src="/myapp/asset/js/highcharts.js"></script>

<style>

	.chart {
		border: 1px solid black;
		border-radius: 10px;
		width: 600px;
		margin: 40px auto;
		padding: 20px;
	}
	
</style>

</head>
<body>
	<!-- template.jsp  > chart.jsp -->
	<%@ include file="/inc/header.jsp" %>
	
	<section class="main-section">
		
		<h1>차트</h1>
		
		<div class="chart" id="chart1"></div>
		<div class="chart" id="chart2"></div>
		
	</section>	
	
	
	<%@ include file="/inc/init.jsp" %>
	<script>
	
		Highcharts.chart('chart1', {
		    chart: {
		        plotBackgroundColor: null,
		        plotBorderWidth: null,
		        plotShadow: false,
		        type: 'pie'
		    },
		    title: {
		        text: '유저별 게시물 수'
		    },
		    tooltip: {
		        pointFormat: '{series.name}: <b>{point.y}개</b>'
		    },
		    accessibility: {
		        point: {
		            valueSuffix: '개'
		        }
		    },
		    plotOptions: {
		        pie: {
		            allowPointSelect: true,
		            cursor: 'pointer',
		            dataLabels: {
		                enabled: true,
		                format: '<b>{point.name}</b>: {point.y}개'
		            }
		        }
		    },
		    series: [
		    	{
		        name: '게시물수',
		        colorByPoint: true,
		        data: [
			        
		        	<c:forEach items="${ list1 }" var="dto">
		        	{
			            name: '${ dto.name }',
			            y: ${ dto.cnt }
			        },
			        </c:forEach>
			        
		        ]
		    }]
		});
		
		Highcharts.chart('chart2', {
		    chart: {
		        plotBackgroundColor: null,
		        plotBorderWidth: null,
		        plotShadow: false,
		        type: 'pie'
		    },
		    title: {
		        text: '유저별 댓글수'
		    },
		    tooltip: {
		        pointFormat: '{series.name}: <b>{point.y}개</b>'
		    },
		    accessibility: {
		        point: {
		            valueSuffix: '개'
		        }
		    },
		    plotOptions: {
		        pie: {
		            allowPointSelect: true,
		            cursor: 'pointer',
		            dataLabels: {
		                enabled: true,
		                format: '<b>{point.name}</b>: {point.y}개'
		            }
		        }
		    },
		    series: [
		    	{
		        name: '댓글수',
		        colorByPoint: true,
		        data: [
		        	
		        	<c:forEach items="${ list2 }" var="dto">
		        	{
			            name: '${ dto.name }',
			            y: ${ dto.cnt }
			        },
			        </c:forEach>
		        ]
		    }]
		});
		
	</script>
</body>
</html>















