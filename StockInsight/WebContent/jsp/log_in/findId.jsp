<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
<title>Stock Insight</title>
<link rel="stylesheet" type="text/css" href="/StockInsight/css/login.css" />
<jsp:include page="/jsp/header.jsp" flush="false">
	<jsp:param name="currentPage"
		value="${'/StockInsight/jsp/log_in/login.jsp'}" />
</jsp:include>
</head>
<body onload='rotate()'>

	<section id="content">
		<nav>
			ID 찾기 <br /> <img class="bar" src="/StockInsight/asset/body_titleUnderBar.jpg"
				style="padding-top: 20px; width: 121px; height: 10px; float: center;">

			<br />
			<div style="font-size: 20px; margin-top: 10px;">
				<class="welcome">가입할 때 작성한 <b>이름과 이메일</b>을 입력해주세요. 
			</div>
		</nav>

		<form method="POST" action="/StockInsight/searchFindId">
			<center>
				<table class="find_idtable">
					<br>
					<tr>
						<td>이름 :</td>
						<td><input required type="text" name="user_name" id="name"
							maxlength="12" /></td>
					</tr>
					<tr>
						<td>email :</td>
						<td><input required type="email" name="user_email" id="email"
							maxlength="50" /></td>
					</tr>
				</table>
				<input type="submit" class="findid" value=" ID 찾기 ">
			</center>
		</form>
	</section>

	<jsp:include page="/jsp/footer.jsp" />

</body>
</html>