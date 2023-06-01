package com.KoreaIT.smw.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.KoreaIT.smw.demo.service.ChatRoomService;
import com.KoreaIT.smw.demo.vo.Rq;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ChatRoomController {

    @Autowired
    private ChatRoomService chatRoomService;

    @Autowired
    private Rq rq;
    // 채팅방 입장 화면
    @GetMapping("/usr/chat/room")
    public String roomDetail(Model model, @RequestParam int id){
        log.info("id {}", id);
        if(id != 1) {
        	return rq.jsHitoryBackOnView("자유 채팅방만 이용 가능합니다.");
        }
        model.addAttribute("room", chatRoomService.getRoomById(id));
        return "usr/chat/chatroom";
    }
}