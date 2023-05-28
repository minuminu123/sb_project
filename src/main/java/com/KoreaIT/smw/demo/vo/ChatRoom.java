package com.KoreaIT.smw.demo.vo;

import lombok.Data;

@Data
public class ChatRoom {
    private int id; // 채팅방 아이디
    private String roomName; // 채팅방 이름
    private int memberId; // 채팅방 생성한 사람
    
    private long userCount; // 채팅방 인원수

}