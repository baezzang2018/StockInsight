<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<%
// 로그인 성공 시 주석 풀기
/*
if (session.getAttribute("ID") == null) {
	out.print("<h1> 로그인 후 이용해주세요. </h1>");
	out.print("<script>");
	out.print("alert(\"로그인 후 이용해주세요\"); location.href = \"login.jsp\"; ");
	out.print("</script>");
}*/
%>
<link rel="stylesheet" type="text/css" href="css/beforeStyle.css" />

<jsp:include page="/jsp/header.jsp" flush="false">
	<jsp:param name="currentPage"
		value="${'/StockInsight/getQna?pageIndex=1'}" />
</jsp:include>

          <section id="content" >
           
            <nav>
                문의하기<br/>
                <img class="bar" src="bar.jpg" style="padding-top:20px; width:121px; height : 10px; float:center;">
            </nav>

            <div class="question_content">
              <center>
              <form method = "POST" action="writeQuestion">
                <table  style="padding-bottom:50px" align = center width=700 border=0 cellpadding=2 >
                        <tr>
                          <td bgcolor=white>
                          <table class = "questionTable">
                                  <tr>
                                  <td>제목</td>
                                  <td><input type = "text" class="title"  name ="title" size=86.5 required></td>
                                  </tr>
          
                                  <tr>
                                  <td>내용</td>
                                  <td><textarea name ="content" class="content" cols=85 rows=30 required></textarea></td>
                                  </tr>

                                  <tr>
                                    <td colspan="2" class="button_table">
                                      <a href="/StockInsight/getQnaList?pageIndex=1" class="btn_question_list">목록보기</button></a>
                                      <input type = "submit" class="btn_question_submit" value="등록하기">
                                    </td>
                                  </tr>

                          </table>                      
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