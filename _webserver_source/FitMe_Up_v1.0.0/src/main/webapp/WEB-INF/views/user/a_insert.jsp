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
}.nav {
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
	width: 1200px;
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
		<div class="nav">
			<ul class="nav-list">
				<li class="nav-item"><a href="a_selectAll.do" class="nav-link">데이터
						출력</a></li>
				<li class="nav-item"><a href="a_insert.do" class="nav-link">데이터
						삽입</a></li>
				<li class="nav-item"><a href="a_update.do" class="nav-link">데이터
						수정</a></li>
			</ul>
		</div>



		<div class="content">
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

			<h1>사용자 데이터 삽입</h1>

			<style>
form {
	margin: 0 auto;
	width: 400px;
	padding: 1em;
	border: 1px solid #CCC;
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
</style>

			<form action="u_insertOK.do" method="post">
				<label>아이디</label>: <input type="text" name="사용자 아이디"><br>
				<label>비밀번호</label>: <input type="password" name="비밀번호"><br>
				<label>이름</label>: <input type="text" name="이름"><br>
				<label>생일</label>: <input type="text" name="생일"><br>
				<label>성별</label>: <input type="text" name="성별"><br>
				<label>키</label>: <input type="number" name="키">cm<br>
				<label>몸무게</label>: <input type="number" name="몸무게">kg<br>
				<label>전화번호</label>: <input type="text" name="전화번호"><br>

				<input type="hidden" name="u_check" value="1"><br> <input
					type="submit" value="OK">
			</form>
			</body>
</html>