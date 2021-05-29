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
			검색 결과<br /> <img class="bar" src="/StockInsight/asset/body_titleUnderBar.jpg"
				style="padding-top: 20px; width: 121px; height: 10px; float: center;">

		</nav>

		<div class="menu_content">
			<center>				
					<form id="stock" method="POST" action="searchCompany">
						<input type="text" name="search" class="search_inner"
							placeholder="검색어 입력">
						<button type="submit" style="width: auto; height: 27pt;"
							class="search_button">&nbsp;검색&nbsp;</button>
					</form>
					<br /> <br />
				<%
					String search = (String) request.getAttribute("search");
					int count = (int) request.getAttribute("count");
					ArrayList<String> result = (ArrayList<String>) request.getAttribute("searchResult");
					String click = (String)request.getAttribute("click");
					System.out.print(click);
						
				%>

				<div class="result_count">
					<strong style="font-size: 20px;">"${search}"</strong>에 대한 검색결과 
					<strong style="font-size: 20px;">총 ${count} 건</strong> 입니다.
				</div>
				
				<%if(count!=0){ %>
				<form method="POST" action="searchCompany">
					
					<input type = "hidden" name = "search" value="${search}"/>
					<%	
						 if(click.equals("btnVolumeLow")){
							 out.print("<button type = \"submit\" name = \"btnUpDown\" value=\"btnVolumeLow\" class=\"updown\"><b><u>거래량 낮은순</u></b></button>");
						 }else{
							 out.print("<button type = \"submit\" name = \"btnUpDown\" value=\"btnVolumeLow\" class=\"updown\"><b>거래량 낮은순</b></button>");
						 } 
						     
							out.print("|");
						 if(click.equals("btnVolumeHigh")){
							 out.print("<button type = \"submit\" name = \"btnUpDown\" value=\"btnVolumeHigh\" class=\"updown\"><b><u>거래량 높은순</u></b></button>");
						 }else{
							 out.print("<button type = \"submit\" name = \"btnUpDown\" value=\"btnVolumeHigh\" class=\"updown\"><b>거래량 높은순</b></button>");
						 }
						     out.print("|");
						 if(click.equals("btnBeforeLow")){
							 out.print("<button type = \"submit\" name = \"btnUpDown\" value=\"btnBeforeLow\" class=\"updown\"><b><u>실시간 낮은순</u></b></button>");
						 }else{
							 out.print("<button type = \"submit\" name = \"btnUpDown\" value=\"btnBeforeLow\" class=\"updown\"><b>실시간 낮은순</b></button>");
						 }
						     out.print("|");
						 if(click.equals("btnBeforeHigh")){
							 out.print("<button type = \"submit\" name = \"btnUpDown\" value=\"btnBeforeHigh\" class=\"updown\"><b><u>실시간 높은순</u></b></button>");
						 }else{
							 out.print("<button type = \"submit\" name = \"btnUpDown\" value=\"btnBeforeHigh\" class=\"updown\"><b>실시간 높은순</b></button>");
						 }
						     out.print("|");
						 if(click.equals("btnName")){
							 out.print("<button type = \"submit\" name = \"btnUpDown\" value=\"btnName\" class=\"updown\" style=\"text-align:left;\"><b><u>   이름순</u></b></button>");
						 }else{
							 out.print("<button type = \"submit\" name = \"btnUpDown\" value=\"btnName\" class=\"updown\" style=\"text-align:left;\"><b>   이름순</b></button>");
						 }
					%>
				</form>
				</br>
				</br>
		
				<form method="POST" action="getStockDetail">
					<table class="srch" width="50%"
						style="border-top: 1px solid #ccc; border-right: 1px solid #ccc;">
						<%
							if (count != 0) {
								for (int i = 0; i < result.size(); i++) {
						%>
						<tr>
							<th width="20%">
								<%
									String index = Integer.toString(i + 1);
									out.print(index);
								%>
							</th>
							<td width="80%"><input type="submit" name="selectCompany"
								value="<%out.print(result.get(i));%>" /></td>
						</tr>
						<%		}
							}%>
					</table>
				</form>
				<%} else{
					 out.print("<center>");   	   
               	  	out.print("검색어의 철자나 띄어쓰기가 정확한지 확인해주세요 .");
               	  	out.print("</center>");
					}%>
			</center>
		</div>


	</section>
<jsp:include page="/jsp/footer.jsp"/>
</body>
</html>