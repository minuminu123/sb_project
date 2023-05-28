package com.KoreaIT.smw.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.KoreaIT.smw.demo.service.ChatRoomService;
import com.KoreaIT.smw.demo.service.ChatService;
import com.KoreaIT.smw.demo.service.ClubService;
import com.KoreaIT.smw.demo.vo.ClubChatRoom;
import com.KoreaIT.smw.demo.vo.PersonalChatRoom;
import com.KoreaIT.smw.demo.vo.Rq;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ChatRoomController {

	@Autowired
	private ChatRoomService chatRoomService;

	@Autowired
	private ClubService clubService;

	@Autowired
	private ChatService chatService;

	@Autowired
	private Rq rq;

	// 채팅 리스트 화면
	@RequestMapping("/usr/chat/list")
	public String ShowChatRoomList(Model model) {
		if (rq.getLoginedMemberId() == 0) {
			return rq.jsHistoryBackOnView("F-L", "로그인 후 이용해주세요.");
		}

		// 해당 memberId가 속하는 개인 채팅방 가져오기
		List<PersonalChatRoom> PList = chatRoomService.getPersonalChatRoomsByMemberId(rq.getLoginedMemberId());
		int PunReadCount = 0;

		// 개인채팅방에서 상대방의 이름과 읽지 않은 채팅 수를 가져오기 위한 반복문
		for (PersonalChatRoom room : PList) {
			if (room.getMemberId1() == rq.getLoginedMemberId()) {
				int tmp1 = room.getMemberId1();
				room.setMemberId1(room.getMemberId2());
				room.setMemberId2(tmp1);

				String tmp2 = room.getMember1_name();
				room.setMember1_name(room.getMember2_name());
				room.setMember2_name(tmp2);
			}

			String roomType = "Personal";

			int lastReadId = chatService.getLastReadId(room.getId(), rq.getLoginedMemberId(), roomType);

			int unreadCount = chatService.getPersonalChatUnreadCount(room.getId(), rq.getLoginedMemberId(), roomType,
					lastReadId);

			room.setUnreadCount(unreadCount);
			
			PunReadCount += unreadCount;

		}
		
		rq.setPunReadCount(PunReadCount);

		model.addAttribute("PList", PList);

		// 해당 memberId가 속하는 동호회 채팅방 가져오기
		List<ClubChatRoom> CList = chatRoomService.getClubChatRoomsByMemberId(rq.getLoginedMemberId());
		
		int CunReadCount = 0;

		// 동호회 채팅방에서 읽지 않은 채팅의 수를 가져오는 것
		for (ClubChatRoom room : CList) {
			String roomType = "Club";

			int lastReadId = chatService.getLastReadId(room.getId(), rq.getLoginedMemberId(), roomType);

			int unreadCount = chatService.getClubChatUnreadCount(room.getId(), rq.getLoginedMemberId(), roomType,
					lastReadId);

			room.setUnreadCount(unreadCount);
			
			CunReadCount += unreadCount;
		}
		
		rq.setCunReadCount(CunReadCount);

		model.addAttribute("CList", CList);

		log.info("SHOW ALL ChatList {}", chatRoomService.getPersonalChatRoomsByMemberId(rq.getLoginedMemberId()));
		log.info("SHOW ALL ChatList {}", chatRoomService.getClubChatRoomsByMemberId(rq.getLoginedMemberId()));

		return "usr/chat/chatlist";
	}

	// 동호회 채팅방 생성
	@RequestMapping("/usr/chat/createClubChatroom")
	public String createClubChatRoom(String roomName, int memberId, int clubId) {
		ClubChatRoom room = chatRoomService.createClubChatRoom(roomName, memberId, clubId);

		log.info("CREATE Chat Room {}", room);

		return "redirect:/usr/chat/list";
	}

	// 개인 채팅방 생성
	@RequestMapping("/usr/chat/createPersonalChatroom")
	public String createPersonalChatRoom(int memberId1) {
		if (memberId1 == rq.getLoginedMemberId()) {
			return rq.jsHistoryBackOnView("F-1", "해당 기능은 사용할 수 없습니다.");
		}

		// 개인 채팅방중에 memberId1과 memberId2를 포함하는 채팅방이 있는지 확인
		PersonalChatRoom isExistRoom = chatRoomService.getPersonalChatRoomByMemberId(memberId1,
				rq.getLoginedMemberId());

		// 중복되는 방이 있다면 그 채팅방으로 리턴
		if (isExistRoom != null) {
			return "redirect:/usr/chat/PersonalChatroom?id=" + isExistRoom.getId();
		}

		PersonalChatRoom room = chatRoomService.createPersonalChatRoom(memberId1, rq.getLoginedMemberId());

		log.info("CREATE Chat Room {}", room);
		return "redirect:/usr/chat/PersonalChatroom?id=" + room.getId();
	}

	// 동호회 채팅방 입장 화면
	@RequestMapping("/usr/chat/ClubChatroom")
	public String ClubChatRoomDetail(Model model, int id) {

		// 로그인한 사람이 해당 동호회에 가입이 되어있어 채팅이 가능한지 체크
		Boolean actorCanChat = clubService.actorCanChat(rq.getLoginedMemberId(), id);

		if (actorCanChat == false) {
			return rq.jsHistoryBackOnView("F-1", "해당 동호회에 가입 후 이용해주세요.");
		}

		log.info("id {}", id);
		model.addAttribute("room", chatRoomService.getClubChatRoomById(id));
		model.addAttribute("roomType", "Club");
		return "usr/chat/chatroom";
	}

	// 개인 채팅방 입장 화면
	@RequestMapping("/usr/chat/PersonalChatroom")
	public String PersonalChatRoomDetail(Model model, int id) {

		log.info("id {}", id);
		PersonalChatRoom room = chatRoomService.getPersonalChatRoomById(id);

		// 개인채팅방에서 상대방의 이름과 로그인한 사람의 이름을 가져오는 코드
		if (room.getMemberId1() == rq.getLoginedMemberId()) {
			int tmp1 = room.getMemberId1();
			room.setMemberId1(room.getMemberId2());
			room.setMemberId2(tmp1);

			String tmp2 = room.getMember1_name();
			room.setMember1_name(room.getMember2_name());
			room.setMember2_name(tmp2);
		}

		model.addAttribute("room", room);
		model.addAttribute("roomType", "Personal");
		return "usr/chat/chatroom";
	}
}
