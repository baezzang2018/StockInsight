<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="model.bean.DTO.QnaDTO"%>
<!DOCTYPE html>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
<title>Stock Insight</title>

<link rel="stylesheet" type="text/css" href="css/qna.css" />

<jsp:include page="/jsp/header.jsp" flush="false">
	<jsp:param name="currentPage"
		value="${'/StockInsight/getQnaList?pageIndex=1'}" />
</jsp:include>

</head>

<body>

	<section id="content">

		<nav>
			문의 게시판<br /> <img class="bar"
				src="/StockInsight/asset/body_titleUnderBar.jpg"
				style="padding-top: 20px; width: 121px; height: 10px; float: center;">
		</nav>

		<div class="menu_content">

			<center>
				<table class="question_content_table">
					<tr class="contType">
						<th colspan="6">문의글</th>
					</tr>
					<%
						request.setCharacterEncoding("UTF-8");
					response.setCharacterEncoding("UTF-8");

					// name, title, content, date, number
					QnaDTO post = (QnaDTO) request.getAttribute("post");

					out.println("<tr class=\"cont\"><th>제목</th><td colspan=\"5\">" + post.getTitle() + "</td></tr>");
					out.println("<tr class=\"cont\"><th>작성자</th><td colspan=\"5\">" + post.getWriterName() + "</td></tr>");
					out.println("<tr class=\"cont\"><th>작성일</th><td colspan=\"5\">" + post.getDate() + "</td></tr>");
					out.println("<tr class=\"cont\"><th>글번호</th><td colspan=\"5\">");
					if (post.getIsQuestion()) {
						out.println(post.getIndex());
					} else {
						out.println(post.printQuestionIndex());
					}
					out.println("</td></tr>");
					out.println("<tr class=\"cont\"><th>내용</th><td colspan=\"5\" height=\"300\">" + post.getContent() + "</td></tr>");
					%>

					<tr class="button_table_content">
						<th></th>
						<td colspan="5" height="50">

							<form method="POST" action="jsp/qna/writeAnswer.jsp"
								accept-charset="utf-8"
								style="padding-top: 10px; padding-bottom: 10px;">
								<%
									String replyIndex = (String) request.getAttribute("replyIndex");
								Boolean hasReply = !(replyIndex == null || replyIndex.length() == 0);
								if ((Boolean) request.getAttribute("admin") && post.getIsQuestion() && !hasReply) {
									out.println("<button type=\"submit\" class=\"btn_answer\">답변하기</button>");
								}
								%>
								<a href="/StockInsight/getQnaList?pageIndex=1" class="btn_list">목록가기</a>
								<%
									//질문info
								out.println("<input type=\"hidden\" name=\"title\" value=\"" + post.getTitle() + "\">");
								out.println("<input type=\"hidden\" name=\"name\" value=\"" + post.getWriter() + "\">");
								out.println("<input type=\"hidden\" name=\"date\" value=\"" + post.getDate() + "\">");
								out.println("<input type=\"hidden\" name=\"content\" value=\"" + post.getContent() + "\">");
								out.println("<input type=\"hidden\" name=\"number\" value=\"" + post.getIndex() + "\">");
								%>
							</form> <%
 	if (session.getAttribute("ID") != null && session.getAttribute("ID").equals(post.getWriter())) {
 	// 자신의 글을 보고 있다면
 	out.println("<form method='POST' action='/StockInsight/deleteQuestion' name='deleteQuestion'>");
 	out.println(
 	"<button type=\"submit\"  style=\"font-weight: bold; width: 85px; height: 29px; border-radius: 3px; font-family: 'nanum'; font-size: 12px;  color: #000;background:#fff;\">문의삭제</button>");
 	out.println("<input type='hidden' name='number' value='" + post.getIndex() + "'/>");
 	out.println("</form>");
 }
 %>
						</td>
					</tr>
				</table>

				<%
					if (hasReply) {
				%>
				<table class="question_content_table">
					<tr class="contType">
						<th colspan="6">답변글</th>
					</tr>
					<%
						QnaDTO replyPost = (QnaDTO) request.getAttribute("replyPost");

					String title = replyPost.getTitle();
					String content = replyPost.getContent();
					String name = replyPost.getWriter();
					String realName = replyPost.getWriterName();
					String number = replyPost.getIndex();
					String date = replyPost.getDate();

					out.println("<tr class=\"cont\"><th>제목</th><td colspan=\"5\">" + title + "</td></tr>");
					out.println("<tr class=\"cont\"><th>작성자</th><td colspan=\"5\">" + realName + "</td></tr>");
					out.println("<tr class=\"cont\"><th>작성일</th><td colspan=\"5\">" + date + "</td></tr>");
					out.println("<tr class=\"cont\"><th>글번호</th><td colspan=\"5\">" + number + "</td></tr>");
					out.println("<tr class=\"cont\"><th>내용</th><td colspan=\"5\" height=\"300\">" + content + "</td></tr>");
					%>

					<%
						String type = request.getParameter("type");
					if (session.getAttribute("ID") != null && (Boolean) request.getAttribute("admin")) {
						// 답변글을 보고 있는 관리자라면
						out.println("<tr class=\"button_table_content\"><th></th><td colspan=\"4\" height=\"50\"><td>");
						out.println("<form method='POST' action='/StockInsight/deleteAnswer' name='deleteAnswer'>");
						out.println(
						"<button type=\"submit\" style=\"font-weight: bold; width: 85px; height: 29px; border-radius: 3px; font-family: 'nanum'; font-size: 12px;  color: #000;background:#fff;\">답변삭제</button>");
						out.println("<input type='hidden' name='number' value='" + replyPost.getIndex() + "'/> </form>");
						out.println("</td></tr>");
					}
					%>

				</table>
				<%
					}
				%>
			</center>
		</div>

	</section>

	<jsp:include page="/jsp/footer.jsp" />
</body>
</html>