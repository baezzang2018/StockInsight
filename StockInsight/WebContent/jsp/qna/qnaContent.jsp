<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="model.bean.DTO.QnaDTO"%>
<!DOCTYPE html>
<html>
<head>
<style>
#rank-list a {
	color: #FFF;
	text-decoration: none;
}

#rank-list a:hover {
	text-decoration: underline;
}

#rank-list {
	overflow: hidden;
	width: 160px;
	height: 20px;
	margin: 0;
}

#rank-list dt {
	display: none;
}

#rank-list dd {
	position: relative;
	margin: 0;
}

#rank-list ol {
	position: absolute;
	top: 0;
	left: 0;
	margin: 0;
	padding: 0;
	list-style-type: none;
}

#rank-list li {
	height: 20px;
	line-height: 20px;
}
</style>

<meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
<title>Stock Insight</title>
<link rel="stylesheet" type="text/css" href="css/beforeStyle.css" />

<jsp:include page="/jsp/header.jsp" flush="false">
	<jsp:param name="currentPage"
		value="${'/StockInsight/getQna?pageIndex=1'}" />
</jsp:include>

<section id="content">

	<nav>
		문의 게시판<br /> <img class="bar" src="bar.jpg"
			style="padding-top: 20px; width: 121px; height: 10px; float: center;">
	</nav>

	<div class="menu_content">

		<center>
		<table class="question_content_table">
			<%
			// name, title, content, date, number
			QnaDTO post = (QnaDTO) request.getAttribute("post");
		
			out.println("<tr class=\"cont\"><th>제목</th><td colspan=\"5\">" + post.getTitle() + "</td></tr>");
			out.println("<tr class=\"cont\"><th>작성자</th><td colspan=\"5\">" + post.getWriter() + "</td></tr>");
			out.println("<tr class=\"cont\"><th>작성일</th><td colspan=\"5\">" + post.getDate() + "</td></tr>");
			out.println("<tr class=\"cont\"><th>글번호</th><td colspan=\"5\">");
			if (post.getIsQuestion()) {
				out.println(post.getIndex());
			} else {
				out.println(post.printQuestionIndex());
			}
			out.println("</td></tr>");
			out.println("<tr class=\"cont\"><th>내용</th><td colspan=\"5\" height=\"500\">" + post.getContent() + "</td></tr>");
			%>
		
			<tr class="button_table_content">
			<th></th>
			<td colspan="5" height="50">
		
			<form method="POST" action="jsp/qna/writeAnswer.jsp" style="padding-top: 10px; padding-bottom: 10px;">
				<% if((Boolean)request.getAttribute("admin") && post.getIsQuestion()){
			     	out.println("<button type=\"submit\" class=\"btn_answer\">답변하기</button>");
			    }%>
				<a href="/StockInsight/getQnaList?pageIndex=1" class="btn_list">목록가기</a>
				<% //질문info
	            out.println("<input type=\"hidden\" name=\"title\" value=\""+post.getTitle()+"\">");
	            out.println("<input type=\"hidden\" name=\"name\" value=\""+post.getWriter()+"\">");
	            out.println("<input type=\"hidden\" name=\"date\" value=\""+post.getDate()+"\">");
	            out.println("<input type=\"hidden\" name=\"content\" value=\""+post.getContent()+"\">");
	            out.println("<input type=\"hidden\" name=\"number\" value=\""+post.getIndex()+"\">");
	            %>
			</form> 
			<%
	        if(!post.getIsQuestion()&&session.getAttribute("ID")!=null&&session.getAttribute("ID").equals("admin")){
	        	// 답변글을 보고 있는 관리자라면
	        	out.println("<form method='POST' action='deleteAnswer'>");
	        	out.println("<button type=\"submit\" style=\"font-weight: bold; width: 85px; height: 29px; border-radius: 3px; font-family: 'nanum'; font-size: 12px;  color: #000;background:#fff;\">삭제하기</button>");
	        	out.println("<input type='hidden' name='number' value='"+post.getIndex()+"'/>");
	        	out.println("</form>");      		
	        }else if(session.getAttribute("ID")!=null&&session.getAttribute("ID").equals(post.getWriter())){
	        	// 자신의 글을 보고 있다면
	         	out.println("<form method='POST' action='deleteQuestion'>");
	         	out.println("<button type=\"submit\" style=\"font-weight: bold; width: 85px; height: 29px; border-radius: 3px; font-family: 'nanum'; font-size: 12px;  color: #000;background:#fff;\">삭제하기</button>");
	        	out.println("<input type='hidden' name='number' value='"+post.getIndex()+"'/>");
	        	out.println("</form>");
	         }
	         %>
				</td>
			</tr>
		</table>
		</center>
	</div>

</section>

<jsp:include page="/jsp/footer.jsp" />
</body>
</html>