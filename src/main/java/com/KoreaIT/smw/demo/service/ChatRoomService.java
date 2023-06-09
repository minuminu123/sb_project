package com.KoreaIT.smw.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.KoreaIT.smw.demo.repository.ChatRepository;
import com.KoreaIT.smw.demo.vo.ChatRoom;

@Service
public class ChatRoomService {

    private ChatRepository chatRepository;

    public ChatRoomService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public List<ChatRoom> getRooms() {
        return chatRepository.getRooms();
    }

    public ChatRoom getRoomById(int id) {
        return chatRepository.getRoomById(id);
    }
}