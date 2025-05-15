function openModal1() {
    document.getElementById('chatRoomCreateModal').style.display = 'flex';
  }

  function closeModal1() {
    document.getElementById('chatRoomCreateModal').style.display = 'none';
  }

  function createChatRoom() {
  const chatRoomName = document.getElementById('chatRoomName1').value.trim();
  const groupChat = document.getElementById('isGroupChat1').value.trim();
  const messages = document.getElementById('messages1').value.trim();

  if (!chatRoomName || groupChat === "") {
    alert("Please fill all required fields.");
    return;
  }

  //Convert isGroupChat to boolean
  const groupChatBool = groupChat.toLowerCase() === 'true';

  fetch('/chatrooms', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
      chatRoomName,
      groupChat: groupChatBool,
      messages: messages === "null" ? null : messages
    })
  })
  .then(res => {
    if (res.ok) return res.json();
    throw new Error("Failed to create chat room");
  })
  .then(data => {
    alert("Chat room created: " + data.chatRoomName + "  id:  " + data.id);
    closeModal1();
  })
  .catch(err => {
    alert("Error: " + err.message);
  });
}
  
    function openModal2() {
    document.getElementById('chatRoomUpdateModal').style.display = 'flex';
  }

  function closeModal2() {
    document.getElementById('chatRoomUpdateModal').style.display = 'none';
  }

 function updateChatRoom() {
  
  const chatRoomId = document.getElementById('chatRoomId2').value.trim();
  const chatRoomName = document.getElementById('chatRoomName2').value.trim();
  const groupChat = document.getElementById('isGroupChat2').value.trim();
  const messages = document.getElementById('messages2').value.trim();


  if (!chatRoomId || !chatRoomName || groupChat === "") {
    alert("Please fill all required fields.");
    return;
  }

  fetch(`/chatrooms/${chatRoomId}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
      chatRoomName,
      groupChat,
      messages: null
    })
  })
  .then(res => {
    if (res.ok) return res.json();
    throw new Error("Failed to update chat room");
  })
  .then(data => {
    alert("Chat room updated: " + data.chatRoomName);
    closeModal2();
  })
  .catch(err => {
    alert("Error: " + err.message);
  });
}

function openModal3() {
    document.getElementById('chatRoomDeleteModal').style.display = 'flex';
  }

  function closeModal3() {
    document.getElementById('chatRoomDeleteModal').style.display = 'none';
  }


 function deleteChatRoom() {
  const chatRoomId = document.getElementById('chatRoomId3').value.trim();

  if (!chatRoomId) {
    alert("Please fill the required fields.");
    return;
  }

  fetch(`/chatrooms/${chatRoomId}`, {
    method: 'DELETE'
  })
  .then(res => {
    if (res.ok) return res.text();
    throw new Error("Failed to delete chat room");
  })
  .then(data => {
    alert("Chat room deleted successfully");
    closeModal2();
  })
  .catch(err => {
    alert("Error: " + err.message);
  });
}


function openModal4() {
  document.getElementById('chatRoomOpenModal').style.display = 'flex';
}

function closeModal4() {
  document.getElementById('chatRoomOpenModal').style.display = 'none';
}

function openChatRoom() {
  
  const chatRoomId = document.getElementById('chatRoomId4').value.trim();

  if (!chatRoomId) {
    alert("Please enter chat room Id.");
    return;
  }

  fetch(`/chatrooms/${chatRoomId}`, {
    method: 'GET',
    headers: { 'Content-Type': 'application/json' }
  })
  .then(res => {
        if (res.ok) {
          return res.json();
        }
    throw new Error("Failed to fetch chat room");
  })
  .then(data => {
    // alert("Chat room fetched: " + data.chatRoomName);
    const roomId=data.id;
    const name=data.chatRoomName;
    const groupOrPrivate=data.groupChat;
    sessionStorage.setItem("ChatRoomName", name);
    sessionStorage.setItem("RoomId", roomId);
    sessionStorage.setItem("isChatRoomOpen", "true");
    if(groupOrPrivate)
      {
        sessionStorage.setItem("GroupOrPrivate", "true");
        sessionStorage.removeItem('Private');

      }else{
        sessionStorage.setItem("Private", "true");
        sessionStorage.removeItem('GroupOrPrivate');
      }
    openChatPage();
    closeModal4();

  })
  .catch(err => {
    alert("Error: " + err.message);
  });
}


function openChatPage() {
  sessionStorage.setItem("div", "1");
window.location.href='chatpage.html';
}

