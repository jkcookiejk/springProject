<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	#profileImg{
        width:250px;
        height:250px;
        border:1px solid lightgray;
        border-radius: 50%;
    }
</style>
</head>
<body>

	<!-- 이쪽에 메뉴바 포함 할꺼임 -->
     <jsp:include page="../common/header.jsp"/>

     <div class="content">
        <br><br>
        <div class="innerOuter">
            <h2>마이페이지</h2>
            <br>

            <div align="center">
                <img id="profileImg" src="<c:out value='${ loginUser.profileImg }' default='resources/profile_images/defaultProfile.png' />" onclick="$('#profileImgFile').click();">
                <input type="file" id="profileImgFile" style="display:none;">
            </div>
            
            <script>
            	$(function(){
            		$("#profileImgFile").change(function(){
            			
            			// * 비동기식으로 첨부파일 업로드
            			// 새로운 파일이 선택되는 순간 곧바로 ajax 요청으로
            			// 첨부파일 넘겨서 => 서버에 업로드 => db에도 update
            			
            			let formData = new FormData(); // 가상의 form요소
            			
            			let uploadFile = this.files[0]; // 현재 선택된 파일객체
            			//console.log(uploadFile);
            			
            			formData.append("uploadFile", uploadFile);
            			formData.append("userId", '${loginUser.userId}');
            			formData.append("originalFile", '${loginUser.profileImg}');
            			
            			$.ajax({
            				url:"uploadProfile.me",
            				data:formData, // 파일이 담겨있는 form
            				processData:false,
            				contentType:false,
            				type:"POST",
            				success:function(){
            					location.reload();
            				},error:function(){
            					
            				}
            			})
            			
            		});
            	})
            </script>

            <form action="update.me" method="post">
                <div class="form-group">
                    <label for="userId">* ID :</label>
                    <input type="text" class="form-control" id="userId" name="userId" value="${ loginUser.userId }" readonly><br>
                    
                    <label for="userName">* Name :</label>
                    <input type="text" class="form-control" id="userName" name="userName" value="${ loginUser.userName }" required><br>
                    
                    <label for="email"> &nbsp; Email :</label>
                    <input type="email" class="form-control" id="email" name="email" value="${ loginUser.email }"><br>
                    
                    <label for="age"> &nbsp; Age :</label>
                    <input type="number" class="form-control" id="age" name="age" value="${ loginUser.age }"><br>
                    
                    <label for="phone"> &nbsp; Phone :</label>
                    <input type="tel" class="form-control" id="phone" name="phone" value="${ loginUser.phone }"><br>
                    
                    <label for="address"> &nbsp; Address :</label>
                    <input type="text" class="form-control" id="address" name="address" value="${ loginUser.address }"><br>
                    
                    <label for=""> &nbsp; Gender : </label> &nbsp;&nbsp;
                    <input type="radio" name="gender" id="Male" value="M">
                    <label for="Male">남자</label> &nbsp;&nbsp;
                    <input type="radio" name="gender" id="Female" value="F">
                    <label for="Female">여자</label><br>
                    
                    <script>
                    	$(function(){
                    		if("${loginUser.gender}" != ""){
                    			$("input[value=${loginUser.gender}]").attr("checked", true);
                    		}
                    	})
                    </script>
                    
                </div>
                <br>
                <div class="btns" align="center">
                    <button type="submit" class="btn btn-primary">수정하기</button>
                    <button type="button" class="btn btn-secondary" data-toggle="modal" data-target="#updatePwdForm">비밀번호변경</button>
                    <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#deleteForm">회원탈퇴</button>
                </div>
            </form>

        </div>
        <br><br>
    </div>
    
    <!-- 비밀번호 변경용 Modal -->
	<div class="modal" id="updatePwdForm">
	  <div class="modal-dialog">
	    <div class="modal-content">
	
	      <div class="modal-header">
	        <h4 class="modal-title">비밀번호 변경</h4>
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	      </div>
	
	      <div class="modal-body" align="center">
	        <form action="updatePwd.me" method="post">
	        	<input type="hidden" name="userId" value="${ loginUser.userId }">
	        	<table>
	        		<tr>
	        			<td>현재 비밀번호</td>
	        			<td><input type="password" name="userPwd" required></td>
	        		</tr>
	        		<tr>
	        			<td>변경할 비밀번호</td>
	        			<td><input type="password" name="updatePwd" required></td>
	        		</tr>
	        	</table>
	        	<br>
	        	<button type="submit" class="btn btn-sm btn-secondary" >비밀번호 변경</button>
	        </form>
	      </div>
	
	    </div>
	  </div>
	</div>
    

    <!-- 회원탈퇴 버튼 클릭시 보여질 Modal -->
    <div class="modal" id="deleteForm">
        <div class="modal-dialog">
            <div class="modal-content">
            
                <!-- Modal Header -->
                <div class="modal-header">
                <h4 class="modal-title">회원탈퇴</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
                
                <!-- Modal body -->
                <div class="modal-body" align="center">
                
                    <b>
			                        탈퇴 후 복구가 불가능합니다. <br>   
			                        정말로 탈퇴 하시겠습니까?
                    </b>

                    <form action="delete.me" method="post">
                        비밀번호 : 
                        <input type="password" name="userPwd" required>
                        <input type="hidden" name="userId" value="${ loginUser.userId }">

                        <button type="submit" class="btn btn-danger">탈퇴하기</button>
                    </form>

                </div>
                
            </div>
        </div>
    </div>

    <!-- 이쪽에 푸터바 포함할꺼임 -->
    <jsp:include page="../common/footer.jsp"/>

</body>
</html>