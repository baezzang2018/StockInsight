<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
			<center>
				<br> <br> <br>
				<table class="mypage_inner">
					<%
					// existing user
					out.println(
							"<tr><td align=right><font size=\"5\"><b>이름</b></font></td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td><font size=\"4\">"
							+ (String) request.getAttribute("name") + "</font></td></tr>");
					out.println(
							"<tr><td align=right><font size=\"5\"><b>아이디</b></font></td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td><font size=\"4\">"
							+ (String) request.getAttribute("id") + "</font></td></tr>");
					out.println(
							"<tr><td align=right><font size=\"5\"><b>이메일</b></font></td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td><font size=\"4\">"
							+ (String) request.getAttribute("email") + "</font></td></tr>");
					out.println(
							"<tr><td align=right><font size=\"5\"><b>패스워드</b></font></td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td><font size=\"4\">"
							+ (String) request.getAttribute("pwd") + "</font></td></tr>");
					%>
				</table>
				<br> <br> <br>
			</center>
			<div>
				<form id="getMyPage" action="getMyQna" style="display: inline">
					<button type="button" class="btn_collection">내가 쓴 문의글</button>
				</form>
				<button type="button" class="btn_set"
					onClick="location.href='setMyPage' ">수정하기</button>
				<button type="button" class="btn_delete"
					onClick="location.href='/Stock_Insigh/removeId' ">탈퇴하기</button><br /><br /><br /><br /><br />
			</div>
		</div>
	</section>
	<jsp:include page="/jsp/footer.jsp" />
</body>
</html>