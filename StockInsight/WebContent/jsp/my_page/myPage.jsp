<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
<title>Stock Insight</title>
<link rel="stylesheet" type="text/css" href="/StockInsight/css/main.css" />
<link rel="stylesheet" type="text/css" href="/StockInsight/css/myPage.css" />
<jsp:include page="/jsp/header.jsp" flush="false">
	<jsp:param name="currentPage" value="${'/StockInsight/doMyPage'}" />
</jsp:include>
</head>
<body>
	<section id="content">
		<nav>
			마이페이지 <br /> <img class="bar"
				src="/StockInsight/asset/body_titleUnderBar.jpg"
				style="padding-top: 20px; width: 121px; height: 10px; float: center;">
		</nav>
		<div class="menu_content">
			마이페이지 정보를 확인하기 전<br> 안전한 열람을 위해 비밀번호를 한번 더 입력해주세요<br>
			<p>
			<form method="POST" action="getMyPage">
				<br> <br> <br> <br>
				<center>
					<font size="3"> 패스워드 </font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input autofocus requied type="password" name="user_pwd" id="user_pwd"
				class="input_tag" placeholder="Enter your Password" />
				</center>
				<br> <br> <br> 
				 <input type="submit" 
					class="btn_submit" value=" 확 인 "><br> <br /> <br /><br> <br /> <br />
			</form>
			</center>
		</div>
	</section>
	<jsp:include page="/jsp/footer.jsp" />
</body>
</html>