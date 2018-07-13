<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<!DOCTYPE html>
<html>
<head>
<link href="https://fonts.googleapis.com/css?family=Limelight"
	rel="stylesheet">
<meta charset="UTF-8">
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
	font-size: 8em; //
	font-weight: bold; //
	background: #5457de;
	color: #FFFFFF;
	display: inline-block;
	padding: 0 8px;
}

.container {
	overflow: hidden;
}

.nav {
	float: left;
	width: 150px;
	background: #333;
	color: #fff;
	margin-right: 50px;
}

.nav-list {
	list-style: none;
	margin: 0;
	padding: 10px 0;
}

.nav-item {
	margin: 4px 0;
}

.nav-link {
	display: block;
	text-decoration: none;
	padding: 4px 10px;
	color: #fff;
}

.nav-link:hover {
	background: #5457de;
}

.content {
	margin: 0 auto;
	width: 1300px;
	text-align: center;
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
			<%@ include file="menu.html"%><br>
	</div>
	<div class="content">
		<h1>Welcome to FITME</h1>
		<h2>SmartWatch</h2>
		<p>환영합니다. 현대인의 맞춤 헬스 케어 서비스 FitMe 입니다.이곳은 관리자 페이지 입니다.<br>
		<p>허락 된 관리자 외에 접근하실 수 없음을 사전에 고지합니다.<br>
		이곳에서는 FitMe 사용자들의 건강, 활동량, 운동 정보를 수집합니다. 수집된 정보는 통계 자료로 활용되어 사용자에게 보다 유익한 데이터로 제공됩니다.<br>
		FitMe의 주요기능으로는 만보기, 러닝거리, 근력운동 횟수체크, 줄넘기, 심박수체크, 수면검사 입니다. 데이터는 1시간 단위로 수집되며, 수면검사의 경우는 30분 단위로 수집됩니다.</p>
		
	</div>
</div>

<!-- //container -->
<div class="footer">

	<!-- //footer -->
</div>
<!-- //frame -->
</html>
