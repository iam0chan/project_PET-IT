<%@page import="com.pet.question.model.dto.QuestionComment"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.pet.question.model.dto.Question" %>	
<%
	Question q = (Question) request.getAttribute("question");
	QuestionComment qcc = (QuestionComment) request.getAttribute("questionComment"); //댓글 
%>
<%@ include file="/views/header.jsp"%>

<style>
* {
	letter-spacing: -.5px;
	font-weight: 300;
	line-height: 1.0;
}

.titleArea {
	padding-top: 80px;
	margin: 0 auto;
	display: flex;
	flex-direction: column;
	align-items: center;
	text-align: center;
}

.container mt-3 {
	display: flex;
	flex-direction: column;
	justify-content: center;
	width: 1000px !important;
	padding: 0px !important;
}

.row {
	width: 900px;
/* 	margin: 0 auto; */
	display: flex{0,1,0};
	flex-direction: column;
	align-items: center;
}

.space {
	width: 900px;
	display: flex;
	flex-direction: column;
/* 	align-items: center; */
	margin: 0px auto;
}

#contentBox{
	width: 900px !important;
}

#contentBox img {
	width: 600px !important;
	height : 500px !important;
}
#comment-container {
	text-align: center;
}

.btn-container{
	text-align: center;
}

#comment-btn{
	margin-bottom: 30px;
}

#comment-content{
	text-align: center;
	
	
}

.comment-container{
	display:flex;
	justify-content:center;
}

</style>
<div class="space">
	<div class="titleArea">
		<h2>1:1문의</h2>
	</div>
	
	<div class="container mt-3">
		<div class="row">
			<table class="table table-bordered">
					<%if(q!=null) {%>
					<tr>
						<th style="text-align: center; background-color: #F2F2F2;">제목</th>
						<th style="color:black;"><%=q.getQuestionTitle()%></th>
					</tr>
					<tr>
						<td style="text-align: center; background-color: #F2F2F2;">작성일</td>
						<td><%=q.getQuestionDate()%></td>
					</tr>
			</table>
			<table class="table table-bordered">
				<thead>
					<tr>
						<th style="text-align: center; background-color: #F2F2F2;">내용</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td id="contentBox"><%=q.getQuestionContent()%></td>
					</tr>
				</tbody>
				<%} %>
			</table>
		</div>
	</div>
</div>
	<br><br>
	<!--관리자 댓글기능-->
	<div id = "comment-container">
	<%if(loginMember!=null
			&&loginMember.getMemberId().equals("petitad")){ %>
		<div class="comment-editer">
		<form action="<%=request.getContextPath()%>/insertComment.do" method="post">
			<input type="hidden" name="questionRef" value="<%=q.getQuestionNo()%>">
			<textarea name="content" cols="55" rows="3"></textarea>
			<button id="comment-btn" type="submit" class="btn btn-outline-success" onclick="listBtn();">등록</button>
		</form> 
		</div>
	</div>
	<%} %>
	<!--댓글출력화면 -> 댓글이 있으면 출력해라 -->
	<div class="comment-container">
	<%if(qcc!=null){%>
	<table id="comment-content"> <!--댓글이 없을 수도 있으니까 분기처리 해야함-->
		<tr>
			<th>작성자 : Pet-it</th>
		</tr>
		<tr><td></td></tr>
		<tr>
			<th style="font-size:13px"><%=qcc.getReplyDate() %></th>		
		</tr>
		<tr><td></td></tr>
		<tr><td><div style="border-bottom:1px solid #ccc8c8; width:900px;"></div></td></tr>
		
		<tr><td></td></tr>
		<tr><td></td></tr>
		<tr><td></td></tr>
		<tr><td></td></tr>
		<tr >
			<th><%=qcc.getReplyContent() %></th>
		</tr>
	</table>
	<%} %>
	</div>
	<br><br><br>	
	
	
	<!--목록버튼-->
	<div class="btn-container">
		<button id="list-btn" class="btn btn-outline-success" onclick="listBtn(); ">목록</button>
	</div>
	
	
	
<script>
	//목록버튼 눌렀을 때 questionList로 페이지이동 
	const listBtn = ()=> {
		location.replace("<%=request.getContextPath()%>/questionList.do");
	}
	
	
</script>
<%@ include file="/views/footer.jsp"%>