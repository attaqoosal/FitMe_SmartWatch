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
				<li class="nav-item"><a href="a_selectAll.do" class="nav-link">데이터
						출력</a></li>
				<!--    <li class="nav-item"><a href="a_insert.do" class="nav-link">데이터 삽입</a></li> -->

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
}
</style>



			<script type="text/javascript"
				src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
			<script type="text/javascript">
				$(function() {
					console.log("ready...");
					$
							.ajax({
								type : "post",
								url : "http://localhost:8090/fitMe/a_json_selectAll.do",
								dataType : "json",
								success : function(list, status, xhr) {
									console.log(list);
									$("#result").empty();
									$('<tr></tr>')
											.html(
													'<td align = \"center\">&nbsp;&nbsp;사용자아이디&nbsp;&nbsp;</td>'
															+ '<td align = \"center\">&nbsp;&nbsp;체크&nbsp;&nbsp;</td>'
															+ '<td align = \"center\">&nbsp;&nbsp;이름&nbsp;&nbsp;</td>'
															+ '<td align = \"center\">&nbsp;&nbsp;생일&nbsp;&nbsp;</td>'
															+ '<td align = \"center\">&nbsp;&nbsp;성별&nbsp;&nbsp;</td>'
															+ '<td align = \"center\">&nbsp;&nbsp;키&nbsp;&nbsp;</td>'
															+ '<td align = \"center\">&nbsp;&nbsp;몸무게&nbsp;&nbsp;</td>'
															+ '<td align = \"center\">&nbsp;&nbsp;전화번호&nbsp;&nbsp;</td>'
															+ '<td align = \"center\">&nbsp;&nbsp;삭제&nbsp;&nbsp;</td>')
											.appendTo('#result');
									$
											.each(
													list,
													function(i, obj) {
														console
																.log(obj.user_id);
														$("#result")
																.append(
																		function() {
																			var str = "<tr id=\""+obj.user_id+"\">";
																			str += "<td align = \"center\">"
																					+ obj.user_id
																					+ "</a></td>";
																			str += "<td align = \"center\">"
																					+ obj.u_check
																					+ "</td>";
																			str += "<td align = \"center\">"
																					+ obj.name
																					+ "</td>";
																			str += "<td align = \"center\">"
																					+ obj.birth
																					+ "</td>";
																			str += "<td align = \"center\">"
																					+ obj.gender
																					+ "</td>";
																			str += "<td align = \"center\">"
																					+ obj.height
																					+ "</td>";
																			str += "<td align = \"center\">"
																					+ obj.weight
																					+ "</td>";
																			str += "<td align = \"center\">"
																					+ obj.tel
																					+ "</td>";
																			str += "<td align = \"center\"><a href=\"a_deleteOK.do?user_id="
																					+ obj.user_id 
																					+ "\"><a style=\"color: black\">삭제</a></td>";
																			str += "</tr>";
																			return str;
																		});
													});
								}
							});
				});
			</script>
</head>
<body>
	<h1>사용자 전체 정보</h1>

	<table id="result">
	</table>
	</div>
	</div>
	<div class="footer">
		<p class="copyright">&copy;copy</p>
	</div>
</body>
</html>