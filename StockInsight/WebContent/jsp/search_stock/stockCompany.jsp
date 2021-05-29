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
       <jsp:param name="currentPage" value="${'/StockInsight/getStockCompany'}" />
</jsp:include>
	
</head>
<body>
<section id="content">
		<nav>
			<%
            ArrayList<String> fieldList = (ArrayList<String>) request.getAttribute("searchFieldList");
            ArrayList<String> companyList = (ArrayList<String>) request.getAttribute("searchCompanyList");
         %>

			종목조회<br /> <img class="bar" src="/StockInsight/asset/body_titleUnderBar.jpg"
				style="padding-top: 20px; width: 121px; height: 10px; float: center;">

			<br />
		</nav>

		<div class="inner_search">
			<fieldset>
				<center>
					<form id="stock" method="POST" action="searchCompany">
						<input type="text" name="search" class="search_inner"
							placeholder="검색어 입력">
						<button type="submit" style="width: auto; height: 27pt;"
							class="search_button" onclick="location.href='search_after.jsp'">&nbsp;검색&nbsp;</button>
					</form>
					<br /> <br /> <br />
				</center>
				<br /> <br />
				<div class="inn">
				  
					<h1 class="inn h1"style="margin-left:15%">분야</h1> 
					<h1 class="inn h1"style="margin-left:49%">회사</h1>
				  
				</div>
				<br /> <br /> <br />

				<div class="interest">

					<ul class="interest ul"
						style="border: 5px solid #4568DC; width: 360px; height: 510px; float: left">
						<form method="POST" action="getStockCompany">
							<%
							  		String selectField = (String) request.getAttribute("field"); // 받아온 stock_index 
                                    if (fieldList != null) {
                                    for (int i = 0; i < fieldList.size(); i++) {
                                 %>
							<% 
	                                 if(selectField.equals(fieldList.get(i))){
	                                	 out.print("<button type = \"submit\" name= \"field\" style=\"height: 40px; width: 340px; background: linear-gradient(to right, #B06AB3, #4568DC); border: none; outline: none;\" value = \"");   
										 out.print(fieldList.get(i));
		                                 out.print("\">");
		                                 out.print("<li>");
	                                	 out.print("<a style = \"color:white;\">");
	                                	 out.print(fieldList.get(i));
	                                	 out.print("</a>");
	                                 }else{
	                                	 out.print("<button type = \"submit\" class=\"interbtn\" name= \"field\" style=\"height: 40px; width: 340px;\" value = \"");   
										 out.print(fieldList.get(i));
		                                 out.print("\">");
		                                 out.print("<li>");
	                                	 out.print("<a class=\"interbtn\" >");
	                                	 out.print(fieldList.get(i));        
	                                	 out.print("</a>");
	                                	 out.print("</li>");
		                                 out.print("</button>");   
	                                 } 
                                 %>
							<%
                                    }
                                 }
                                 %>
						</form>
					</ul>

					<ul class="interest ul"
						style="border: 5px solid #B06AB3; width: 360px; height: 510px;">
						<form method="POST" action="getStockDetail">
							<%
                                       if (companyList != null) {
                                       for (int i = 0; i < companyList.size(); i++) {
                                    %>
							<%
	                                 out.print("<button type = \"submit\" class=\"interbtn\" name= \"selectCompany\" style=\"height: 40px; width: 340px;\" value = \"");
	                                 out.print(companyList.get(i));
	                                 out.print("\">");
	                                 out.print("<li>");
	                                 out.print("<a class=\"interbtn\">");
	                                 out.print(companyList.get(i));
	                                 out.print("</a>");
	                                 out.print("</li>");
	                                 out.print("</button>");   
                                    %>
							<%
                                       }
                                    }
                                    %>
						</form>
					</ul>

				</div>
		</div>
		<br /> <br /> <br /> <br /> <br /> <br />
		</fieldset>



		</div>
		</div>
	</section>
	<jsp:include page="/jsp/footer.jsp"/>
</body>
</html>