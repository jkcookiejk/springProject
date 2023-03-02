<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
      .chat{width:400px; margin:auto; padding:10px; border:1px solid lightgray;}
      .chat-area{height:500px; overflow: auto;}

       .chat-message{margin:10px 0px;}
       .chat-message.mine{display: flex; justify-content:flex-end;}
       
      .chat-message .send-message{
         padding: 5px 7px;
         border-radius: 10px;
         max-width: 190px;
         font-size:0.9em;
         white-space: pre-line;
      }
      .chat-message.other .send-message{background: lightgray;}
      .chat-message.mine .send-message{background: #FFE08C;}
   
      .chat-user {
         text-align:center; 
         border-radius:10px;   
         background: lightgray;
         opacity: 0.5;
         margin: 20px 0px;
         color: black;
         line-height: 30px;
      }
</style>
</head>
<body>
	
	<jsp:include page="../common/header.jsp"/>

   <div class="chat">
   
      <div class="chat-area">
      
      	 <!-- 
         <div class="chat-message mine">
            <div class="send-message">내가보낸 메세지내가보낸 메세지내가보낸 메세지내가보낸 메세지내가보낸 메세지</div>
         </div>   
         
         <div class="chat-message other">
            <span class="send-user">상대방</span>
            <div class="send-message">남이보낸 메세지남이보낸 메세지남이보낸 메세지남이보낸 메세지남이보낸 메세지남이보낸 메세지</div>
         </div>
         
         <div class="chat-user entry">
            xxx님이 들어왔습니다.
         </div>
         
         <div class="chat-user exit">
            xxx님이 나갔습니다.
         </div>   
         -->      
         
      </div>
      
      <div class="input-area">
      
         <div class="form-group">
             <textarea class="form-control" rows="3" id="message" style="resize:none"></textarea>
         </div>
         
         <button type="button" class="btn btn-sm btn-secondary btn-block" onclick="sendMessage();">전송하기</button>
         <button type="button" class="btn btn-sm btn-danger btn-block" onclick="onClose();">퇴장하기</button>
         
      </div>   
      
   </div>
   
   <script src="https://cdn.jsdelivr.net/sockjs/1/sockjs.min.js"></script>
   <script>
   
   		const $chatArea = $(".chat-area"); // 메세지들이 쌓이는 div요소객체 
   		
   		const sock = new SockJS("${pageContext.request.contextPath}/chat"); // * 웹소켓 서버와 연결됨 (== 웹소켓 접속 => ChatEchoHandler 클래스의 afterConnectionEstablished 메소드 실행됨) 
   		sock.onmessage = onMessage; // 웹소켓에서 해당 클라이언트 측으로 메세지를 보내면 자동으로 실행될 함수 정의 
   		sock.onclose = onClose; // 웹소켓과 연결이 끊겨졌을때 실행하는 메소드 
   		
   		function sendMessage(){ // 전송하기 버튼 클릭시 실행되는 함수 
   			sock.send($("#message").val()); // * websocket으로 메세지 전달 => ChatEchoHandler의 handleMessage 메소드 실행됨 
   			$("#message").val(""); 
   		}
   		
   		function onMessage(evt){ // 나에게 온 메세지가 있을 경우 실행되는 함수 
   			// evt : 웹소켓측에서 해당 클라이언트로 보내준 데이터 
   			//console.log(evt.data); // 메세지 문구 ("메시지유형(채팅|입장|퇴장)|메세지내용|발신자이름|발신자아이디")
   			
   			let msgArr = evt.data.split("|"); // ["메세지유형", "메세지내용", "발신자이름", "발신자아이디"] 
   			
   			 // 동적으로 메세지 div 요소 만들어서 $chatArea요소에 추가
            let $chatDiv; // 채팅방영역에 추가할 요소
            
            if(msgArr[0] == "입장"){
               	$chatDiv = $("<div class='chat-user entry'>").text(msgArr[1]);
            }else if(msgArr[0] == "퇴장"){
            	$chatDiv = $("<div class='chat-user exit'>").text(msgArr[1]);
            }else if(msgArr[0] == "채팅"){
               
               	let $msgDiv = $("<div class='send-message'>").text(msgArr[1]); // 채팅메세지를 담은 .send-message요소 
               	let $userSpan = $("<span class='send-user'>").text(msgArr[2]); // 발신자를 담은 .send-user요소 
               
               	$chatDiv = $("<div class='chat-message'>").append($msgDiv); 
               	
               	// 내가 작성한 메세지일 경우 
               	if(msgArr[3] == "${loginUser.userId}"){
               		$chatDiv.addClass("mine"); 
               	}else{ // 남이 작성한 메세지일 경우 
               		$chatDiv.addClass("other"); 
               		$chatDiv.prepend($userSpan); 
               	}
            }
            
            $chatArea.append($chatDiv); 
   			
   		}
   		
   		function onClose(){ // 퇴장하기 클릭시 실행되는 함수 
   			location.href= "${pageContext.request.contextPath}"; 
   			// * 웹소켓과의 통신도 끊기게 됨 => ChatEchoHandler클래스의 afterConnectionClosed메소드 실행됨 
   		}
   		
   </script>
   
   <jsp:include page="../common/footer.jsp"/>
	
</body>
</html>