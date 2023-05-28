package com.KoreaIT.smw.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.KoreaIT.smw.demo.repository.ChatRepository;
import com.KoreaIT.smw.demo.vo.Chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

	// WebSocket을 통해 클라이언트로 메시지를 보낼 수 있는 기능
	@Autowired
	private SimpMessageSendingOperations template; 

	@Autowired
	private ChatRepository chatRepository;

	// 채팅방에 사용자가 입장할 때 호출
	public void enterUser(Chat chat, SimpMessageHeaderAccessor headerAccessor) {
		
		// 사용자의 회원 id가 해당 채팅방에 중복되지 않는지 확인
		boolean isMemberIdUnique = isMemberIdUnique(chat.getRoomId(), chat.getMemberId(), chat.getRoomType());

		if (!isMemberIdUnique) {
			return;
		}
		
		// 사용자를 채팅방에 추가
		chatRepository.addUser(chat.getRoomId(), chat.getMemberId(), chat.getRoomType());
		
		int memberId = chat.getMemberId();
		
		// 세션에 memberId, roomId, roomType이라는 이름으로 값을 설정
		headerAccessor.getSessionAttributes().put("memberId", memberId);
		headerAccessor.getSessionAttributes().put("roomId", chat.getRoomId());
		headerAccessor.getSessionAttributes().put("roomType", chat.getRoomType());

		chat.setMessage(chat.getSender() + " 님 입장!!");
		
		// 입장에 관련 채팅 내용 DB에 저장
		chatRepository.saveChat(chat.getType(), chat.getRoomId(), chat.getSender(), chat.getMemberId(),
				chat.getMessage(), chat.getTime(), chat.getRoomType());
		
		int lastInsertId = chatRepository.getLastInsertId();
				
		// 마지막으로 읽은 채팅 업데이트
		chatRepository.updateLastReadChatId(chat.getMemberId(), chat.getRoomId(), chat.getRoomType(), lastInsertId);
		
		// 서버에서 클라이언트로 아래와 같은 주제를 가진 채팅방에 chat 객체를 보냄
		template.convertAndSend("/sub/chat/room/" + chat.getRoomId(), chat);
	}

	// 채팅을 보냈을 시 호출
	public void sendMessage(Chat chat) {
		log.info("CHAT {}", chat);
		chat.setMessage(chat.getMessage());
		template.convertAndSend("/sub/chat/room/" + chat.getRoomId(), chat);

		// ChatRepository를 통해 메시지 정보를 DB에 저장
		chatRepository.saveChat(chat.getType(), chat.getRoomId(), chat.getSender(), chat.getMemberId(),
				chat.getMessage(), chat.getTime(), chat.getRoomType());
		
		// 채팅을 보낸 id값 가져오기
		int lastInsertId = chatRepository.getLastInsertId();
		
		// 마지막으로 읽은 채팅 업데이트
		chatRepository.updateLastReadChatId(chat.getMemberId(), chat.getRoomId(), chat.getRoomType(), lastInsertId);

	}

	// SessionDisconnectEvent의 이벤트가 발생시 호출
	@EventListener
	public void handleDisconnectEvent(SessionDisconnectEvent event) {
		log.info("SessionDisconnectEvent occurred.");
		log.info("DisConnEvent {}", event);

		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

		// 세션에 memberId, roomId, roomType인 속성의 값을 불러옴
		int memberId = (int) headerAccessor.getSessionAttributes().get("memberId");
		int roomId = (int) headerAccessor.getSessionAttributes().get("roomId");
		String roomType = (String) headerAccessor.getSessionAttributes().get("roomType");

		log.info("headAccessor {}", headerAccessor);

		// 해당 채팅방id와 회원id와 roomType이 일치하는 사용자의 이름 가져오기
		String username = chatRepository.getUserName(roomId, memberId, roomType);

		if (username != null) {
			log.info("User Disconnected: " + username);
			
			// Chat 클래스에 있는 builder 패턴을 사용하여 설정된 속성들을 가진 Chat 객체 생성
			Chat chat = Chat.builder().type(Chat.MessageType.LEAVE).sender(username).message(username + " 님 퇴장!!")
					.roomId(roomId).memberId(memberId).roomType(roomType).build();

			// 퇴장에 관련 채팅 내용 DB에 저장
			chatRepository.saveChat(chat.getType(), chat.getRoomId(), chat.getSender(), chat.getMemberId(),
					chat.getMessage(), chat.getTime(), chat.getRoomType());
			
			// 채팅방에서 해당 유저 삭제
			chatRepository.delUser(roomId, memberId, roomType);

			template.convertAndSend("/sub/chat/room/" + roomId, chat);
		}
	}

	// 해당 채팅방의 회원 리스트 가져오기
	public List<String> getUserList(int roomId, String roomType) {
		return chatRepository.getUserList(roomId, roomType);
	}

	// 해당 채팅방에서 memberId가 중복되는지 확인
	public boolean isMemberIdUnique(int roomId, int memberId, String roomType) {

		boolean isUnique = chatRepository.getChat_UserByRoomIdAndMemberId(roomId, memberId, roomType) == null;

		return isUnique;
	}

	// 해당 채팅방의 채팅내역 가져오기
	public List<Chat> getChatHistory(int roomId, String roomType) {

		return chatRepository.getChatHistory(roomId, roomType);
	}

	// 해당 개인채팅방의 읽지 않은 채팅 수 가져오기
	public int getPersonalChatUnreadCount(int roomId, int memberId, String roomType, int lastReadId) {
		return chatRepository.getPersonalChatUnreadCount(roomId, memberId, roomType, lastReadId);
	}
	
	// 해당 동호회 채팅방의 읽지 않은 채팅 수 가져오기
	public int getClubChatUnreadCount(int roomId, int memberId, String roomType, int lastReadId) {
		return chatRepository.getClubChatUnreadCount(roomId, memberId, roomType, lastReadId);
	}

	// 해당 채팅방에서 해당 회원이 마지막으로 채팅을 읽은 곳 가져오기
	public int getLastReadId(int roomId, int memberId, String roomType) {
		return chatRepository.getLastReadId(roomId, memberId, roomType);
	}

}
