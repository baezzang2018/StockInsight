<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<%
request.setCharacterEncoding("UTF-8");
response.setCharacterEncoding("UTF-8");
// 로그인 성공 시 주석 풀기
if (session.getAttribute("ID") == null) {
	out.print("<h1> 로그인 후 이용해주세요. </h1>");
	out.print("<script>");
	out.print("alert(\"로그인 후 이용해주세요\"); location.href = \"/StockInsight/jsp/log_in/login.jsp\"; ");
	out.print("</script>");
}
%>
<meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
<title>Stock Insight</title>
<link rel="stylesheet" type="text/css" href="/StockInsight/css/qna.css" />

<jsp:include page="/jsp/header.jsp" flush="false">
	<jsp:param name="currentPage"
		value="${'/StockInsight/getQna?pageIndex=1'}" />
</jsp:include>
	<section id="content">

		<nav>
			답변 하기<br /> 
<img class="bar" src="/StockInsight/asset/body_titleUnderBar.jpg" style="padding-top: 20px; width: 121px; height: 10px; float: center;">
		</nav>

		<div class="menu_content">
			<center>
				<table class="question_content_table">
					<%
					
					
					String title = (String)request.getParameter("title");
					String content = (String)request.getParameter("content");
					String name = (String)request.getParameter("name");
					String number = (String)request.getParameter("number");
					String date = (String)request.getParameter("date");
					
					out.println("<tr class=\"cont\"><th>제목</th><td colspan=\"5\">"+title+"</td></tr>");
                	out.println("<tr class=\"cont\"><th>작성자</th><td colspan=\"5\">"+name+"</td></tr>");
                	out.println("<tr class=\"cont\"><th>작성일</th><td colspan=\"5\">"+date+"</td></tr>");
                	out.println("<tr class=\"cont\"><th>글번호</th><td colspan=\"5\">"+number+"</td></tr>");
                	out.println("<tr class=\"cont\"><th>내용</th><td colspan=\"5\" height=\"300\">"+content+"</td></tr>");
                	
					%>
				</table>
				
				<form method="POST" action="/StockInsight/writeAnswer">
					<table style="padding-bottom: 50px" align=center border=0
						cellpadding=2>
						<tr>
							<td bgcolor=white>
								<table class="questionTable" id="answer">
									<tr>
										<td>제목</td>
										<td><input type="text" class="title" name="title"
											style="width: 100%" required></td>
									</tr>

									<tr>
										<td>내용</td>
										<td><textarea name="content" class="content" cols=150
												rows=30 style="width: 100%" required></textarea></td>
									</tr>


									<tr>
										<td colspan="2" class="button_table"><a onclick="history.back()"
											class="btn_question_list">뒤로가기
												</button>
										</a> <input type="submit" class="btn_question_submit" value="등록하기">
										</td>
									</tr>

								</table> <%
								out.println("<input type=\"hidden\" name=\"number\" value=\""+number+"\">");
								%>
							</td>
						</tr>
					</table>
				</form>
			</center>
		</div>

	</section>

	<jsp:include page="/jsp/footer.jsp" />
</body>
</html>