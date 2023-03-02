package com.br.spring.chat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ChatController {
	
	@RequestMapping("chattingRoom.do")
	public String chattingRoomEntry() {
		return "chat/chattingRoom"; 
	}
}
