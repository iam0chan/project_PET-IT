<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/views/header.jsp"%>    
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>fnq 관리자 작성페이지</title>
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

    .qna-content{
        display: flex;
        justify-content:center;
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
     
    </style>

</head>
<body>
    <section class="container mt-3">
        <div class="space" >
            <div class="titleArea">
                <h2>fnq 작성</h2>
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
                                    <option value="1">자주하는 질문</option>
                                    <option value="2">회원서비스</option>
                                    <option value="3">주문/결제</option>
                                    <option value="4">배송</option>
                                    <option value="5">취소/반품/교환</option>
                                </select>
                                <input id="qna-title" style="width: 400px;" type="text">
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