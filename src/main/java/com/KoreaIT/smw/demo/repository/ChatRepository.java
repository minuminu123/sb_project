package com.KoreaIT.smw.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.KoreaIT.smw.demo.vo.Chat;
import com.KoreaIT.smw.demo.vo.Chat.MessageType;
import com.KoreaIT.smw.demo.vo.Chat_User;


@Mapper
public interface ChatRepository {

    public void createClubChatRoom(String roomName, int memberId, int clubId);

    public void addUser(int roomId, int memberId, String roomType);

    public void delUser(int roomId, int memberId, String roomType);

    public String getUserName(int roomId, int memberId, String roomType);

    public List<String> getUserList(int roomId, String roomType);

    public void saveChat(MessageType type, int roomId, String sender, int memberId, String message, String time, String roomType);

	public int getLastInsertId();

	public Chat_User getChat_UserByRoomIdAndMemberId(int roomId, int memberId, String roomType);

	public List<Chat> getChatHistory(int roomId, String roomType);

	public void updateLastReadChatId(int memberId, int roomId, String roomType, int lastReadId);

	public int getPersonalChatUnreadCount(int roomId, int memberId, String roomType, int lastReadId);
	
	public int getClubChatUnreadCount(int roomId, int memberId, String roomType, int lastReadId);

	public int getLastReadId(int roomId, int memberId, String roomType);
}