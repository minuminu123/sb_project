package com.KoreaIT.smw.demo.vo;

import lombok.Data;

@Data
public class PersonalChatRoom {
    private int id; // 채팅방 아이디
    private String regDate; // 채팅방 생성 날짜
    private String updateDate; // 채팅방 수정 날짜
    private int memberId1; // 채팅방 사람1
    private int memberId2; // 채팅방 사람2
    private int unreadCount; // 안읽은 채팅 수
    
    private String member1_name;
    private String member2_name;
}
