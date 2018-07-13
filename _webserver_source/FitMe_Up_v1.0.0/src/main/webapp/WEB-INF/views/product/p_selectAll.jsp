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
				<li class="nav-item"><a href="p_selectAll.do" class="nav-link">데이터
						출력</a></li>
				<li class="nav-item"><a href="p_insert.do" class="nav-link">데이터
						삽입</a></li>
			
			</ul>
		</div>

		<div class="content">
<style>
table {
	width: 90%;
	border-top: 1px solid #444444;
	border-collapse: collapse;
}

th, td {
	border-bottom: 1px solid #444444;
	padding: 10px;
	text-align: center;
	color: black;
}

</style>

			<h1>등록제품 전체 정보</h1>

			<table>
				<tr>
					<th>시리얼번호</th>
					<th>기기이름</th>
					<th>제조일</th>
					<th>가격</th>
					<th>삭제<th>
				</tr>
				<c:forEach var="vo" items="${list}">
					<tr>
						<td><a href="p_selectOne.do?serial_num=${vo.serial_num}" style="color: green">${vo.serial_num}</a></td>
						<td>${vo.name}</td>
						<td>${vo.manufacture_date}</td>
						<td>${vo.price}</td>
						<td><a href="p_deleteOK.do?serial_num=${vo.serial_num}" style="color: black">삭제</a></td>
				</c:forEach>

			</table>
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
