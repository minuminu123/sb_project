package com.KoreaIT.smw.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

    // StompHeaderAccessor [headers={simpMessageType=MESSAGE, stompCommand=SEND, nativeHeaders={destination=[/pub/chat/enterUser], content-length=[61]}, simpSessionAttributes={}, simpHeartbeat=[J@659a17ed, lookupDestination=/chat/enterUser, simpSessionId=kn10dphs, simpDestination=/pub/chat/enterUser}
    @MessageMapping("/chat/enterUser")
    public void enterUser(@Payload Chat chat, SimpMessageHeaderAccessor headerAccessor) {
    	System.out.println(chat);
    	System.out.println(headerAccessor + "====================================================");
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
    @GetMapping("/chat/userList")
    @ResponseBody
    public List<String> userList(int roomId) {
        return chatService.getUserList(roomId);
    }
    
    // 채팅내역을 받아오는 메소드
    @GetMapping("/chat/chatHistory")
    @ResponseBody
    public List<Chat> getChatHistory(@RequestParam("roomId") int roomId) {
        return chatService.getChatHistory(roomId);
    }
}