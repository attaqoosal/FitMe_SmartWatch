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
body {
	font: 14px/1.8 Arial, Helvetica, sans-serif;
	margin: 0 auto;
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
   margin: 0 auto; 
	float: middle;
	width: 1200px;
}

.footer {
	text-align: center;
	border-top: 1px solid #aaa;
	margin: 20px 20px 0;
	font-size: 12px;
}
</style>

			<style>
table {
	width: 100%;
	border-top: 1px solid #444444;
	border-collapse: collapse;
}

th, td {
	border-bottom: 1px solid #444444;
	padding: 10px;
	text-align: center;
	
}

th {
	background-color: #bbdefb;
}

td {
	background-color: #e3f2fd;
}
</style>


<div class="frame">
	<div class="header">
		<div class="logo" style="font-family: 'Limelight', cursive;">FitMe</div>
		<p>
			<%@ include file="../menu.html"%><br>
	</div>
</div>
	<!-- //header -->
	<div class="container">
		<div class="nav">
			<ul class="nav-list">
				<li class="nav-item"><a href="p_selectAll.do" class="nav-link">데이터
						출력</a></li>
				<li class="nav-item"><a href="p_insert.do" class="nav-link">데이터
						삽입</a></li>
			</ul>
		</div>

		<div class="content">
			<h1>제품 데이터 삽입</h1>
			
			
			<style>
form {
	margin: 0 auto;
	width: 450px;
	padding: 2em;
	border: 3px solid #CCC;
	border-radius: 1em;
	
}

form div+div {
	margin-top: 1em;
}

label {
	display: inline-block;
	width: 90px;
	text-align: right;
}
input, textarea {
	font: 1em sans-serif;
	width: 300px;
	-moz-box-sizing: border-box;
	box-sizing: border-box;
	border: 1px solid #999;
}

input:focus, textarea:focus {
	border-color: #000;
}

textarea {
	vertical-align: top;
	height: 5em;
	resize: vertical;
}.button {
	margin-left: 15em;
	
}
</style>
</style>

			<form action="p_insertOK.do" method="post">

				<label>시리얼 넘버</label>: <input type="text" name="serial_num"><br>
				<label>기기이름</label>: <input type="text" name="name"><br>
				<label>제조일</label>: <input type="text"	name="manufacture_date"><br> 
				<label>가격</label>: <input type="number" name="price"><br>
				<div class="button">
				<button type="submit" class="btn_button">확인</button>
			</div> 
				<!-- <input type="submit" value="OK"> -->
			</form>
			</div>
			</div>
			</body>
</html>