package com.KoreaIT.smw.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.KoreaIT.smw.demo.vo.Chat;
import com.KoreaIT.smw.demo.vo.Chat.MessageType;
import com.KoreaIT.smw.demo.vo.Chat_User;
import com.KoreaIT.smw.demo.vo.ClubChatRoom;
import com.KoreaIT.smw.demo.vo.PersonalChatRoom;


@Mapper
public interface ChatRepository {

	public List<ClubChatRoom> getRooms();

    public ClubChatRoom getClubChatRoomById(int id);

    public void createClubChatRoom(String roomName, int memberId, int clubId);

    public void addUser(int roomId, int memberId, String roomType);

    public void delUser(int roomId, int memberId, String roomType);

    public String getUserName(int roomId, int memberId, String roomType);

    public List<String> getUserList(int roomId, String roomType);

    public void saveChat(MessageType type, int roomId, String sender, int memberId, String message, String time, String roomType);

	public int getLastInsertId();

	public Chat_User getChat_UserByRoomIdAndMemberId(int roomId, int memberId, String roomType);

	public List<Chat> getChatHistory(int roomId, String roomType);

	public void createPersonalChatRoom(int memberId1, int memberId2);

	public PersonalChatRoom getPersonalChatRoomById(int id);

	public PersonalChatRoom getPersonalChatRoomByMemberId(int memberId1, int memberId2);

	public List<ClubChatRoom> getClubChatRoomsByMemberId(int memberId);

	public List<PersonalChatRoom> getPersonalChatRoomsByMemberId(int memberId);

	public void updateLastReadChatId(int memberId, int roomId, String roomType, int lastReadId);

	public int getPersonalChatUnreadCount(int roomId, int memberId, String roomType, int lastReadId);
	
	public int getClubChatUnreadCount(int roomId, int memberId, String roomType, int lastReadId);

	public int getLastReadId(int roomId, int memberId, String roomType);
}