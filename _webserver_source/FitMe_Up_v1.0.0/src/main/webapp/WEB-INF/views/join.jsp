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
	float: middle;
	width: 600px;
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
	width: 500px;
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
}

.button {
	margin-left: 15em;
	
}
</style>



<div class="frame">
	<div class="header">
		<div class="logo" style="font-family: 'Limelight', cursive;">FitMe</div>
		<div cursive;" float:left;><%@ include file="menu2.html"%><br></div>
	</div>
	<!-- //nav -->
	<div class="content">
		<h1>회원가입</h1>
		<form action="admin_joinOK.do" method=POST>

			

			<div class="Createform">
				<label>아이디:</label> <input type="text" name="user_id">
			</div>

			<div class="Createform">
				<label>비밀번호:</label> <input type="text" name="pw">
			</div>

			<div class="Createform">
				<label>이름:</label> <input type="text" name="name">
			</div>

			<div class="Createform">
				<label>생년월일:</label> <input type="text" name="birth">
			</div>

			<div class="Createform">
				<label>성별:</label><br> 
				<input type="radio" name="gender"	value="남자" checked="checked">남자</input> 
				<input type="radio"	name="gender" value="여자">여자</input>
			</div>

			<div class="Createform">
				<label>키:</label> <input type="number" name="height">
			</div>

			<div class="Createform">
				<label>몸무게:</label> <input type="number" name="weight">
			</div>

			<div class="Createform">
				<label>전화번호:</label> <input type="text" name="tel">
			</div>

			<div class="Createform">
				<input type="hidden" name="u_check" value="1">
			</div>

			<div class="button">
				<button type="submit" class="btn_button" onSubmit="return check()"
					onclick="admin_joinOK.do">가입</button>
			</div>
		</form>

	</div>
</div>
<!-- //container -->
<div class="footer">

	<!-- //footer -->
</div>



<!-- //frame -->
</html>
