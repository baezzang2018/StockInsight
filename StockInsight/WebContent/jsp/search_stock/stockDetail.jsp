<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<script src="./js/myAjax.js"></script>
<meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
<title>Stock Insight</title>
<link rel="stylesheet" type="text/css" href="/StockInsight/css/stock.css" />
<link rel="stylesheet" type="text/css" href="/StockInsight/css/main.css" />
<jsp:include page="/jsp/header.jsp" flush="false">
       <jsp:param name="currentPage" value="${'/StockInsight/getStockCompany'}" />
</jsp:include>
</head>
<body>
<section id="content">
      <nav>
         <%
            String selectField = (String) request.getAttribute("selectField"); //분야
            String selectCompany = (String) request.getAttribute("selectCompany"); //회사
            String selectFuture = (String) request.getAttribute("selectFuture"); // 예측가격
         %>
         예측결과<br /> <img class="bar" src="/StockInsight/asset/body_titleUnderBar.jpg"
				style="padding-top: 20px; width: 121px; height: 10px; float: center;">
      </nav>
      <div class="final_content">
         <div class="left_final">

            <p style="font-size: 30px; color: black;">
               <b>${selectField}</b>분야 :
            </p>
            <!-- yh 사용 -->
            <p style="font-size: 40px; color: black; padding-left: 30px;">
               <b>${selectCompany}</b>
               <%
                  if (session.getAttribute("ID") != null) { //세션 존재 
                  String user_id = (String) session.getAttribute("ID"); // 세션에 저장된 user_id 
                  String user_index = (String) request.getAttribute("user_index"); // 받아온 user_index 

                  String stock_index = (String) request.getAttribute("stock_index"); // 받아온 stock_index 
                  Boolean interestCheck = (Boolean) request.getAttribute("interCheck"); //관심 종목에 들어가있는지 유무 
               %>
               <%
                  if (interestCheck == false) {//선택한 회사가 관심종목에 없을 때, x
                  out.print("<form method = \"POST\" action=\"updateInterest\">"); //관심종목에 현재 로그인한 user_index에 선택한 분야가 관심종목에 있을때 
                  out.print(
                  "<button type = \"submit\" name= \"user_id\" style=\" border : none; margin-left:20px;\" onClick=\"alert('관심종목에 담겼습니다.')\" value = \"");
                  out.print(user_id);
                  out.print("\">");
                  out.print("<input type = \"hidden\" name = \"selectCompany\" value = \"");
                  out.print(selectCompany);
                  out.print("\"/>");
                  out.print("<img src=\"/StockInsight/asset/empty_heart.png\" style=\"width: 30px; height: auto; background: white;  \">");
                  out.print("</button>");
                  out.print("</p>");

                  out.print("<input type = \"hidden\" name = \"user_index\" value = \"");
                  out.print(user_index);
                  out.print("\"/>");

                  out.print("<input type = \"hidden\" name = \"stock_index\" value = \"");
                  out.print(stock_index);
                  out.print("\"/>");

                  out.print("<input type = \"hidden\" name = \"selectField\" value = \"");
                  out.print(selectField);
                  out.print("\"/>");
                  out.print("</form>");
               } else { //선택한 회사가 관심종목에 있을 때, o 
                  out.print("<form method = \"POST\" action=\"deleteInterest\">");
                  out.print(
                  "<button type = \"submit\" name= \"user_id\" style=\" border : none; margin-left:20px;\" onClick=\"alert('관심종목이 취소되었습니다.')\" value = \"");
                  out.print(user_id);
                  out.print("\">");
                  out.print("<input type = \"hidden\" name = \"selectCompany\" style=\" float : right;\" value = \"");
                  out.print(selectCompany);
                  out.print("\"/>");
                  out.print("<img src=\"/StockInsight/asset/heart.png\" style=\"width: 30px; height: auto; background: white; float : right;\">");
                  out.print("</button>");
                  out.print("</p>");

                  out.print("<input type = \"hidden\" name = \"user_index\" value = \"");
                  out.print(user_index);
                  out.print("\"/>");

                  out.print("<input type = \"hidden\" name = \"stock_index\" value = \"");
                  out.print(stock_index);
                  out.print("\"/>");

                  out.print("<input type = \"hidden\" name = \"selectField\" value = \"");
                  out.print(selectField);
                  out.print("\"/>");
                  out.print("</form>");
               }
               %>
               <%
                  } else {
               // 세션존재하지 않음
               %>
               <button style="border: none; margin-left: 20px;"
                  onClick="alert('로그인이 필요한 서비스입니다.');">
                  <img src="/StockInsight/asset/empty_heart.png"
                     style="width: 30px; height: auto; background: white;">
               </button>
            </p>
            <%
               }
            %>
            <div class="realtime_price" id="stock_pre"></div>
            <div class="price">
               내일 예측 가격은<br> <b
                  style="font-size: 30px; background: linear-gradient(to right, #B06AB3, #4568DC); -webkit-background-clip: text; -webkit-text-fill-color: transparent;">${selectFuture}원</b>
               입니다.
            </div>
            <% %>
            <div style="margin-top:10%">
            	<a href="https://search.naver.com/search.naver?where=news&sm=tab_jum&query=<%=selectCompany%>" 
            	target="_blank" style="text-decoration: underline; color: gray; ">
            	<b style="font-size: 20px;"> 📢 관련 이슈가 궁금하신가요?</b></a>
            </div>
         </div>

         <div id="right_final"
            style="border: 1px solid black; float: left; margin-left: 5%; width: 35%; height: 450px;">
            <!-- chat -->
            실시간 차트
         </div>

      </div>
   </section>
   <jsp:include page="/jsp/footer.jsp"/>
</body>
</html>