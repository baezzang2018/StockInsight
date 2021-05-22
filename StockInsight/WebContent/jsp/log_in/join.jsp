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
		value="${'/StockInsight/searchLogin'}" />
</jsp:include>
<script>
   function validate() {
      var re = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{4,12}$/;
      // 아이디와 패스워드가 적합한지 검사할 정규식
    	  
      var re2 = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
      // 이메일이 적합한지 검사할 정규식

      var email = document.getElementById("email");


      if (!check(re2, email, "적합하지 않은 이메일 형식입니다.")) {
         return false;
      }

 <!--  if (!check(re, pw, "패스워드는 4~12자 사이의 영문 대소문자와 숫자를 포함해야 합니다.")) {
         return false;
      }-->

      if (join.checkpw.value == "") { // 비밀번호 확인 입력하지 않을때,
         alert("비밀번호를 확인란을 입력해주세요.");
         join.checkpw.value = "";
         join.checkpw.focus();
         return false;
      }

      if (join.pw.value != join.checkpw.value) {
         alert("비밀번호가 다릅니다. 다시 확인해 주세요.");
         join.checkpw.value = "";
         join.checkpw.focus();
         return false;
      }

   }
   function check(re, what, message) {
      if (re.test(what.value)) {
         return true;
      }
      alert(message);
      what.value = "";
      what.focus();
      //return false;
   }
</script>
</head>

<body>
<section id="content">
		<nav>
			회원가입<br />  <img class="bar" src="/StockInsight/asset/body_titleUnderBar.jpg"
				style="padding-top: 20px; width: 121px; height: 10px; float: center;">

			<br />
			<div style="font-size: 20px; margin-top: 10px;">
				<b class="welcome">아래의 정보를 빠짐없이 입력해주시기 바랍니다. </b>
			</div>
		</nav>

		<form name="join" onsubmit="return validate();" action="/StockInsight/saveJoin"
			method="POST">
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
						<td colspan="2" style="color: red;">※ 4-16자의 영문 대소문자와 숫자로만 입력</td>
					</tr>
					<tr>
						<td>비밀번호 확인 :</td>
						<td><input required type="password" id="checkpw"
							maxlength="16"></td>
					</tr>
				</table>
				<br /> <br /> <input type="submit" class="join_btn" value=" 회원가입 ">
				<br/><br/>
				</td>
			</center>
		</form>
	</section>
	
	<jsp:include page="/jsp/footer.jsp"/>
	
</body>
</html>