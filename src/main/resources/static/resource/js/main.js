// 코드 작성의 실수를 예방하고 JavaScript 엔진의 최적화를 통해 코드 실행 속도를 향상
'use strict';

document.write("<script\n" +
	"  src=\"https://code.jquery.com/jquery-3.6.1.min.js\"\n" +
	"  integrity=\"sha256-o88AwQnZB+VDvE9tvIXrMQaPlFFSUTR+nldQm1LuPXQ=\"\n" +
	"  crossorigin=\"anonymous\"></script>")

var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');

var stompClient = null;
var username = null;
var memberId = 0;
var roomType = null;

var colors = [
	'#2196F3', '#32c787', '#00BCD4', '#ff5652',
	'#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

// roomId 파라미터 가져오기
const url = new URL(location.href).searchParams;
const id = url.get('id');

// 연결 시도(채팅방 입장 전 화면)
function connect(event) {
	username = document.querySelector('#name').value.trim();
	memberId = document.querySelector('#memberId').value.trim();
	roomType = document.querySelector('#roomType').value.trim();

	usernamePage.classList.add('hidden');
	chatPage.classList.remove('hidden');

	// SockJS 객체를 생성하고, '/ws-stomp' 엔드포인트에 대한 WebSocket 연결을 설정
	var socket = new SockJS('/ws-stomp');
	
	// WebSocket을 사용하여 STOMP 클라이언트를 생성하는 메서드
	stompClient = Stomp.over(socket);

	// 클라이언트를 사용하여 서버와의 연결
	stompClient.connect({}, onConnected, onError);

	event.preventDefault();
}

// 연결 됐을때(채팅방 입장시)
function onConnected() {
	
	// 해당 채팅방 구독 설정, 메시지 도착 시 onMessageReceived 함수 실행
	stompClient.subscribe('/sub/chat/room/' + id, onMessageReceived);

	// 사용자가 채팅방에 입장하는 메시지를 서버로 전송(JSON 형식)
	stompClient.send("/pub/chat/enterUser",
		{},
		JSON.stringify({
			"roomId": id,
			"sender": username,
			"memberId": memberId,
			"type": 'ENTER',
			"roomType": roomType
		})
	)

	// 해당 채팅방의 채팅 내역 불러오기
	getChatHistory(id);

	connectingElement.classList.add('hidden');

}

// 유저 리스트 받기
function getUserList() {
	const $list = $("#list");

	$.ajax({
		type: "GET",
		url: "/chat/userlist",
		data: {
			"roomId": id,
			roomType: roomType
		},
		success: function(data) {
			var users = "";
			for (let i = 0; i < data.length; i++) {

				users += "<li class='dropdown-item'>" + data[i] + "</li>"
			}
			$list.html(users);
		}
	})
}

// 채팅 내역을 AJAX를 활용하여 서버에서 받아온 후 받아온 채팅 내역을 handleChatHistory() 함수를 호출하여 처리
function getChatHistory(id) {
	$.ajax({
		url: '/chat/chatHistory',
		method: 'GET',
		data: {
			"roomId": id,
			"roomType": roomType
		},
		success: function(data) {
			console.log(data);
			handleChatHistory(data);
		},
		error: function(error) {
			console.log('Error:', error);
		}
	});
}

// 받아온 채팅 내역을 채팅방 형식에 맞게 변환
function handleChatHistory(chatHistory) {
	var messageArea = document.querySelector('#messageArea');
	messageArea.innerHTML = ''; // 기존 채팅 내역을 초기화

	for (var i = 0; i < chatHistory.length; i++) {
		var chat = chatHistory[i];
		var messageElement = document.createElement('li');

		messageElement.classList.add('chat-message');

		// 아이콘
		var avatarElement = document.createElement('i');
		var avatarText = document.createTextNode(chat.sender[0]);
		avatarElement.appendChild(avatarText);
		avatarElement.style['background-color'] = getAvatarColor(chat.sender);

		messageElement.appendChild(avatarElement);

		// 회원 이름
		var usernameElement = document.createElement('span');
		var usernameText = document.createTextNode(chat.sender);
		usernameElement.appendChild(usernameText);
		messageElement.appendChild(usernameElement);

		// 채팅 내용
		var textElement = document.createElement('p');
		var messageText = document.createTextNode(chat.message);
		textElement.appendChild(messageText);
		messageElement.appendChild(textElement);

		// 내가 보낸 채팅과 상대방이 보낸 채팅 구분
		if (parseInt(chat.memberId) === parseInt(memberId)) {
			messageElement.classList.add('own-message');
		} else {
			messageElement.classList.add('other-message');
		}

		messageArea.appendChild(messageElement);
	}

	// 스크롤을 아래로 이동하여 가장 최근의 메시지가 보이도록
	messageArea.scrollTop = messageArea.scrollHeight;
}

// 에러시 호출
function onError(error) {
	connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
	connectingElement.style.color = 'red';
}

// 채팅을 messageContent 객체 생성 후 JSON 형식으로 변환하여 데이터를 담아서 서버로 보냄
function sendMessage(event) {
	var messageContent = messageInput.value.trim();

	if (messageContent && stompClient) {
		var chatMessage = {
			"roomId": id,
			"sender": username,
			"memberId": memberId,
			"message": messageInput.value,
			"type": 'TALK',
			"roomType": roomType
		};

		stompClient.send("/pub/chat/sendMessage", {}, JSON.stringify(chatMessage));
		messageInput.value = '';
	}
	event.preventDefault();
}

// 메시지를 수신할 때마다 호출
function onMessageReceived(payload) {
	
	// 수신된 메시지의 내용인 payload.body를 JSON 형식으로 파싱하여 JavaScript 객체로 변환
	var chat = JSON.parse(payload.body);

	var messageElement = document.createElement('li');

	if (chat.type === 'ENTER') {
		messageElement.classList.add('event-message');
		chat.content = chat.sender + chat.message;
		getUserList();

	} else if (chat.type === 'LEAVE') {
		messageElement.classList.add('event-message');
		chat.content = chat.sender + chat.message;
		getUserList();

	} else {
		messageElement.classList.add('chat-message');

		var avatarElement = document.createElement('i');
		var avatarText = document.createTextNode(chat.sender[0]);
		avatarElement.appendChild(avatarText);
		avatarElement.style['background-color'] = getAvatarColor(chat.sender);

		messageElement.appendChild(avatarElement);

		var usernameElement = document.createElement('span');
		var usernameText = document.createTextNode(chat.sender);
		usernameElement.appendChild(usernameText);
		messageElement.appendChild(usernameElement);
	}

	var textElement = document.createElement('p');
	var messageText = document.createTextNode(chat.message);
	textElement.appendChild(messageText);

	if (parseInt(chat.memberId) === parseInt(memberId)) {
		messageElement.classList.add('own-message');
	} else {
		console.log(memberId + '+' + chat.memberId);
		messageElement.classList.add('other-message');
	}

	messageElement.appendChild(textElement);

	messageArea.appendChild(messageElement);
	
	// 스크롤을 아래로 이동하여 가장 최근의 메시지가 보이도록
	messageArea.scrollTop = messageArea.scrollHeight;
}

// 발신자의 문자열을 해시 값으로 변환하여 그에 따른 고유한 아바타 색상을 반환
function getAvatarColor(messageSender) {
	var hash = 0;
	for (var i = 0; i < messageSender.length; i++) {
		hash = 31 * hash + messageSender.charCodeAt(i);
	}

	var index = Math.abs(hash % colors.length);
	return colors[index];
}

// 폼 제출 이벤트를 감지하여 해당하는 함수를 실행
usernameForm.addEventListener('submit', connect, true)
messageForm.addEventListener('submit', sendMessage, true)