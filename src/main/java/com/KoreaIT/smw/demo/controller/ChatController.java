package com.KoreaIT.smw.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.KoreaIT.smw.demo.service.ChatService;
import com.KoreaIT.smw.demo.vo.Chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {
    
    @Autowired
    private ChatService chatService;

    // MessageMapping 을 통해 클라이언트로부터의 메시지를 처리하는 핸들러 메서드를 지정
    @MessageMapping("/chat/enterUser")
    public void enterUser(@Payload Chat chat, SimpMessageHeaderAccessor headerAccessor) {
    	
        chatService.enterUser(chat, headerAccessor);
    }
    
    // 페이로드 : 데이터 전송 프로토콜에서 실제로 전송되는 데이터
    // 아래 경로로 들어오는 WebSocket 메시지의 페이로드를 Chat 객체로 수신
    @MessageMapping("/chat/sendMessage")
    public void sendMessage(@Payload Chat chat) {
        log.info("CHAT {}", chat);
        
        chatService.sendMessage(chat);
    }
    
    // WebSocket 연결 종료(SessionDisconnectEvent) 이벤트를 처리하는 메소드
    @EventListener
    public void webSocketDisconnectListener(SessionDisconnectEvent event) {
        chatService.handleDisconnectEvent(event);
    }

    // 유저 목록을 받아오는 메소드
    @GetMapping("/chat/userlist")
    @ResponseBody
    public List<String> userList(int roomId, String roomType) {
        return chatService.getUserList(roomId, roomType);
    }
    
    // 채팅내역을 받아오는 메소드
    @GetMapping("/chat/chatHistory")
    @ResponseBody
    public List<Chat> getChatHistory(int roomId, String roomType) {
        return chatService.getChatHistory(roomId, roomType);
    }
}