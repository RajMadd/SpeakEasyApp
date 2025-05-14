let stompClient = null;

function connectWebSocket() {
  const socket = new SockJS("http://localhost:8080/ws");
  stompClient = Stomp.over(socket);

  stompClient.connect({}, function (frame) {
    console.log("Connected: " + frame);

    stompClient.subscribe("/topic/group/1", function (message) {
      const msg = JSON.parse(message.body);
      const messagesDiv = document.getElementById("messages");
      messagesDiv.innerHTML += `<div>${msg.sender}: ${msg.content}</div>`;
    });
  });
}

function sendMessage() {
  const content = document.getElementById("chatInput").value;
  const messageObject = {
    sender: "You", // Replace with actual username
    content: content,
    groupId: 1
  };
  stompClient.send("/app/chat.send", {}, JSON.stringify(messageObject));
  document.getElementById("chatInput").value = "";
}
