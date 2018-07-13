<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<!DOCTYPE html>
<html>
<head>
<link href="https://fonts.googleapis.com/css?family=Limelight" rel="stylesheet">
<meta charset="UTF-8">
<style>
#chartdiv {
	width: 100%;
	height: 500px;
}
</style>


<script src="https://www.amcharts.com/lib/3/amcharts.js"></script>
<script src="https://www.amcharts.com/lib/3/serial.js"></script>
<script
	src="https://www.amcharts.com/lib/3/plugins/export/export.min.js"></script>
<link rel="stylesheet"
	href="https://www.amcharts.com/lib/3/plugins/export/export.css"
	type="text/css" media="all" />
<script src="https://www.amcharts.com/lib/3/themes/none.js"></script>




<style>
body {
margin: 0 auto;
	font: 14px/1.8 Arial, Helvetica, sans-serif;
}

.header {
	padding: 10px 10px;
	text-align: center;
	background: #00BCD4;
	margin-bottom: 20px;
}

.logo {

	font-size: 8em;
	//font-weight: bold;
	//background: #5457de;
	color: #FFFFFF;
	display: inline-block;
	padding: 0 8px;
}

.container {
	text-align: center;
	overflow: hidden;
}

.nav {
	float: left;
	width: 150px;
	background: #333;
	color: #fff;
	margin-right: 20px;
}

.nav-list {
	list-style: none;
	margin: 0;
	padding: 2px 0;
}

.nav-item {
	margin: 0px 0;
}

.nav-link {
	display: block;
	text-decoration: none;
	padding: 2px 2px;
	color: #fff;
	text-align: center;
}

.nav-link:hover {
	background: #5457de;
}

.content {
	float: left;
	width: 1500px;
}

.chartdiv {
	width: 100%;
	height: 600px;
}

.footer {
	text-align: center;
	border-top: 1px solid #aaa;
	margin: 20px 20px 0;
	font-size: 12px;
}
</style>


<div class="frame">
	<div class="header">
		<div class="logo" style="font-family: 'Limelight', cursive;">FitMe</div>
		<p>
			<%@ include file="../menu.html"%><br>
	</div>
	<!-- //header -->
	<div class="container">
		<div class="nav"></div>

		<div class="content">

			<h1>지금까지 많은 사용자들이 FitMe를 애용하고 있습니다.</h1>
			<h2>사용자들은 각 사람의 데이터를 통해 개인의 활동을 확인할 순 있지만, 기본적인 활동량을 채워나가고 있는지
				알지 못해요.</h2>
			<h2>보다 기준이 되는 데이터를 바탕으로 우리의 건강을 확인 해 볼까요?</h2>

			<h4>궁금한 데이터를 검색해보세요</h4>
			<select name="select" onchange="window.open(value,'_self');">
				<option value="">선택</option>
				<option value="data_popular.do">선호운동</option>
				<option value="data_avg.do">평균운동</option>
				<option value="data_real.do">실제운동</option>
			</select>




			<script type="text/javascript"
				src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
			<script>
				$(function() {
					console.log("ready...");
					$
							.ajax({
								type : "post",
								url : "http://localhost:8090/fitMe/data_json_popular.do",
								dataType : "json",
								success : function(list) {

									console.log("aaa" + list[0].type + " "
											+ list[0].max);
									var chart = AmCharts
											.makeChart(
													"chartdiv",
													{
														"type" : "serial",
														"theme" : "none",
														"rotate" : true,
														"labelText" : "[[value]]",
														"marginBottom" : 50,
														"dataProvider" : [
																{
																	"age" : "10대",
																	"male" : -list[0].max,
																	"female" : list[6].max,
																	"sport" : list[0].type
																},
																{
																	"age" : "20대",
																	"male" : -list[1].max,
																	"female" : list[7].max,
																	"sport" : list[1].type
																},
																{
																	"age" : "30대",
																	"male" : -list[2].max,
																	"female" : list[8].max,
																	"sport" : list[2].type
																},
																{
																	"age" : "40대",
																	"male" : -list[3].max,
																	"female" : list[9].max,
																	"sport" : list[3].type
																},
																{
																	"age" : "50대",
																	"male" : -list[4].max,
																	"female" : list[10].max,
																	"sport" : list[4].type
																},
																{
																	"age" : "60대+",
																	"male" : -list[5].max,
																	"female" : list[11].max,
																	"sport" : list[5].type
																} ],
														"startDuration" : 1,
														"graphs" : [
																{
																	"fillAlphas" : 0.8,
																	"lineAlpha" : 0.2,
																	"type" : "column",
																	"valueField" : "male",
																	"title" : "Male",
																	"labelText" : "[[value]]",
																	"clustered" : false,
																	"labelFunction" : function(
																			item) {
																		return item.values.sport;
																	},
																	"balloonFunction" : function(
																			item) {
																		return  item.category
																		+ ": "
																		+ [[list[1].type]]
																		+ [[list[2].type]]
																		+ "운동";
																	}
																},
																{
																	"fillAlphas" : 0.8,
																	"lineAlpha" : 0.2,
																	"type" : "column",
																	"valueField" : "female",
																	"title" : "Female",
																	"labelText" : "[[value]]",
																	"clustered" : false,
																	"labelFunction" : function(
																			item) {
																		return item.values.sport;
																	},
																	
																 	"balloonFunction" : function(
																			item) {
																		return item.category
																				+ ": "
																				+ [[list[6].type]]
														
																				+ "운동";
																	} 
																} ],

														"categoryField" : "age",
														"categoryAxis" : {
															"gridPosition" : "start",
															"gridAlpha" : 0.2,
															"axisAlpha" : 0
														},
														"valueAxes" : [ {
															"gridAlpha" : 0,
															"ignoreAxisWidth" : true,
															"labelFunction" : function(
																	value) {
																return Math
																		.abs(value)
																		+ "횟수";
															},
															"guides" : [ {
																"value" : 0,
																"lineAlpha" : 0.2
															} ]
														} ],
														"balloon" : {
														
															"fixedPosition" : true
														},
														"chartCursor" : {
															"valueBalloonsEnabled" : false,
															"cursorAlpha" : 0.05,
															"fullWidth" : true
														},
														"allLabels" : [ {
															"text" : "Male",
															"x" : "28%",
															"y" : "97%",
															"bold" : true,
															"align" : "middle"
														}, {
															"text" : "Female",
															"x" : "75%",
															"y" : "97%",
															"bold" : true,
															"align" : "middle"
														} ],
														"export" : {
															"enabled" : true
														}

													})
								}
							})
				});
			</script>

			<div id="chartdiv"></div>


		</div>
	</div>
	<!-- //container -->
	<div class="footer">
		<p class="copyright">&copy;copy</p>
	</div>
	<!-- //footer -->
</div>
<!-- //frame -->
</body>
</html>
