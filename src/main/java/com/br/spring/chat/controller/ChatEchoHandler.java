package com.br.spring.chat.controller;

import java.util.ArrayList;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.br.spring.member.model.vo.Member;


public class ChatEchoHandler extends TextWebSocketHandler {
	
	
	// 웹소켓 세션 객체(클라이언트)들을 저장해두는 리스트  
	private ArrayList<WebSocketSession> sessionList = new ArrayList<>(); 
	
	// 해당 웹소켓에 새로운 클라이언트와 연결이 되었을 때 실행할 내용 정의 
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// WebSocketSession 객체 : 현재 해당 웹소켓과 연결된 클라이언트측 객체 (즉, 채팅방에 접속한 사용자)
		
		// * 현재 웹소켓과 잘 연결되는지 확인 
		System.out.println("\n=========== websocket 연결됨 ==========="); 
		System.out.println("WebsocketSession 객체 : " + session); 
		System.out.println("session Id : " + session.getId());
		System.out.println("session Attribute : " + session.getAttributes()); // {sessionId=xxxxxxx, loginUser=Member객체}
		System.out.println("현재 채팅방에 참가한 로그인한 회원 : " + session.getAttributes().get("loginUser")); 
		
		// * 현재 입장한 클라이언트를 list에 추가 
		sessionList.add(session); 
		
		// * 현재 접속되어있는 모든 클라이언트들에게 xxx님이 입장했다는 메세지 전송 
		for(WebSocketSession sss : sessionList) {
			// 메세지유형|채팅방에띄워주고자하는메세지내용
			String msg = "입장|" + ((Member)session.getAttributes().get("loginUser")).getUserName() + "님이 입장하였습니다."; 
			sss.sendMessage(new TextMessage(msg));
		}
		
		
	}
	
	// 클라이언트 측으로부터 해당 웹소켓으로 데이터가 전달되었을 때 실행할 내용 정의 
	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		
		// WebSocketSession : 현재 해당 웹소켓으로 메세지를 전송한 클라이언트 측 객체 
		// WebSocketMessage : 현재 전달된 메시지에 대한 정보를 가지고 있는 객체 
		
		System.out.println("\n----------- 메세지 들어옴 ------------ "); 
		System.out.println("WebSocketSession객체 : " + session); 
		System.out.println("현재 메세지를 보낸 회원의 이름 : " + ((Member)session.getAttributes().get("loginUser")).getUserName()); 
		System.out.println("WebSocketMessage : " + message); 
		System.out.println("현재 전달된 메세지 : " + message.getPayload()); 
		
		// * 현재 해당 웹소켓에 연결되어있는 모든 클라이언트들에게 현재 들어온 메세지를 발송함 (작성자본인 포함)
		for(WebSocketSession sss : sessionList) { // ArrayList<WebSocketSession> sessionList == 웹소켓과 연결되어있는 클라이언트들의 집합 
			
			// 전달하고자 하는 메세지의 형식 => 메세지유형|채팅방에띄워주고하는메세지내용|발신자이름|발신자아이디|... (등등) 
			String msg = "채팅|" + message.getPayload() + "|" + ((Member)session.getAttributes().get("loginUser")).getUserName() + "|" + ((Member)session.getAttributes().get("loginUser")).getUserId(); 
			sss.sendMessage(new TextMessage(msg)); // 채팅방jsp에서 onMessage함수가 실행됨 
		}
		
	}
	
	// 클라이언트와 연결이 끊어졌을 때 실행할 내용 정의 
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		
		// * 클라이언트와의 연결이 잘 끊겼는지 확인 
		
		System.out.println("\n******** websocket 연결끊김 ********"); 
		System.out.println("WebSocketSession 객체 : " + session); 
		System.out.println("session Id : " + session.getId()); 
		System.out.println("현재 채딩방에서 나간 회원 : " + session.getAttributes().get("loginUser")); 
		
		// * 현재 나가진 WebsocketSession객체 List로부터 지우기 
		sessionList.remove(session);  
		
		for(WebSocketSession sss : sessionList) {
			String msg = "퇴장|" + ((Member)session.getAttributes().get("loginUser")).getUserName();
			sss.sendMessage(new TextMessage(msg));
		}
		
	}
	
}
