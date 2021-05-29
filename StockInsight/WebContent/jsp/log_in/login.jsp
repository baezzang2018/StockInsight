<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
<title>Stock Insight</title>
<link rel="stylesheet" type="text/css"
	href="/StockInsight/css/login.css" />
<jsp:include page="/jsp/header.jsp" flush="false">
	<jsp:param name="currentPage"
		value="${'/StockInsight/jsp/log_in/login.jsp'}" />
</jsp:include>
<script>
	function validate() {

		if (login.id.value == "") {
			alert("아이디를 입력해 주세요");
			login.id.focus();
			return false;
		}

		if (login.pw.value == "") { // 비밀번호 확인 입력하지 않을때,
			alert("비밀번호를 입력해주세요.");
			login.pw.value = "";
			login.pw.focus();
			return false;
		}

	}
</script>
</head>

<body onload='rotate()'>

	<section id="content">
		<nav>
			로그인<br /> <img class="bar"
				src="/StockInsight/asset/body_titleUnderBar.jpg"
				style="padding-top: 20px; width: 121px; height: 10px; float: center;">

			<br />
			<div style="font-size: 20px; margin-top: 10px;">
				<b class="welcome">스톡인사이트 </b>에 오신 것을 환영합니다!
			</div>
		</nav>

		<div class="inner_login">
			<div class="login_start">
				<form id="login" onsubmit="return validate();" method="POST"
					action="/StockInsight/searchLogin">
					<fieldset>
						<div class="box_login">
							<div class="inp_text">
								<label for="loginId" class="login_show">아이디</label> <input
									type="text" id="id" name="user_id" placeholder="ID">
							</div>
							<div class="inp_text">
								<label for="loginPw" class="login_show">비밀번호</label> <input
									type="password" id="pw" name="user_pwd" placeholder="Password">
							</div>
						</div>
						<input type="submit" class="btn_login" value="로그인"
							onclick="showPopup();">
					</fieldset>
				</form>
				<fieldset class="login_under">
					<a href="/StockInsight/jsp/log_in/findId.jsp">ID 찾기 </a> / <a
						href="/StockInsight/jsp/log_in/findPwd.jsp">PASSWORD 찾기</a> <a
						href="/StockInsight/jsp/log_in/join.jsp" class="join">회원가입</a>

				</fieldset>

			</div>
		</div>
	</section>

	<jsp:include page="/jsp/footer.jsp" />

</body>

</html>