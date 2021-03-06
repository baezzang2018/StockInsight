<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="model.bean.DTO.QnaDTO,java.util.ArrayList"%>

<html>
<head>

<meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
<title>Stock Insight</title>

<link rel="stylesheet" type="text/css" href="css/qna.css" />

<jsp:include page="/jsp/header.jsp" flush="false">
	<jsp:param name="currentPage" value="${'/StockInsight/getQnaList?pageIndex=1'}" />
</jsp:include>

</head>


<body onload='rotate()'>

	<section id="content">

		<nav>
			문의 게시판<br /> <img class="bar" src="/StockInsight/asset/body_titleUnderBar.jpg"
            style="padding-top: 20px; width: 121px; height: 10px; float: center;">
		</nav>

		<div class="menu_content">

			<a href="jsp/qna/writeQuestion.jsp" class="btn_question">글 작성</a>

			<center>
				<table class="qna_table">
					<thead>
						<tr>
							<th>번호</th>
							<th>제목</th>
							<th>작성자</th>
							<th>작성일</th>
						</tr>
					</thead>
					<tbody>

				<%
                  // 출력할 post list
                  	ArrayList<QnaDTO> postList = (ArrayList<QnaDTO>)request.getAttribute("postList");
                  // 현재 page
                  String pageIndexStr = "1";
                  int pageindex = 1;
                  	try{
                  		pageIndexStr = (String)request.getParameter("pageIndex");
                  		pageindex = Integer.parseInt(pageIndexStr);
                  	}catch(Exception e){
                  		pageIndexStr = "1";
                  		pageindex = 1;
                  	}          
                  	
                  	for(int i=(15*(pageindex-1));i<postList.size()&&i<(15*(pageindex-1)+15);i++){
                  		if(postList.get(i).getIsQuestion()){ //질문= 질문글인덱스
                  			out.println("<tr><td>"+postList.get(i).getIndex()+"</td>");
                  		}
                  		else{ //답글 = >[re]질문글인덱스
                  			out.println("<tr class=\"reply\"><td>"+postList.get(i).printQuestionIndex()+"</td>");	
                  		}
                  		
                  		// url?type=질문글&index=인덱스
                  		String value = "type=";
                  		if(postList.get(i).getIsQuestion()){ //질문글인덱스
                  			value = value + "question" + "&index=" + postList.get(i).getIndex();
                  		}else{ //답변글인덱스
                  			value = value + "answer" + "&index="+ postList.get(i).getIndex();
                  		}
                  		
                  		//글제목 click 시 해당 글 조회
                  		out.print("<td><a href=\"/StockInsight/getPost?"+value+"\">"+postList.get(i).getTitle()+"</a></td>");
                  		out.print("<td>"+postList.get(i).getWriterName()+"</td>");
                  		out.print("<td>"+postList.get(i).getDate()+"</td>");
                  	}
                  %>

					</tbody>
				</table>

				<table class="page">
					<tr>
						<%
                  	if (pageindex > 1) {
                  	out.println("<td><a href=\"/StockInsight/getQna?pageIndex=" + (pageindex - 1) + "\" class=\"pageIndex\"> &lt; </a></td>");
                  }
                  if ((postList.size() / 15 + 1) > pageindex) {
                  	out.println("<td><a href=\"/StockInsight/getQna?pageIndex=" + (pageindex + 1) + "\" class=\"pageIndex\"> &gt; </a></td>");
                  }
                  %>
					</tr>
				</table>

			</center>
		</div>

	</section>

	<jsp:include page="/jsp/footer.jsp"/>

</body>
</html>