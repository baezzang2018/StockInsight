<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script src="./js/myAjax.js"></script>
<meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/StockInsight/css/reset.css" />

<script type="text/javascript">
	var n = 0;
	var imgs = new Array("/StockInsight/asset/main_header_titleOn.png", "/StockInsight/asset/main_header_titleOff.png");
	function rotate() {	
		  if($('.flag').css('display') === 'block') {
			  document.images.slide.src = ""; //빈 소스 넣기	
		   } 
		  else {			  
			   (n == (imgs.length - 1)) ? n = 0  : n = 1;	
			   document.images.slide.src = imgs[n];
		   }
		  setTimeout("rotate()", 1000);	// rotate() 호출 계속적으로 
	}
</script>
<script type="text/javascript">
   function popupOpen() {

	   var popUrl = "/Stock_Insigh/doPop"; //팝업창에 출력될 페이지 URL
		//경로 변경 필수
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
		var btn = document.getElementById("popupRadius");
		var SynopsisDiv = $('#content');
		var real = $('#popupRadius');
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
<body>
<div class="flag"/>  <!-- 반응형 글씨 사라져야되서 항시 필요 -->
<div class="front">
		<div class="logo">
			<a href="/StockInsight/jsp/main.jsp"><img/></a>
		</div>

		<div id="content-rank">
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
		<div id="popupRadius"
			style="display:none; border-radius:20px; position: absolute;  margin-left: 380px; margin-top: 85px; padding-top: 10px; padding-left: 10px; background-color: #ffffffdd; font-size:14px; font-family: 'nanum';"></div>
		<input name="hidTempSynopsis" type="hidden" id="hidTempSynopsis"
			value="0">
		
		<!-- value 체크값을 위함 -->
		<%
			if (session.getAttribute("ID") == null) {
			// 세션 존재
		%>
		<ul id="success_Login">
			<li>
				<%
					String name = (String) session.getAttribute("NAME");
				out.println(" <b>" + name + "</b> 님 환영합니다. <a style=\"display:inline;\" href=\"javascript:popupOpen();\"/><b>🔔</b></a>");
				%>
			</li>
			</br> 			
			<!-- 알림확인 자리 나중에 확인 후 삭제할 주석 -->
			<li><a href="/Stock_Insigh/sortPredict">🥇예측순위</a></li>&nbsp;&nbsp;&nbsp;
			<li><a href="/Stock_Insigh/doLogout"> 로그아웃
			</a></li> &nbsp; &nbsp;
			<li><a style="color : var(--yellow);" href="/StockInsight/jsp/main.jsp">메인화면</a></li>
			&nbsp; &nbsp;
			<li><a href="/Stock_Insigh/doStock">종목조회</a></li>
			&nbsp; &nbsp;
			<li><a href="/Stock_Insigh/doSearchInterest">관심종목</a></li>
			&nbsp; &nbsp;
			<li><a href="mypage.jsp">마이페이지</a></li> &nbsp;
			&nbsp;
			<li><a
				href="/Stock_Insigh/postList?pageIndex=1">문의하기</a></li>
			</br>
		</ul>
		<%
			} else {
		// 세션존재하지 않음
		%>
		<ul>
			<li><a href="login.jsp">로그인</a></li> &nbsp; &nbsp;
			<li><a style="color : var(--yellow);" href="/StockInsight/jsp/main.jsp">메인화면</a></li> &nbsp; &nbsp;
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
			
</body>
</html>