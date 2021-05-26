<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.sql.SQLException"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
<title>Stock Insight</title>
<link rel="stylesheet" type="text/css" href="/StockInsight/css/main.css" />
<link rel="stylesheet" type="text/css"
	href="/StockInsight/css/myPage.css" />
<jsp:include page="/jsp/header.jsp" flush="false">
	<jsp:param name="currentPage" value="${'/StockInsight/doMyPage'}" />
</jsp:include>
<script type="text/javascript">
	function validation() {
		// 1. 이메일 입력
		if (setMyPage.email.value == "") {
			alert("이메일을 입력해 주세요.");
			email.focus();
			return false;
		}

		// 2. 이메일 유효성 검사 
		if (!/([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/
				.test(setMyPage.email.value)) {
			alter("이메일의 형식을 다시 확인한 후 입력해주세요.");
			email.focus();
			return false;
		}

		// 3. 비밀번호 입력 
		if (setMyPage.pwd.value == "") {
			alert("변경할 비밀번호을 입력해 주세요.");
			pwd.focus();
			return false;
		}

		// 4. 비밀번호 유효성 검사 
		if (!/^[a-zA-Z0-9]{8,20}$/.test(setMyPage.pwd.value)) {
			alert("비밀번호는 숫자와 영문자 조합으로 8~20자리를 사용해야 합니다.");
			pwd.value = "";
			pwd.focus();
			return false;
		}

		// 5. 비밀번호 확인 입력 
		if (setMyPage.pwd_check.value == "") {
			alert("비밀번호 확인란을 입력해 주세요.");
			pwd_check.value = "";
			pwd_check.focus();
			return false;
		}

		// 6. 비밀번호 일치 검사 
		if (pwd_check.value != pwd.value) {
			alert("비밀번호가 일치하지 않습니다. 다시 확인해 주세요.");
			pwd_check.value = "";
			pwd_check.focus();
			return false;
		}

		return true;

	}

	/* function btn_confirm() {
	     var check = confirm("확인/취소 버튼");
	     if(check)
	         alert("확인버튼 클릭");
	     else
	         alert("취소 버튼 클릭");
	 }
	
	 function btn_prompt() {
	     var name = prompt("이름은?","Park");
	     if(name)
	         alert("이름은 "+name+" 입니다.")
	     else
	         alert("질문창 취소")
	 } */

	function validadtion() {
		// 1. 이메일 입력
		if (setMyPage.email.value == "") {
			alert("이메일을 입력해 주세요.");
			email.focus();
			return false;
		}

		// 2. 이메일 유효성 검사 
		if (!/([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/
				.test(setMyPage.email.value)) {
			alter("이메일의 형식을 다시 확인한 후 입력해주세요.");
			email.focus();
			return false;
		}

		// 3. 비밀번호 입력 
		if (setMyPage.pwd.value == "") {
			alert("변경할 비밀번호을 입력해 주세요.");
			pwd.focus();
			return false;
		}

		// 4. 비밀번호 유효성 검사 /^[a-zA-Z0-9]+$/
		var pattern1 = /[0-9]/; // 숫자 
		var pattern2 = /[a-zA-Z]/; // 문자 
		var pattern3 = /[~!@#$%^&*()_+|<>?:{}]/; // 특수문자

		if (!pattern1.test(setMyPage.pwd.value)
				|| !pattern2.test(setMyPage.pwd.value)
				|| !pattern3.test(setMyPage.pwd.value)
				|| setMyPage.pwd.value.length < 8) {
			alert("비밀번호는 숫자와 영문자, 특수문자 조합으로 8자리 이상 구성하여야 합니다.");
			return false;

		}

		// 5. 비밀번호 확인 입력 
		if (setMyPage.pwd_check.value == "") {
			alert("비밀번호 확인란을 입력해 주세요.");
			pwd_check.value = "";
			pwd_check.focus();
			return false;
		}

		// 6. 비밀번호 일치 검사 
		if (pwd_check.value != pwd.value) {
			alert("비밀번호가 일치하지 않습니다. 다시 확인해 주세요.");
			pwd_check.value = "";
			pwd_check.focus();
			return false;
		}

		return true;
	}
</script>
</head>
<body>
	<%-- <% 세션 체
if (session.getAttribute("ID") == null) {
   out.print("<h1> 로그인 후 이용해주세요. </h1>");
   out.print("<script>");
   out.print("alert(\"로그인 후 이용해주세요\"); location.href = \"login.jsp\"; ");
   out.print("</script>");
}
%> --%>
	<section id="content">
		<nav>
			마이페이지 <br /> <img class="bar"
				src="/StockInsight/asset/body_titleUnderBar.jpg"
				style="padding-top: 20px; width: 121px; height: 10px; float: center;">
		</nav>
		<div class="menu_content">
			변경하고 싶은 정보를 수정한 후<br> 수정완료 버튼을 클릭하세요<br>
			<br>
			<br>
			<p style="color: red;">(단, 아이디와 이름은 변경이 불가합니다.)</p>
			<br> <br> <br>
			<form name='aa' id='b' method="post" action="cs">
				<input type="button" value="버튼" onclick="javascript:alert('버튼클릭~')"
					name="btn_alert"><br /> <input type="button" value="알림창"
					onclick="btn_alert();" name="btn_confirm" /><br /> <input
					type="button" value="확인창" onclick="btn_confirm();"
					name="btn_confirm" /><br /> <input type="button" value="플롬프트창"
					onclick="btn_prompt();" name="btn_prompt" /><br />
			</form>
			<center>
				<form name="setMyPage" onsubmit="return validation();" method="POST"
					action="setUserData">
					<table class="setMyPage">
						<%
						String name = (String) request.getAttribute("name");
						String id = (String) request.getAttribute("id");
						String email = (String) request.getAttribute("email");
						String pwd = (String) request.getAttribute("pwd");

						out.println(
								"<tr><td align=right><font size=\"5\"><b>이름</b></font></td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td><font size=\"4\">"
								+ name + "</font></td></tr>");
						out.println(
								"<tr><td align=right><font size=\"5\"><b>아이디</b></font></td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td><font size=\"4\">"
								+ id + "</font></td></tr>");
						// 변경가능
						out.println(
								"<tr><td align=right><font size=\"5\"><b>이메일</b></font></td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td><input type=\"text\" name = \"email\" id = \"email\" size=23 font size=\"4\" value="
								+ email + "></font></td></tr>");
						out.println(
								"<tr><td align=right><font size=\"5\"><b>비밀번호</b></font></td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td><input type=\"password\" name = \"pwd\" id = \"pwd\" size=23 font size=\"4\" placeholder=\"변경할 비밀번호를 입력하세요\">"
								+ "</font></td></tr>");
						out.println(
								"<tr><td align=right><font size=\"5\"><b>비밀번호 확인</b></font></td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td><input type=\"password\" name = \"pwd_check\" id = \"pwd_check\" size=23 font size=\"4\" placeholder=\"비밀번호를 한번 더 입력하세요\">"
								+ "</font></td></tr>");
						%>
					</table>
					<br> <br> <br> <input type="submit" class="btn_set"
						value="수정완료" />
				</form>
		</div>
		<br> <br> <br>
	</section>
	<jsp:include page="/jsp/footer.jsp" />
</body>
</html>