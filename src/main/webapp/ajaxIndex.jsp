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
	
	<h4>1. 요청시 값 전달, 응답결과 받아보기</h4>
	이름 : <input type="text" id="name"> <br> 
	나이 : <input type="number" id="age"> <br> 
	<!-- <button id="btn">전송</button>  -->
	<button onclick="test1()";>전송</button> 
	
	
	
	<div id="result1"></div>
	
	<script>
	
		/*
		$(function(){
			$("btn").click(function(){
				
			})
		})
		*/
		
		/* -- 응답데이터가 하나였을 경우 
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
					//let value = "이름 : " + result[0] + "<br> 나이: " + result[1]; 
					
					// 응답데이터가 단순객체의 형태일 경우 => 속성에 접근 가능
					let value = "이름 : " + result.name + "<br>나이 : " + result.age; 
					
					$("#result1").html(value); 
					
				},error:function(){
					console.log("ajax 통신 실패"); 
				}
			}); 
		}
		
		
	</script>
</body>
</html>