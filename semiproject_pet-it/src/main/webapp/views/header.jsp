<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 11.14 17:22 jm member.dto, loginMember추가-->
<%@ page import="com.pet.member.dto.Member" %>
<%
   Member loginMember=(Member)session.getAttribute("loginMember");
%>
<!doctype html>
<style>
@import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap');
	* {
	font-family: 'Noto Sans KR', sans-serif;
	}
</style>
    <head>
        <!-- Basic -->
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=chrome" />
        <!-- Mobile Metas -->
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <!-- Site Metas -->
        <link rel="icon" href="<%=request.getContextPath()%>/img/favicon-16x16.png" type="image/png" />
        <meta name="keywords" content="" />
        <meta name="description" content="" />
        <meta name="author" content="" />
      
        <title>Pet-It</title>
      
      
        <!-- bootstrap core css -->
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.css" />
      
        <!-- fonts style -->
        <link href="https://fonts.googleapis.com/css?family=Poppins:400,600,700&display=swap" rel="stylesheet" />
      
        <!-- font awesome style -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"/>
        <!-- Custom styles for this template -->
        <link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" />
        <!-- responsive style -->
        <link href="<%=request.getContextPath()%>/css/responsive.css" rel="stylesheet" />
        <!-- jQery -->

        <script src="<%=request.getContextPath()%>/js/jquery-3.4.1.min.js"></script>

      
      <script>
            $(document).ready(function(){
             /* $(".header-menu-item>a").hover(function(){
                $(this).parent().find("ul").css("display","block");
             })
             $(".header-menu-item>ul").mouseout(function(){
                $(this).css("display","none");
             }) */
             //hover -> 사용자가 마우스로 nav-bar 중 li에 hover시 보여주고 싶다.
             //submenu위에서 menu가 유지되고 벗어날 시 사라짐
             $(".header-menu-item>a").mouseenter(function(e){
                $(e.target).parent().find("ul").css("display","block");
                /* $(e.target).find("ul").css("display","none"); */
             })
             $(".header-menu-item>a").mouseleave(function(e){
                $(e.target).parent().find("ul").css("display","none");
                /* $(e.target).find("ul").css("display","none"); */
             })
             $(".sub-nav").mouseenter(function(e){
                $(this).css("display","block");
                /* $(e.target).find("ul").css("display","none"); */
             })
             $(".sub-nav").mouseleave(function(e){
                $(this).css("display","none");
                /* $(e.target).find("ul").css("display","none"); */
             })
             
   
             /* yc) 231109 10:19 수정  */
             $(".sub-item").mouseenter(function(){
                $(this).css("background-color","#04aa6d");
             })
             $(".sub-item").mouseleave(function(){
                $(this).css("background-color","white");
             })
          })
          
          {
             
          }
      </script>
      <style>
         .sub-nav li{
            height: 65px;
            line-height:3.0 !important;
         }
         
         .quote_btn-container{
         	width: 300px;
    		display: flex;
    		justify-content: center;
         }
         header{
         	margin-bottom:30px !important;
         }
      </style>
      </head>
      
      <body>
         
        <div class="hero_area">
          <!-- header section strats // 11/16 헤더 그림자 추가 - YC -->
          <header class="header_section long_section px-0" style="margin:0px;">
            <nav class="navbar navbar-expand-lg custom_nav-container" style="padding:0px 40px; box-shadow: rgba(0, 0, 0, 0.07) 0px 3px 4px 0px;">
              <a class="navbar-brand" href="<%=request.getContextPath()%>/">
                <span>
                  <img src="<%=request.getContextPath()%>/img/petit_logo_bk.png" width="185" height="65">
                </span>
              </a>
              <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class=""> </span>
              </button>
      
              <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <div class="d-flex mx-auto flex-column flex-lg-row align-items-center" style="font-size: 15pt; font-family: G마켓 산스;">
                  <ul class="navbar-nav  ">
                    <li class="nav-item active header-menu-item">
                      <a class="nav-link " href="<%=request.getContextPath() %>/views/brandInfo/brandInfoPage.jsp">Pet-It <span class="sr-only">(current)</span></a>
                       <ul class="sub-nav">
                         <a href="<%=request.getContextPath() %>/views/brandInfo/brandInfoPage.jsp"><li class = "sub-item">브랜드소개</li></a>
                         <a href="<%=request.getContextPath() %>/noticeList.do"><li class = "sub-item">공지사항</li></a>
                         <a id="questionBtn"><li class = "sub-item">1:1문의</li></a>
                         <!--로그인 한 회원만 1:1문의 볼 수 있는 script-->
                         <script>                         	
                         	$("#questionBtn").click(function(){
                         		if(<%=loginMember==null%>){
                         			alert('로그인 후 이용할 수 있습니다');
                         			location.href='<%=request.getContextPath()%>/views/member/login.jsp';
                         		}else{
                         			location.href='<%=request.getContextPath()%>/questionList.do';
                         		}
                         	});
                         </script>	
                         
                      </ul> 
                    </li>
                    <li class="nav-item header-menu-item">
                      <a class="nav-link " href="<%=request.getContextPath() %>/views/event/eventList.jsp">이벤트</a>
                     <!--  <ul class="sub-nav">
                         <li class = "sub-item">신상품</li>
                         <li class = "sub-item">베스트</li>
                         <li class = "sub-item">사료</li>
                         <li class = "sub-item">간식</li>
                         <li class = "sub-item">용품</li>     
                      </ul> -->
                    </li>
                    <li class="nav-item header-menu-item">
                      <a class="nav-link " href="<%=request.getContextPath()%>/productList.do?type=all">주요상품</a>
                      <ul class="sub-nav">
                         <li class = "sub-item"><a href="<%=request.getContextPath()%>/productList.do?type=new">신상품</a></li>
                         <li class = "sub-item"><a href="<%=request.getContextPath()%>/productList.do?type=T-1">사료</a></li>
                         <li class = "sub-item"><a href="<%=request.getContextPath()%>/productList.do?type=T-2">간식</a></li>
                         <li class = "sub-item"><a href="<%=request.getContextPath()%>/productList.do?type=T-3">용품</a></li>
                      </ul>
                    </li>
                    <li class="nav-item header-menu-item">
                    <!-- ty 11/21 17:27 관리자페이지 링크 추가 -->
                    <% if(loginMember!=null&& loginMember.getMemberId().equals("petitad")) {%>
                    	<a class="nav-link" href="<%=request.getContextPath() %>/views/admin/adminPage.jsp">관리자페이지</a>
                    <%}else{ %>
                        <a class="nav-link" href="<%=request.getContextPath() %>/views/myPage/mypageView.jsp">마이페이지</a>                    
                    <%} %>
                      <!-- <ul class="sub-nav">
                         <li class = "sub-item">신상품</li>
                         <li class = "sub-item">베스트</li>
                         <li class = "sub-item">사료</li>
                         <li class = "sub-item">간식</li>
                         <li class = "sub-item">용품</li>     
                      </ul> -->
                    </li>
                    <li class="nav-item header-menu-item">
                      <a class="nav-link" href="<%=request.getContextPath()%>/faqList.do">FaQ</a>
                      <!-- <ul class="sub-nav">
                         <li class = "sub-item">신상품</li>
                         <li class = "sub-item">베스트</li>
                         <li class = "sub-item">사료</li>
                         <li class = "sub-item">간식</li>
                         <li class = "sub-item">용품</li>     
                      </ul> -->
                    </li>
                  </ul>
                </div>
                <div class="quote_btn-container">
				
                  <%if(loginMember==null) {%>
                  <a href="<%=request.getContextPath()%>/views/member/login.jsp"> <!--11.13 12:07 jm 연결주소 추가-->

                    <i class="fa-solid fa-dog"></i>
                    <span>Login</span>
                  </a>
                  <%-- <a href="<%=request.getContextPath() %>/views/cart/cartList.jsp"> --%>
                  <%}else{ %>
                     <i class="fa-solid fa-dog"></i>
                     <span id="memberName"><%=loginMember.getMemberName() %>님</span><br>
                     <span id="logoutbtn" style="display:none;"
                        onclick="location.assign('<%=request.getContextPath()%>/logout.do');">로그아웃</span>
                  <%} %>
                  <style>
                     
                  </style>
                  <script>
              document.getElementById('memberName').addEventListener('click', function() {
                var logoutbtn = document.getElementById('logoutbtn');
                
                if (logoutbtn.style.display === 'none') {
                  logoutbtn.style.display = 'block';
                } else {
                  logoutbtn.style.display = 'none';
                }
              });
              
              /* document.getElementById('logoutbtn').addEventListener('click', function(){
                location.replace
                 
              }); */
              </script>
                  <a href="<%=request.getContextPath()%>/cart/cartList.do">
                    <i class="fa-solid fa-cart-shopping"></i>
                    <span>장바구니</span>
                  </a>
<!--                   <a href="">
                    <form class="d-flex">
                      <input class="form-control me-2" type="search" placeholder="상품검색" aria-label="Search">
                      <button class="btn btn-outline-success" type="submit"><i class="fa-solid fa-magnifying-glass"></i></button>
                    </form>
                  </a> -->
                  
                </div>
              </div>
            </nav>
          </header>
          <!-- end header section -->