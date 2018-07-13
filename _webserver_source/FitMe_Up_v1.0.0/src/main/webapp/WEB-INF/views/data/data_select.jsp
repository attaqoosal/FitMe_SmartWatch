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
	overflow: hidden;
	text-align: center;
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
	height: 500px;
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
		</div>
	</div>
</div>
</head>




</body>
</html>