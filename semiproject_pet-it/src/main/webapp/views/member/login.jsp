<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/views/header.jsp"%>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.security.SecureRandom" %>
<%@ page import="java.math.BigInteger" %>

<style>
@import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap');
	* {
	font-family: 'Noto Sans KR', sans-serif;
	}
	#container{
		margin-top:50px;
	}
	
		.navbar {
			
			height:130px;
		}
	    .footer_section{
	    	height:130px;
	    }
	section {
        text-align:center;
        width: 350px;
	    max-width: 1000px;
	    height: 599px;
	    padding: 0 20px;
	    margin: 0px auto;
	    margin-bottom: 50px;
	    background: white;
    }
	body {
        box-sizing: border-box;
        line-height:40px;
    }
	.login-container{
		margin-top:20px;
	}
    
    
  	position:absolute;
  	top: 50%;
 	 left: 50%;
  	margin: -25px 0 0 -25px;
	}

    .form-group {
        margin-top: 20px;
        
    }
    

	
    .input-field {
        width: 100%;
        padding: 10px;
    }

    .btn:not(.btn-outline-success) {
        width: 100%;
        height: 35px;
        margin-bottom : 10px;
    }

    .find-member {
        margin-bottom:0px;
        display:flex;
		justify-content: end;       
    }

    .find-member a {
        font-size: 14px;
        margin: auto;
        padding:0px 5px;
		color:black;    
        
    }
	.find-member .find-memberId>a{
		border-right:1px solid lightgray
	}
	
    .find-member .find-memberId,.find-member .find-memberPw{
    	
    }
    
    .sns-login * {
    	margin-bottom : 10px;
    	border-radius: 0.25rem;
    	width:300px;
    	height:43px;
    }
    
    .sns-login div {
    	padding:0px;
    }
    
    hr{
    	background-color:lightgray;
    	margin-bottom : 20px;
    }
</style>

<%if(loginMember==null){ %>
<section id=container>
		<div class="login-container">
        <h5 style="font-weight: bold; margin-bottom:30px;">로그인</h5>
        <form id="loginFrm" action="<%=request.getContextPath()%>/login.do" method="post">
            <div class="form-group">
                <input type="text" name="memberId" placeholder="아이디" class="form-control" style="width:300px;" autocomplete="off"; id="memberId">
            </div>
            <div class="form-group">
                <input type="password" name="memberPw" placeholder="비밀번호" class="form-control" style="width:300px;">
            </div>
           	<div class="find-member">
		        <div class="find-memberId">
		            <a class="forgot-id" href="<%=request.getContextPath()%>/findId.do">아이디 찾기</a>
		        </div>
		        <div class="find-memberPw">
		            <a class="forgot-pw" href="<%=request.getContextPath()%>/findPw.do">비밀번호 찾기</a>
	            </div>
	        </div>
            <input type="submit" class="btn btn-primary btn-sm" style="width:300px; height:43px;" value="로그인">
        </form>
        <input type="button" class="btn btn-outline-primary" style="width:300px; height:43px" onclick="location.assign('<%=request.getContextPath()%>/enrollView.do');"
        	value="회원가입">
        <hr>	
        <div class="sns-login" style="height:100px;">
            <div class="btn kakao_id_login"><a href="javascript:kakaoLogin()"><img src="<%=request.getContextPath()%>/img/kakao_login.png"></a></div>
            <p style="height:10px; margin:0px 0px 0px 0px;">
            <div class="btn naver_id_login"><a><img src="<%=request.getContextPath()%>/img/naver_login.png"></a></div>
            <!-- <span class="btn btn-success" style="width:300px; height:45px">네이버 로그인</span> -->
        </div>
    </div>
</section>
<%} %>
<script type="text/javascript" src="https://developers.kakao.com/sdk/js/kakao.js"></script>
<script type="text/javascript">
/* 카카오 로그인 */
Kakao.init('6014fee111349e7f74291c3efcd85575');
function kakaoLogin() {
    Kakao.Auth.login({
        success: function (response) {
            Kakao.API.request({
                url: '/v2/user/me',
                success: function (response) {
                    alert(JSON.stringify(response))
                },
                fail: function (error) {
                    alert(JSON.stringify(error))
                },
            })
        },
        fail: function (error) {
            alert(JSON.stringify(error))
        },
    })
}

/* 네이버 로그인 */
	var naver_id_login = new naver_id_login("fR0lnpixp0NakyK32xPp", "http://localhost:9090/semiproject_pet-it/views/index.jsp");
	var state = naver_id_login.getUniqState();
	naver_id_login.setButton("white", 2,40);
	naver_id_login.setDomain(".service.com");
	naver_id_login.setState(state);
	naver_id_login.setPopup();
	naver_id_login.init_naver_id_login();
</script>

<%@ include file="/views/footer.jsp"%>