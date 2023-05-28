package com.KoreaIT.smw.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.KoreaIT.smw.demo.repository.ChatRepository;
import com.KoreaIT.smw.demo.vo.ClubChatRoom;
import com.KoreaIT.smw.demo.vo.PersonalChatRoom;

@Service
public class ChatRoomService {

	private ChatRepository chatRepository;

	public ChatRoomService(ChatRepository chatRepository) {
		this.chatRepository = chatRepository;
	}

	// 동호회 채팅방 리스트 가져오기
	public List<ClubChatRoom> getRooms() {
		return chatRepository.getRooms();
	}

	// 동호회 채팅방 생성 
	public ClubChatRoom createClubChatRoom(String roomName, int memberId, int clubId) {
		chatRepository.createClubChatRoom(roomName, memberId, clubId);
		
		int id = chatRepository.getLastInsertId();

		ClubChatRoom room = chatRepository.getClubChatRoomById(id);

		return room;
	}

	// 채팅방의 id에 해당되는 동호회 채팅방 가져오기
	public ClubChatRoom getClubChatRoomById(int id) {
		return chatRepository.getClubChatRoomById(id);
	}

	// 채팅방의 id에 해당되는 개인 채팅방 가져오기
	public PersonalChatRoom getPersonalChatRoomById(int id) {
		return chatRepository.getPersonalChatRoomById(id);
	}

	// 개인 채팅방 생성
	public PersonalChatRoom createPersonalChatRoom(int memberId1, int memberId2) {
		chatRepository.createPersonalChatRoom(memberId1, memberId2);

		int id = chatRepository.getLastInsertId();

		PersonalChatRoom room = chatRepository.getPersonalChatRoomById(id);

		return room;
	}

	// memberId1과 memberId2에 해당되는 개인채팅방 가져오기
	public PersonalChatRoom getPersonalChatRoomByMemberId(int memberId1, int memberId2) {
		return chatRepository.getPersonalChatRoomByMemberId(memberId1, memberId2);
	}

	// memberId가 해당되어있는 동호회 채팅방 리스트 가져오기
	public List<ClubChatRoom> getClubChatRoomsByMemberId(int memberId) {
		return chatRepository.getClubChatRoomsByMemberId(memberId);
	}

	// memberId가 해당되어있는 개인 채팅방 리스트 가져오기
	public List<PersonalChatRoom> getPersonalChatRoomsByMemberId(int memberId) {
		return chatRepository.getPersonalChatRoomsByMemberId(memberId);
	}
}