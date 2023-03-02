<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
</head>
<body>

	<h1>Spring에서의 AJAX</h1>
	
	<h3>1. 요청시 값 전달, 응답결과 받아보기</h3>
	이름 : <input type="text" id="name"> <br>
	나이 : <input type="number" id="age"> <br>
	<!-- <button id="btn">전송</button> -->
	<button onclick="test1();">전송</button>
	
	<div id="result1"></div>
	
	<script>
		/*
		$(function(){
			$("#btn").click(function(){
				
			})
		})
		*/
		
		/* -- 응답데이터가 한개였을 경우 --
		function test1(){
			$.ajax({
				url:"ajax1.do",
				data:{
					name:$("#name").val(),
					age:$("#age").val()
				},
				success:function(result){
					console.log(result);
					$("#result1").html(result);
				},error:function(){
					console.log("ajax 통신 실패");
				}
			});
		}
		*/
		
		// -- 응답데이터가 여러개일 경우 --
		function test1(){
			$.ajax({
				url:"ajax1.do",
				data:{
					name:$("#name").val(),
					age:$("#age").val()
				},
				success:function(result){
					console.log(result);
					
					// 응답데이터가 배열의 형태일 경우 => 인덱스로 접근 가능
					//let value = "이름 : " + result[0] + "<br>나이 : " + result[1];
					
					// 응답데이터가 단순객체의 형태일 경우 => 속성에 접근 가능 
					let value = "이름 : " + result.name + "<br>나이 : " + result.age;
					
					$("#result1").html(value);
					
				},error:function(){
					console.log("ajax 통신 실패");
				}
			});
		}
	</script>
	
	<h3>2. 조회요청 후 조회된 한 회원 객체(vo)를 응답받아서 출력하기</h3>
	조회할 회원번호 : <input type="number" id="userNo">
	<button onclick="test2();">조회</button>
	
	<div id="result2"></div>
	
	<script>
		function test2(){
			$.ajax({
				url:"ajax2.do",
				data:{num:$("#userNo").val()},
				success:function(obj){
					console.log(obj); // {속성:속성값}
					
					let value =   "<ul>"
								+	"<li>이름 : " + obj.userName + "</li>"
								+	"<li>아이디 : " + obj.userId + "</li>"
								+	"<li>전화번호 : " + obj.phone + "</li>"
								+ "</ul>";
					
					$("#result2").html(value);			
				},error:function(){
					console.log("ajax 통신 실패");
				}
			});
		}
	</script>

	<h3>3. 조회요청 후 조회된 회원리스트 응답받아서 출력해보기</h3>
	<button id="btn">회원 전체 조회</button>
	<br><br>
	
	<table border="1" id="result3">
		<thead>
			<tr>
				<th>이름</th>
				<th>아이디</th>
				<th>나이</th>
			</tr>
		</thead>
		<tbody>
		
		</tbody>
	</table>
	
	<script>
		$(function(){
			$("#btn").click(function(){
				$.ajax({
					url:"ajax3.do",
					success:function(list){
						
						console.log(list);
						
						let value = "";
						for(let i=0; i<list.length; i++){
							value += "<tr>"
									+	"<td>" + list[i].userName + "</td>"
									+	"<td>" + list[i].userId + "</td>"
									+	"<td>" + list[i].age + "</td>"
									+"</tr>";
						}
						
						$("#result3 tbody").html(value);
						
					},error:function(){
						console.log("ajax 통신 실패");
					}
				})
			})
		})
	</script>
	
	
	
	
	





</body>
</html>