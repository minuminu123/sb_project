package com.KoreaIT.smw.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Chat {
	
    public enum MessageType{
        ENTER, TALK, LEAVE;
    }

    private int id; // 메시지 타입
    private MessageType type; // 메시지 타입
    private int roomId; // 방 번호
    private String sender; // 채팅을 보낸 사람
    private int memberId; // 채팅을 보낸 사람
    private String message; // 메시지
    private String time; // 채팅 발송 시간간
    private String roomType; // 채팅방 타입
}