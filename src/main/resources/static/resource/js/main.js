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

var colors = [
	'#2196F3', '#32c787', '#00BCD4', '#ff5652',
	'#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

// roomId 파라미터 가져오기
const url = new URL(location.href).searchParams;
const id = url.get('id');

function connect(event) {
	username = document.querySelector('#nickname').value.trim();
	memberId = document.querySelector('#memberId').value.trim();

	usernamePage.classList.add('hidden');
	chatPage.classList.remove('hidden');

	// 연결하고자하는 Socket 의 endPoint
	var socket = new SockJS('/ws-stomp');
	stompClient = Stomp.over(socket);

	stompClient.connect({}, onConnected, onError);

	event.preventDefault();
}


function onConnected() {
	stompClient.subscribe('/sub/chat/room/' + id, onMessageReceived);

	stompClient.send("/pub/chat/enterUser",
		{},
		JSON.stringify({
			"roomId": id,
			sender: username,
			memberId: memberId,
			type: 'ENTER'
		})
	)

	getChatHistory(id);

	connectingElement.classList.add('hidden');

}

// 유저 리스트 받기
function getUserList() {
	const $list = $("#list");

	$.ajax({
		type: "GET",
		url: "/chat/userList",
		data: {
			"roomId": id
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

function getChatHistory(id) {
	// Ajax 요청을 통해 채팅 내역을 서버에서 받아온다.
	// 서버에 roomId를 함께 보내고, 받은 채팅 내역은 handleChatHistory() 함수를 호출하여 처리한다.
	$.ajax({
		url: '/chat/chatHistory',
		method: 'GET',
		data: {
			"roomId": id
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

function handleChatHistory(chatHistory) {
	var messageArea = document.querySelector('#messageArea');
	messageArea.innerHTML = '';

	for (var i = 0; i < chatHistory.length; i++) {
		var chat = chatHistory[i];
		var messageElement = document.createElement('li');

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

		var textElement = document.createElement('p');
		var messageText = document.createTextNode(chat.message);
		textElement.appendChild(messageText);
		messageElement.appendChild(textElement);

		if (parseInt(chat.memberId) === parseInt(memberId)) {
			messageElement.classList.add('own-message');
		} else {
			messageElement.classList.add('other-message');
		}

		messageArea.appendChild(messageElement);
	}

	messageArea.scrollTop = messageArea.scrollHeight;
}

function onError(error) {
	connectingElement.textContent = '웹 소켓 서버와 연결할 수 없습니다. 잠시후 다시 이용해주세요';
	connectingElement.style.color = 'red';
}

function sendMessage(event) {
	var messageContent = messageInput.value.trim();

	if (messageContent && stompClient) {
		var chatMessage = {
			"roomId": id,
			sender: username,
			memberId: memberId,
			message: messageInput.value,
			type: 'TALK'
		};

		stompClient.send("/pub/chat/sendMessage", {}, JSON.stringify(chatMessage));
		messageInput.value = '';
	}
	event.preventDefault();
}


function onMessageReceived(payload) {
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
	messageArea.scrollTop = messageArea.scrollHeight;
}


function getAvatarColor(messageSender) {
	var hash = 0;
	for (var i = 0; i < messageSender.length; i++) {
		hash = 31 * hash + messageSender.charCodeAt(i);
	}

	var index = Math.abs(hash % colors.length);
	return colors[index];
}

usernameForm.addEventListener('submit', connect, true)
messageForm.addEventListener('submit', sendMessage, true)