package com.KoreaIT.smw.demo.vo;

import lombok.Data;

@Data
public class ClubChatRoom {
    private int id; // 채팅방 아이디
    private String regDate; // 채팅방 생성 날짜
    private String updateDate; // 채팅방 수정 날짜
    private String roomName; // 채팅방 이름
    private int memberId; // 채팅방 생성한 사람
    private int clubId; // 동호회 번호
    
    private long userCount; // 채팅방 인원수
    private int unreadCount; // 안읽은 채팅 수

}
