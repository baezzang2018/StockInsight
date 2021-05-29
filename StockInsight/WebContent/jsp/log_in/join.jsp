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
	function join_check() {
		var pwd_valid = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,12}$/;
		var email_valid = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;

		//var re = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{4,12}$/;
		// 아이디와 패스워드가 적합한지 검사할 정규식

		//var re2 = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
		// 이메일이 적합한지 검사할 정규식

		var email = document.getElementById("email");
		var pwd = document.getElementById("pw");
		var checkpwd = document.getElementById("checkpw");

		if (!email_valid.test(email.value)) {
			alert("이메일 형식이 알맞지 않습니다.");
			email.focus();
			return false;
		}

		if (!pwd_valid.test(pwd.value)) { // 비밀번호가 정규화식에 알맞지 않으면
			alert("비밀번호는 영문자+숫자+특수문자 조합으로 8~12자리 사용해야 합니다.");
			pwd.focus();
			return false;
		}

		if (pwd.value !== checkpwd.value) { // 비밀번호랑 비밀번호 확인이 알맞지 않으면
			alert("비밀번호가 일치하지 않습니다..");
			checkpwd.focus();
			return false;
		}

		document.join.submit(); //유효성 검사의 포인트
	}
</script>
</head>

<body onload='rotate()'>
	<section id="content">
		<nav>
			회원가입<br /> <img class="bar"
				src="/StockInsight/asset/body_titleUnderBar.jpg"
				style="padding-top: 20px; width: 121px; height: 10px; float: center;">

			<br />
			<div style="font-size: 20px; margin-top: 10px;">
				<b class="welcome">아래의 정보를 빠짐없이 입력해주시기 바랍니다. </b>
			</div>
		</nav>

		<form name="join" action="/StockInsight/saveJoin" method="POST">
			<center>
				<table class="table">
					<br>
					<tr>
						<td>이름 :</td>
						<td><input required type="text" name="user_name" id="name"
							maxlength="12" /></td>
					</tr>
					<tr>
						<td>ID :</td>
						<td><input required type="text" name="user_id" id="id"
							maxlength="20" /></td>

					</tr>
					<tr>
						<td>email :</td>
						<td><input required type="text" name="user_email" id="email"
							maxlength="50" /></td>

					</tr>
					<tr>
						<td></td>
						<td colspan="2" style="color: red;">ex)
							stockinsight@naver.com</td>
					</tr>
					<tr>
						<td>비밀번호 :</td>
						<td><input required type="password" name="user_pwd" id="pw"
							maxlength="16"></td>
					</tr>
					<tr>
						<td></td>
						<td colspan="2" style="color: red;">※ 8-12자의 영문 대소문자 + 숫자 +
							특수문자 조합</td>
					</tr>
					<tr>
						<td>비밀번호 확인 :</td>
						<td><input required type="password" id="checkpw"
							maxlength="16"></td>
					</tr>
				</table>
				<br /> <br /> <input type="button" onclick="join_check();"
					class="join_btn" value=" 회원가입 "> <br />
				<br />
				</td>
			</center>
		</form>
	</section>

	<jsp:include page="/jsp/footer.jsp" />

</body>
</html>