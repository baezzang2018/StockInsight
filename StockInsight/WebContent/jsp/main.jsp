<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script src="./js/myAjax.js"></script>
<meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
<title>Stock Insight</title>
<link rel="stylesheet" type="text/css" href="/StockInsight/css/main.css" />
<jsp:include page="/jsp/header.jsp" flush="false">
       <jsp:param name="currentPage" value="${'/StockInsight/jsp/main.jsp'}" />
</jsp:include>

</head>

<body>
	<!-- 차트 그리기 -->
	<script type="text/javascript"
		src="https://www.gstatic.com/charts/loader.js"></script>

	<script type="text/javascript">
		//Google Stuff
		google.charts.load('current', {
			packages : [ 'corechart' ]
		});
		google.charts.setOnLoadCallback(function() {
			repeatChart();
			loadPresentPrice();
		});
	</script>

	<script>
		var kospi = "000000";
		var kosdaq = "111111";
		// 차트 부분만 reload
		var repeatChart = function() {
			myAjax("/Stock_Insigh/csv", "code=" + kospi, function() {
				ajaxMakeChart_kospi(kospi_chart, this.responseText.trim()); //데이터, 그리기 함수가 들어간 함수
			});
			myAjax("/Stock_Insigh/csv", "code=" + kosdaq, function() {
				ajaxMakeChart_kosdaq(kosdaq_chart, this.responseText.trim()); //데이터, 그리기 함수가 들어간 함수
			});
		}
		// 현재 가격 부분만 reload
		var loadPresentPrice = function() {
			var btn = document.getElementById('btn');
			myAjax("/Stock_Insigh/csv", "code=" + kospi, function() {
				currentData_kospi(kospi_pre_data, this.responseText.trim()); //현재가격 영역
			});
			myAjax("/Stock_Insigh/csv", "code=" + kosdaq, function() {
				currentData_kosdaq(kosdaq_pre_data, this.responseText.trim()); //데이터, 그리기 함수가 들어간 함수
			});
		};
		// 보여지는 순위만 reload
		var loadShowRank = function() {
			var btn = document.getElementById('showRank');
			var table = "stock_volume"
			myAjax("/Stock_Insigh/getRank", "table="+table, function() {
				updateShowRank(btn, this.responseText.trim()); //현재가격 영역
			});
		}
		// 숨겨진 순위만 reload
		var loadHiddenRank = function() {
			var btn = document.getElementById('hiddenRank');
			var table = "stock_volume"
			myAjax("/Stock_Insigh/getRank", "table="+table, function() {
				updateHiddenRank(btn, this.responseText.trim()); //현재가격 영역
			});
		}
		
		// 보여지는 순위 그리는 코드
		function updateShowRank(element, txtData) {
			var strTag = "";
			row = txtData.split("@"); // 회사명@회사명@...@회사명        
			for (var rowIndex = 1; rowIndex <= row.length; rowIndex++) {
				var companyName = row[rowIndex-1];
				strTag += "<li><a href='javascript:showplay();'>"+rowIndex + "위. "+companyName+"</a></li>";
			};
			element.innerHTML = strTag;
		};
		
		// 숨긴 순위 그리는 코드
		function updateHiddenRank(element, txtData) {
			var strTag = "";
			row = txtData.split("@"); // 회사명@회사명@...         
			for (var rowIndex = 1; rowIndex <= row.length; rowIndex++) {
				var companyName = row[rowIndex-1];
				strTag += "<a href='"+"/Stock_Insigh/getRankInfo?companyName="+companyName+"'>"+rowIndex + "위. "+companyName+"</a></br>";
			};
			element.innerHTML = strTag;
		};
		
		
		// 차트 그리기
		function ajaxMakeChart_kospi(element, txtData) {
			row = txtData.split("@"); // row1@row2@...         
			var one_row;
			var all_row = [];
			// 한 tr이 한 row
			for (var rowIndex = 0; rowIndex < row.length - 1; rowIndex++) {
				col = row[rowIndex].split("|"); // row의 각 col들 (name=value|name=value)            
				//one_row 초기화
				one_row = [];
				// date
				dateNameValue = col[0].split("=");
				one_row.push(dateNameValue[1]);
				// price
				priceNameValue = col[1].split("=");
				one_row.push(parseInt(priceNameValue[1]));
				// (date, price)
				all_row.push(one_row);
			};
			var data = new google.visualization.DataTable();
			data.addColumn('string', 'date');
			data.addColumn('number', 'price');
			data.addRows(all_row);
			var options = null;
			var options = {
					hAxis : {
						title : 'time',
						textPosition: 'none'
					},
					vAxis : {
						title : 'price'
					}
				
				};
			var chart = new google.visualization.LineChart(document
					.getElementById('kospi_chart'));

			chart.draw(data, options);
		};
		function ajaxMakeChart_kosdaq(element, txtData) {
			row = txtData.split("@"); // row1@row2@...         
			var one_row;
			var all_row = [];
			// 한 tr이 한 row
			for (var rowIndex = 0; rowIndex < row.length - 1; rowIndex++) {
				col = row[rowIndex].split("|"); // row의 각 col들 (name=value|name=value)            
				//one_row 초기화
				one_row = [];
				// date
				dateNameValue = col[0].split("=");
				one_row.push(dateNameValue[1]);
				// price
				priceNameValue = col[1].split("=");
				one_row.push(parseInt(priceNameValue[1]));
				// (date, price)
				all_row.push(one_row);
			};
			var data = new google.visualization.DataTable();
			data.addColumn('string', 'date');
			data.addColumn('number', 'price');
			data.addRows(all_row);
			var options = null;
			var options = {
				hAxis : {
					title : 'time',
					textPosition: 'none'
				},
				vAxis : {
					title : 'price'
				}
			
			};
			var chart = new google.visualization.LineChart(document
					.getElementById('kosdaq_chart'));
			chart.draw(data, options);
		};
		// 현재 주식 상황 보기 그리기
		var currentData_kospi = function(element, txtData) {
			row = txtData.split("@"); // row1@row2@... 
			var strTag = "<b>";

			// 내가 필요한 것은 가장 최근이므로, 가장 마지막 row(에서 2번째. 가장 마지막 row는 비어있다.)
			rowIndex = row.length - 2;
			col = row[rowIndex].split("|"); // row의 각 col들 (name=value|name=value)

			// code , date, presentPrice, sign, difference, prevEndPrice, volume
			if (col.length > 4) {
				presentPrice = col[1].split("=");
				sign = col[2].split("=");
				difference = col[3].split("=");
				strTag += "현재 가격: " + presentPrice[1];
				if (sign[1] == "상승") {
					strTag += "<b style='color:red;'>&emsp;&emsp;&emsp; ▲ ";

				} else if (sign[1] == "하락") {
					strTag += "<b style='color:blue;'>&emsp;&emsp;&emsp; ▼ ";
				} else { // 보합, ���� 
					strTag += "<b style='color:black;'>&emsp;&emsp;&emsp; 〓 ";
				}

				strTag += difference[1] + "</b><br/>";
			}
			strTag += "</b>";
			element.innerHTML = strTag;
		};
		var currentData_kosdaq = function(element, txtData) {
			row = txtData.split("@"); // row1@row2@... 
			var strTag = "<b>";

			// 내가 필요한 것은 가장 최근이므로, 가장 마지막 row(에서 2번째. 가장 마지막 row는 비어있다.)
			rowIndex = row.length - 2;
			col = row[rowIndex].split("|"); // row의 각 col들 (name=value|name=value)

			// code , date, presentPrice, sign, difference, prevEndPrice, volume
			if (col.length > 4) {
				presentPrice = col[1].split("=");
				sign = col[2].split("=");
				difference = col[3].split("=");
				strTag += "현재 가격: " + presentPrice[1];
				if (sign[1] == "상승") {
					strTag += "<b style='color:red;'>&emsp;&emsp;&emsp; ▲ ";

				} else if (sign[1] == "하락") {
					strTag += "<b style='color:blue;'>&emsp;&emsp;&emsp; ▼ ";
				} else { // 보합, ���� 
					strTag += "<b style='color:black;'>&emsp;&emsp;&emsp; 〓 ";
				}

				strTag += difference[1] + "</b><br/>";
			}
			strTag += "</b>";
			element.innerHTML = strTag;
		};
		setInterval(function() { 
			repeatChart();
			loadPresentPrice();
		}, 30000);
		setInterval(function() { 
			loadShowRank();
			loadHiddenRank();
		}, 1000);
		window.onload = function(){
			rotate();
			loadShowRank();
			loadHiddenRank();
		}
	</script>			
		
	<section style="color : white;">

		<nav>
			실시간 차트<br /> <img class="bar" src="/StockInsight/asset/body_titleUnderBar.jpg"
				style="padding-top: 20px; width: 121px; height: 10px; float: center;">
		</nav>

		<div class="main">
			 <table id= "main_table">
				<tr>
					<th style="font-size:20pt">
						<div id="kospi_name" ><b> KOSPI 차트 </b></div>
					</th>
					<th style="font-size:20pt">
						<div id="kosdaq_name"><b> KOSDAQ 차트 </b></div>
					</th>
				</tr>
				<tr >
					<td >
					<div id="kospi_chart"/>
					</td>
					<td >
						<div id="kosdaq_chart"/>
					</td>					
				</tr>
				<tr>
					<td>
						<div id="kospi_pre_data" style="font-size:15pt" > </div>
					</td>
					<td>
						<div id="kosdaq_pre_data" style="font-size:15pt"></div>
					</td>					
				</tr>
				 <tr>
				<td style="padding:20px;">
					<a href="https://search.naver.com/search.naver?where=news&sm=tab_jum&query=%EC%BD%94%EC%8A%A4%ED%94%BC" 
            	target="_blank" style="text-decoration: underline;  color: gray; ">
            	<b style="font-size: 20px; margin-top: 25%;"> 📢 코스피 관련 이슈가 궁금하신가요?</b></a>					
					</td>
					<td style="padding:20px;">
					<a href="https://search.naver.com/search.naver?sm=tab_hty.top&where=news&query=%EC%BD%94%EC%8A%A4%EB%8B%A5&oquery=%EC%BD%94%EC%8A%A4%ED%94%BC&tqi=U9nm2lprvxZsshIjg5CssssssX0-066341" 
            	target="_blank" style="text-decoration: underline; color: gray; ">
            	<b style="font-size: 20px; margin-top: 25%;"> 📢 코스닥 관련 이슈가 궁금하신가요?</b></a>					
					</td>
				</tr>
			</table>
		</div>
		
		<div class="responsive_main">
			 <table id= "responsive_table">
            <tr>
               <th style="font-size:20pt; color: black;">
                  <div id="kospi_name" ><b> KOSPI 차트 </b></div>
               </th>
               <td>
               <div id="kospi_chart"/>
               </td>
               <td>
                  <div id="kospi_pre_data" style="font-size:15pt" > </div>
               </td>
                <td style="text-align: center; margin-bottom: 60px" >
               <a href="https://search.naver.com/search.naver?where=news&sm=tab_jum&query=%EC%BD%94%EC%8A%A4%ED%94%BC" 
               target="_blank" style="text-decoration: underline;  color: gray; ">
               <b style="font-size: 20px; margin-top: 25%;"> 📢 코스피 관련 이슈가 궁금하신가요?</b></a>               
               </td>               
            </tr>
            
            <tr>
               <th style="font-size:20pt; color: black;">
                  <div id="kosdaq_name"><b> KOSDAQ 차트 </b></div>
               </th>               
               <td>
                  <div id="kosdaq_chart"/>
               </td>             
               <td>
                  <div id="kosdaq_pre_data" style="font-size:15pt"></div>
               </td>   
                <td style="text-align: center; margin-bottom: 70px" >
               <a href="https://search.naver.com/search.naver?sm=tab_hty.top&where=news&query=%EC%BD%94%EC%8A%A4%EB%8B%A5&oquery=%EC%BD%94%EC%8A%A4%ED%94%BC&tqi=U9nm2lprvxZsshIjg5CssssssX0-066341" 
               target="_blank" style="text-decoration: underline; color: gray; ">
               <b style="font-size: 20px; margin-top: 25%;"> 📢 코스닥 관련 이슈가 궁금하신가요?</b></a>               
               </td>

            </tr>
           
         </table>
      </div>
	</section>

	<jsp:include page="/jsp/footer.jsp"/>
	
</body>
</html>