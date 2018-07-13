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

}th,td {
	text-align: left;
	color: black;
}button{
	margin-left: 15em;

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
				<li class="nav-item"><a href="p_selectAll.do" class="nav-link">데이터
						출력</a></li>
				<li class="nav-item"><a href="p_insert.do" class="nav-link">데이터
						삽입</a></li>
			
			</ul>
		</div>

		<div class="content">




	<h1>등록제품 수정</h1>
	<%@ include file="../menu.html"%><br>
	
		<form action="p_updateOK.do" method=GET>

		<table>
			<tr>
				<td><input type="hidden" name="serial_num" value="${vo.serial_num}" />
				시리얼번호:${vo.serial_num}</td>
			</tr>
			<tr>
				<td>기기이름:&nbsp;<input type="text" name="name"value="${vo.name}"></td>
			</tr>
			<tr>
				<td>제조일:&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="manufacture_date" 	value="${vo.manufacture_date}"></td>
			</tr>
			<tr>
				<td>가격:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="price" value="${vo.price}"></td>
			</tr>
			
			<tr>
				<td><button type="submit" class="btn_button" onclick="p_updateOK.do">확인</button></td>
			<!-- <input type="submit" value="업데이트"> -->
			</tr>
		</table> 
		</form>
	

</body>
</html>