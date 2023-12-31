<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/views/header.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>qna 관리자 작성페이지</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdn.ckeditor.com/ckeditor5/40.0.0/classic/ckeditor.js"></script>

    <style>
     * {
        letter-spacing: -.5px;
        font-weight: 300;
        }   
        .titleArea {
	    padding-top: 80px;
	    margin: 0 auto;
	    display: flex;
	    flex-direction: column;
	    align-items: center;
	    text-align: center;
    }

    .container mt-3{
        display:flex;
        flex-direction: column;
        justify-content: center;
        width:50%;
    }


.ck.ck-editor {
   width: 100%;
   max-width: 850px;
   margin: 0 auto;
}

.ck-editor__editable {
   height: 50vh;
} 

#submitBtn{
	text-align:center;
	
}

#myform fieldset{
    display: inline-block;
    direction: rtl;
    border:0;
}
#myform fieldset legend{
    text-align: right;
}
#myform input[type=radio]{
    display: none;
}
#myform label{
    font-size: 3em;
    color: transparent;
    text-shadow: 0 0 0 #f0f0f0;
}
#myform label:hover{
    text-shadow: 0 0 0 rgba(250, 208, 0, 0.99);
}
#myform label:hover ~ label{
    text-shadow: 0 0 0 rgba(250, 208, 0, 0.99);
}
#myform input[type=radio]:checked ~ label{
    text-shadow: 0 0 0 rgba(250, 208, 0, 0.99);
}
#reviewContents {
    width: 100%;
    height: 150px;
    padding: 10px;
    box-sizing: border-box;
    border: solid 1.5px #D3D3D3;
    border-radius: 5px;
    font-size: 16px;
    resize: none;
}
     
    </style>

</head>
<body>
    <section class="container mt-3">
        <div class="space" >
            <div class="titleArea">
                <h2>구매후기</h2>
            </div>

            <div class="review-FiveStar" style="margin-left: 230px;" > 
            <link href="/assets/css/star.css" rel="stylesheet"/>
 	        <form class="mb-3" name="myform" id="myform" method="post">
                 <span class="text-bold">별점</span>
	            <fieldset>
		            <input type="radio" name="reviewStar" value="5" id="rate1"><label
			        for="rate1">★</label>
		            <input type="radio" name="reviewStar" value="4" id="rate2"><label
			        for="rate2">★</label>
		            <input type="radio" name="reviewStar" value="3" id="rate3"><label
			        for="rate3">★</label>
		            <input type="radio" name="reviewStar" value="2" id="rate4"><label
			        for="rate4">★</label>
		            <input type="radio" name="reviewStar" value="1" id="rate5"><label
		            for="rate5">★</label>
	                </fieldset>
                    </form>
                </div>
            <div class="container mt-3">
                <div class="row">
                    <div class="col-lg-2"></div>
                    <div class="col-lg-8">
                    <form action="#"> <!--noticeList  -->
                        <table class="table table-bordered">
                        <thead>
                            <tr>
                            <th style="text-align: center; background-color: #F2F2F2;">제목</th>
                            <th>
                                <select id="board_category" name="board_category" >
                                    <option value="1">사료</option>
                                    <option value="2">용품</option>
                                </select>
                                <input id="review-title" style="width: 400px;" type="text">
                            </th>
                            </tr>
                        </thead>
                        </table>
                    </div>
                    <div class="col-lg-2"></div>
                    
                </div>
                <div id="editor"></div>
                <br>
                </form>                
              </div>

              <div class="container mt-3">
                <div class="row">
                    <div class="col-lg-2"></div>
                    <div class="col-lg-8">
                    <form action="#">
                        <table class="table table-bordered">
                        <thead>
                            <tr>
                            <th style="text-align: center; width: 80px;  background-color: #F2F2F2;">첨부파일</th>
                            <th>
                                <input class="fileBtn" type="submit" onclick="test();" value="첨부파일" />
                            </th>
                            </tr>
                        </table>

              <div id="submitBtn">
                <input class="Btn" type="submit" onclick="test();" value="작성완료" />
              </div>
              <script>
	var editor;
	ClassicEditor
	.create(document.querySelector('#editor'), {
		ckfinder: {
			uploadUrl : '<%=request.getContextPath()%>/image/upload'
		}
	})
	.then(e => {
		editor = e;
		console.log('Editor was initialized');
	})
	.catch(error => {
		console.error(error);
	});
	
	function test(){	
	    const editorData = editor.getData();
	    /* console.log(editorData); */
	    $("#upload").attr("value",editorData);
	    console.log($("#upload").val());
	}
	
	</script>
              
              </body>
              </html>
<%@ include file="/views/footer.jsp"%>