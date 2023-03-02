<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
    /* div{border:1px solid red;} */
    #footer{
        width:80%;
        height:200px;
        margin:auto;
        margin-top:50px;
    }
    #footer-1{
        width:100%;
        height:20%;
        border-top:1px solid lightgray;
        border-bottom:1px solid lightgray;
    }
    #footer-2{width:100%;height:80%;}
    #footer-1, #footer-2{padding-left:50px}
    #footer-1 > a{
        text-decoration:none;
        font-weight: 600;
        margin:10px;
        line-height: 40px;
        color: black;
    }
    #footer-2>p{
        margin: 0;
        padding:10px;
        font-size: 13px;
    }
    #p2{text-align:center;}
</style>
</head>
<body>
	<div id="footer">
        <div id="footer-1">
            <a href="#">이용약관</a> | 
            <a href="#">개인정보취급방침</a> | 
            <a href="#">인재채용</a> | 
            <a href="#">고객센터</a> 
        </div>

        <div id="footer-2">
            <p id="p1">
                상호명 : GooDee Academy <br>
				전화 : 02-2108-5900 <br>
				팩스 : 02-2108-5909 <br>
				본관 : (08505) 서울특별시 금천구 가산디지털2로 115 (가산동, 대륭테크노타운3차) 509호, 811호, 1109-1호 <br>
				별관 : (08506) 서울특별시 금천구 가산디지털1로 151 (가산동, 세진 이노플렉스1차) 306호
            </p>
            <p id="p2">Copyright ⓒ GooDee Academy. All rights reserved.</p>     
        </div>
    </div>
</body>
</html>