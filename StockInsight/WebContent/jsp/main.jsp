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
<script src="./js/myAjax.js"></script>
<meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
<title>Stock Insight</title>
<link rel="stylesheet" type="text/css" href="/StockInsight/css/style.css" />


<script type="text/javascript">
	var n = 0;
	var imgs = new Array("/StockInsight/asset/main_header_titleOn.png", "/StockInsight/asset/main_header_titleOff.png");
	function rotate() {
		document.images.slide.src = imgs[n];
		(n == (imgs.length - 1)) ? n = 0 : n++;
		setTimeout("rotate()", 800);
	}
</script>
<script type="text/javascript">
   function popupOpen() {

	   var popUrl = "/Stock_Insigh/doPop"; //팝업창에 출력될 페이지 URL

      var popOption = "width=400, height=400, resizable=no, scrollbars=no, status=no;"; //팝업창 옵션(optoin)

      window.open(popUrl, "", popOption);
   }
</script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"
	type="text/javascript"></script>
<script>
	$(function() {
		var count = $('#rank-list li').length;
		var height = $('#rank-list li').height();
		function step(index) {
			$('#rank-list ol').delay(2000).animate({
				top : -height * index,
			}, 500, function() {
				step((index + 1) % count);
			});
		}
		step(1);
	});
</script>
<script>
	function showplay() {
		var flag = $('#hidTempSynopsis');
		var btn = document.getElementById("D");
		var SynopsisDiv = $('#content');
		var real = $('#D');
		var flagValue = flag.val();
		if (flag != null) {
			if (flagValue == "0") {
				real.css("display", "block");
				var tag;
				//tag = "<ul>";
				tag  = "<a href=\"#\" style='color: cornflowerblue;font-size: 16px;'><b>실시간 거래량 순위  &nbsp;</b></a></br>";
				tag += "<section id='hiddenRank' style='padding:3px 10px 10px 10px;'>";
				
				tag += "</section>";
				//tag += "</ul>";
				btn.innerHTML = tag;
				$("#synopMore").text("▲");
				flag.val("1");
			} else {
				//SynopsisDiv.css("height", "77px");
				real.css("display", "none");
				$("#synopMore").text("▼");
				flag.val("0");
			}
		}
	}
</script>
</head>

<body onload='rotate()'>

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
			loadShowRank();
			loadHiddenRank();
		}
	</script>
	<div class="front">
		<div class="logo">
			<a href="/StockInsight/jsp/main.jsp"><img src="/StockInsight/asset/header_logo.png"
				style="width: 336px; height: 148px; float: left;"></a>
		</div>

		<div id="content-rank"
			style="position: absolute; margin-left: 380px; margin-top: 65px;">
			<dl id="rank-list">
				<dd>
					<ol id="showRank" style="font-family: 'nanum';" >
						<li><a href='javascript:showplay();'> </a></li>
						<li><a href='javascript:showplay();'> </a></li>
						<li><a href='javascript:showplay();'> </a></li>
						<li><a href='javascript:showplay();'> </a></li>
						<li><a href='javascript:showplay();'> </a></li>
						<li><a href='javascript:showplay();'> </a></li>
						<li><a href='javascript:showplay();'> </a></li>
						<li><a href='javascript:showplay();'> </a></li>
						<li><a href='javascript:showplay();'> </a></li>
						<li><a href='javascript:showplay();'> </a></li>						
					</ol>
				</dd>
			</dl>
		</div>
		<div id="D"
			style="display:none; border-radius:20px; position: absolute;  margin-left: 380px; margin-top: 85px; padding-top: 10px; padding-left: 10px; background-color: #ffffffdd; font-size:14px; font-family: 'nanum';"></div>
		<input name="hidTempSynopsis" type="hidden" id="hidTempSynopsis"
			value="0">
		<!-- value 체크값을 위함 -->

		<%
			if (session.getAttribute("ID") != null) {
			// 세션 존재
		%>
		<ul id="okaylogin_ul">
			<li id="okaylogin_li">
				<%
					String name = (String) session.getAttribute("NAME");
				out.println("<b>" + name + "</b> 님 환영합니다. </br>");
				%>
			</li>
			</br> 			
			<li id="okaylogin_li"><a href="javascript:popupOpen();" id="red"><b>알림확인</b></a></li>&nbsp;&nbsp;&nbsp;
			<li id="okaylogin_li"><a href="/Stock_Insigh/sortPredict">🥇예측순위</a></li>&nbsp;&nbsp;&nbsp;
			<li id="okaylogin_li"><a href="/Stock_Insigh/doLogout"> 로그아웃
			</a></li> &nbsp; &nbsp;
			<li id="okaylogin_li"><a id="yellow" href="/StockInsight/jsp/main.jsp">메인화면</a></li>
			&nbsp; &nbsp;
			<li id="okaylogin_li"><a href="/Stock_Insigh/doStock">종목조회</a></li>
			&nbsp; &nbsp;
			<li id="okaylogin_li"><a href="/Stock_Insigh/doSearchInterest">관심종목</a></li>
			&nbsp; &nbsp;
			<li id="okaylogin_li"><a href="mypage.jsp">마이페이지</a></li> &nbsp;
			&nbsp;
			<li id="okaylogin_li"><a
				href="/Stock_Insigh/postList?pageIndex=1">문의하기</a></li>
			</br>
		</ul>
		<%
			} else {
		// 세션존재하지 않음
		%>
		<ul>
			<li><a href="login.jsp">로그인</a></li> &nbsp; &nbsp;
			<li><a id="yellow" href="/StockInsight/jsp/main.jsp">메인화면</a></li> &nbsp; &nbsp;
			<li><a href="/Stock_Insigh/doStock">종목조회</a></li> &nbsp; &nbsp;
			<li><a href="/Stock_Insigh/doSearchInterest">관심종목</a></li> &nbsp;
			&nbsp;
			<li><a href="mypage.jsp">마이페이지</a></li> &nbsp; &nbsp;
			<li><a href="/Stock_Insigh/postList?pageIndex=1">문의하기</a></li>
		</ul>

		<%
			}
		%>
	</div>

	<div>
		<header>
			<center>
				<img src="/StockInsight/asset/main_header_titleOff.png" id="slide"
					style="width: 1200; height: auto";">
			</center>
		</header>
	</div>
	<section id="content">

		<nav>
			실시간 차트<br /> <img class="bar" src="/StockInsight/asset/body_titleUnderBar.jpg"
				style="padding-top: 20px; width: 121px; height: 10px; float: center;">
		</nav>

		<!-- <div class="main">
			<div class="kos_char">KOSPI 차트</div>
			<div class="kod_char">KOSDAQ 차트</div>
		</div>-->
		<div class="main">
			<table style="width: 90%; margin-left: auto; margin-right: auto; ">
				<tr >
					<td style="font-size:20pt">
						<div id="kospi_name" ><b> KOSPI 차트 </b></div>
					</td>
					<td style="font-size:20pt">
						<div id="kosdaq_name"><b> KOSDAQ 차트 </b></div>
					</td>
				</tr>
				<tr >
					<td >
					<div id="kospi_chart"
							style="border: 1px solid black; width:400px; height: 300px;margin-left: auto; margin-right: auto; margin-top:20px; margin-bottom:10px;"></div>
					</td>
					<td >
						<div id="kosdaq_chart"
							style=" border: 1px solid black; width:400px; height: 300px;margin-left: auto; margin-right: auto; margin-top:20px; margin-bottom:10px;"></div>
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

	</section>

	<footer>
		<p>​© 2020 본 홈페이지의 모든 권리는 베짱이찬가에 귀속됩니다.</p>
	</footer>

</body>
</html>