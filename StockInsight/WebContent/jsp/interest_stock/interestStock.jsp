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
<jsp:include page="/jsp/header.jsp" flush="false">
       <jsp:param name="currentPage" value="${'/StockInsight/getInterest'}" />
</jsp:include>

</head>
<body>
	<section id="content">

		<nav>
         <%
            ArrayList<String> findStockFieldList = (ArrayList<String>) request.getAttribute("findStockFieldFromStockIndex");
            ArrayList<String> companyList = (ArrayList<String>) request.getAttribute("searchCompanyList");
            ArrayList<String> stockindexList = (ArrayList<String>) request.getAttribute("findStockIndexFromUser");
            ArrayList<String> beforeList = (ArrayList<String>) request.getAttribute("findBeforeList");
            ArrayList<String> futureList = (ArrayList<String>) request.getAttribute("findFutureList");
          %>
         관심종목<br />  <img class="bar" src="/StockInsight/asset/body_titleUnderBar.jpg"
				style="padding-top: 20px; width: 121px; height: 10px; float: center;">
      </nav>
<div class="interst_stock">
   <div >                   
   <fieldset>
      <%
       //if(session.getAttribute("ID")!=null){ //세션 존재 
        
    	%>
       
       <br/>
       
       <h1 style="text-align:center;">예측 가격 <b style="color:#E84620;"> 상승</b> 종목은 <b style="color:#E84620;"> 빨간색</b>, <b style="color:#0066FF;"> 하락</b> 종목은 <b style="color:#0066FF;"> 파란색</b>으로 표시됩니다. </h1><br/><br/><br/>
       
         	
           <form method="POST" action="getStockDetail">                       
               <div class = "alllike">
               <%
                  if (stockindexList != null) {
                  for (int i = 0; i < stockindexList.size(); i++) {
                	  
               	  out.print("<div class = \"like\">");
               	  out.print("<button type = \"submit\" class=\"likebtn\" name= \"selectCompany\"  value = \"");
               	  out.print(companyList.get(i));
               	  out.print("\">"); 
               
               	  out.print("<img src=\"heart.png\" style=\"width: 30px; height: auto; \">");
               	  out.print("<br/>");
               	  out.print("<br/>");
               
               	  int gap = Integer.parseInt(beforeList.get(i))-Integer.parseInt(futureList.get(i));
               	  boolean up = true;
               	  if(gap < 0)
               		  up = true;
               	  else
               		  up = false;	  
               %>
               <h1 style="font-size: 17pt;"><b><%= companyList.get(i)%></b></h1>
               <br/>
               <h1 style="font-size: 13pt;"><%= findStockFieldList.get(i)%></h1>
               <br/>
               <p class = "today" ><b>실시간 가격 : <%= beforeList.get(i)%>원</b></p>  
               <%
               	  if (up == true){%>
               		<p class = "today"><b>내일 예측 가격 : <b style="color:#E84620;"><%= futureList.get(i)%>원</b></b></p>
               <% }
               
               	  else { %>
               		<p class = "today"><b>내일 예측 가격 : <b style="color:#0066FF;"><%= futureList.get(i)%>원</b></b></p>
               	 <% }  %>
               </button>
               </div>
               <%
                  }
                  
               }
                  else{
                	  out.print("<center>");
                	  out.print("<img src=\"/StockInsight/asset/empty_heart.png\" style=\"width: 30px; height: auto; background: white;\">");
                	  out.print("<br/>");
                	  out.print("<br/>");
                	  out.print("<h1>");
                	  out.print("관심종목이 없습니다.");
                	  out.print("</h1>");
                	  out.print("<br/>");
                	  out.print("<h3>");
                	  out.print("관심 표시 된 종목들은 한 번에 확인할 수 있어요!");
                	  out.print("</h3>");
                	  out.print("</center>");
                	 
                  }
               %>
              </div>
            </form>
      <% //} 
      // else { // 세션존재하지 않음
       %><center>
                 <% 
                  //out.print("<h1> 로그인 후 이용해주세요. </h1>");
                  //out.print("<script>");
                  //out.print("alert(\"로그인 후 이용해주세요\"); location.href = \"login.jsp\"; ");
                  //out.print("</script>");
                  %>
         </center>
    <%// } %></div>
   </fieldset>
</div></div>
<br /><br/><br /><br /><br/><br />

   </section>

	<jsp:include page="/jsp/footer.jsp"/>
</body>
</html>