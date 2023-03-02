<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	table *{margin:5px;}
    table{width:100%;}
</style>
</head>
<body>
	<!-- 이쪽에 메뉴바 포함 할꺼임 -->
    <jsp:include page="../common/header.jsp"/>

    <div class="content">
        <br><br>
        <div class="innerOuter">
            <h2>게시글 상세보기</h2>
            <br>
            
            <!-- 로그인후 상태일 경우만 보여지는 글쓰기 버튼-->
            <a class="btn btn-secondary" style="float:right" href="list.bo">목록으로</a>
            <br><br>
            <table id="contentArea" align="center" class="table">
                <tr>
                    <th width="100">제목</th>
                    <td colspan="3">${ b.boardTitle }</td>
                </tr>
                <tr>
                    <th>작성자</th>
                    <td>${ b.boardWriter }</td>
                    <th>작성일</th>
                    <td>${ b.createDate }</td>
                </tr>
                <tr>
                    <th>첨부파일</th>
                    <td colspan="3">
                    	<c:choose>
	                    	<c:when test="${ not empty b.originName }">
	                        	<a href="${ b.changeName }" download="${ b.originName }">${ b.originName }</a>
	                        </c:when>
	                        <c:otherwise>
	                        	첨부파일이 없습니다.
	                        </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                <tr>
                    <th>내용</th>
                    <td colspan="3"></td>
                </tr>
                <tr>
                    <td colspan="4"><p style="height:150px">${ b.boardContent }</p></td>
                </tr>
            </table>
            <br>

			<c:if test="${ loginUser.userId eq b.boardWriter }">
	            <div align="center">
	                <a class="btn btn-primary" onclick="postFormSubmit(1);">수정하기</a>
	                <a class="btn btn-danger" onclick="postFormSubmit(2);">삭제하기</a>
	            </div><br><br>
	            
	            <form action="" method="post" id="postForm">
	            	<input type="hidden" name="no" value="${ b.boardNo }">
	            	<input type="hidden" name="filePath" value="${ b.changeName }">
	            </form>
	            
	            <script>
	            	function postFormSubmit(num){
	            		if(num == 1){ // 수정하기클릭시
	            			$("#postForm").attr("action", "updateForm.bo").submit();
	            		}else{ // 삭제하기클릭시
	            			$("#postForm").attr("action", "delete.bo").submit();
	            		}
	            	}
	            </script>
	            
            </c:if>

            <!-- 댓글 기능은 나중에 ajax 배우고 접목시킬예정! 우선은 화면구현만 해놓음 -->
            <table id="replyArea" class="table" align="center">
                <thead>
                	<c:choose>
	                	<c:when test="${ empty loginUser }">
		                    <tr>
		                        <th colspan="2">
		                            <textarea class="form-control" cols="55" rows="2" style="resize:none; width:100%" readonly>로그인한 사용자만 이용가능한 서비스입니다. 로그인 후 이용바랍니다.</textarea>
		                        </th>
		                        <th style="vertical-align: middle"><button class="btn btn-secondary" disabled>등록하기</button></th>
		                    </tr>
	                    </c:when>
	                    <c:otherwise>
		                    <tr>
		                        <th colspan="2">
		                            <textarea class="form-control" id="content" cols="55" rows="2" style="resize:none; width:100%"></textarea>
		                        </th>
		                        <th style="vertical-align: middle"><button class="btn btn-secondary" onclick="addReply();">등록하기</button></th>
		                    </tr>
	                    </c:otherwise>
                    </c:choose>
                    
                    <tr>
                       <td colspan="3">댓글 (<span id="rcount"></span>) </td> 
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
        <br><br>
    </div>
    
    <script>
    	$(function(){
    		selectReplyList();
    	})
    	
    	function addReply(){ // 댓글 작성용 ajax
    		
    		if($("#content").val().trim().length > 0){ // 유효한 댓글 작성시 => insert ajax요청
    			
    			$.ajax({
    				url:"rinsert.bo",
    				data:{
    					replyContent:$("#content").val(),
    					replyWriter:'${loginUser.userId}',
    					refBoardNo:${b.boardNo}
    				},success:function(result){
    					if(result == "success"){
    						$("#content").val("");
    						selectReplyList();
    					}
    				},error:function(){
    					console.log("댓글 작성용 ajax 통신 실패");
    				}
    			})
    			
    			
    		}else{
    			alertify.alert("댓글 작성 후 등록 요청해주세요");
    		}
    			
    	}
    	
    	function selectReplyList(){ // 해당 게시글에 딸린 댓글리스트 조회용 ajax
    		$.ajax({
    			url:"rlist.bo",
    			data:{no:${b.boardNo}},
    			success:function(list){
    				console.log(list);
    				
    				let value = "";
    				for(let i=0; i<list.length; i++){
    					value += "<tr>"
    							+	"<th>" + list[i].replyWriter + "</th>"
    							+	"<td>" + list[i].replyContent + "</td>"
    							+	"<td>" + list[i].createDate + "</td>"
    							+"</tr>";
    				}
    				
    				$("#replyArea tbody").html(value);
    				$("#rcount").text(list.length);
    				
    			},error:function(){
    				console.log("댓글리스트 조회용 ajax 통신 실패");
    			}
    		})
    	}
    </script>

    <!-- 이쪽에 푸터바 포함할꺼임 -->
    <jsp:include page="../common/footer.jsp"/>
</body>
</html>