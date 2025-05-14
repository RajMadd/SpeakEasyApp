document.addEventListener('DOMContentLoaded', () => {
    const Box = document.getElementById('box');
  
    const savedBackground = sessionStorage.getItem('BoxBackground');
    
    if (savedBackground) {
      Box.style.backgroundColor = 'black';  
    } else {
      Box.style.backgroundColor = 'white';  
    }


  });

  document.getElementById('Logout').addEventListener('click', () => {
    fetch('/users/logout') // adjust path as needed
      .then(res => {
        if (res.ok) {
          sessionStorage.removeItem('login');
          sessionStorage.removeItem('UserId');
          window.location.href = 'login.html';
        }
      })
      .catch(err => console.error('Logout failed:', err));
  });
  



  function translateText(text, targetLang, callback) {
    const url = 'https://deep-translate1.p.rapidapi.com/language/translate/v2';
  
    fetch(url, {
      method: 'POST',
      headers: {
        'content-type': 'application/json',
        'X-RapidAPI-Key': '6f39c919f4msh63cc0e6f772b461p1d0846jsn90a0758d5f20',
        'X-RapidAPI-Host': 'deep-translate1.p.rapidapi.com'
      },
      body: JSON.stringify({
        q: text,
        source: 'en',
        target: targetLang
      })
    })
    .then(response => response.json())
    .then(data => {
      console.log("Translation API response:", data);
      const translated = data?.data?.translations?.translatedText?.[0] || text;
      callback(translated);
    })
    .catch(err => {
      console.error("Translation error:", err);
      callback(text);
    });
  }
  
  


  document.getElementById('sendbtn').addEventListener('click', () => {
    const input = document.getElementById('SearchBar');
    const message = input.value.trim();
  
    if (!message) return alert("Please enter a message.");
  
    const senderId = sessionStorage.getItem('UserId');
    const roomId = sessionStorage.getItem('RoomId');
    const groupOrPrivate = sessionStorage.getItem('GroupOrPrivate');
  
    if (!senderId || !roomId) {
      return alert("Missing sender or room information.");
    }

    const targetLang = sessionStorage.getItem('targetLanguage') || "hi";
  
    console.log("Original message:", message);


    translateText(message, targetLang, (translatedMessage) => {

      console.log("Translated message:", translatedMessage);

      const sender = { id: senderId };
      const chatRoom = { id: roomId };
  
      const payload = {
        sender,
        chatRoom,
        content: translatedMessage  // 🔁 CHANGED: Send translated message
      };
  
    if (!groupOrPrivate) {
      const receiverId = sessionStorage.getItem('privateReceiver');
      if (!receiverId) return alert("Receiver ID missing.");
      payload.receiver = { id: receiverId };
    }

    console.log("Sending payload:", payload);

  
    fetch('/messages', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    })
    .then(res => {
      if (!res.ok) throw new Error("Message couldn't be sent.");
      return res.text();
    })
    .then(result => {
      // Only append if message sent successfully
      const msgDiv = document.createElement('div');
      msgDiv.textContent = "You: " + translatedMessage;

      msgDiv.style.width = 'fit-content';
      msgDiv.style.backgroundColor = "#25D366";
      msgDiv.classList.add('message');
      document.getElementById('box').appendChild(msgDiv);
      input.value = ""; // clear input
    })
    .catch(err => {
      alert("Error: " + err.message);
    });
  });
});
  

  function search(){

const id=document.getElementById('SearchBox').value.trim();

if(id!==""){

fetch(`/users/${id}`,{
  method:'Get',
  headers: {
    'Content-Type': 'application/json'
  }
}).then( res => {
  if (res.ok) { 
    return res.json();}
  throw new Error("No such user exist.");
})
.then(users => {
    const list=document.getElementById('list2');
      document.getElementById('PrintSingleUserList').style.display='flex';
        const userDiv=document.createElement('div');
        userDiv.style.backgroundColor="black";
        list.style.maxHeight = '300px';       // or any height you prefer
        list.style.overflowY = 'auto';
            userDiv.style.color='white';
            userDiv.style.margin = '8px';
            userDiv.style.padding = '8px';
            userDiv.style.border = '1px solid #ccc';
            userDiv.style.borderRadius = '5px';
        userDiv.textContent=users.username + " : " + users.id;
        list.appendChild(userDiv);
  })
.catch(err => {
  alert("Error: " + err.message);
});
}
else{
  fetch('/users', {
    method: 'GET',
  })
  .then(res => {
    if (res.ok) return res.json();
    throw new Error("There are no users.");
  })
  .then(users => {
    const list=document.getElementById('list1');
      document.getElementById('PrintUserList').style.display='flex';
      users.forEach(users => {
        const userDiv=document.createElement('div');
        userDiv.style.backgroundColor="black";
        list.style.maxHeight = '300px';       // or any height you prefer
        list.style.overflowY = 'auto';
            userDiv.style.color='white';
            userDiv.style.margin = '8px';
            userDiv.style.padding = '8px';
            userDiv.style.border = '1px solid #ccc';
            userDiv.style.borderRadius = '5px';
        userDiv.textContent=users.username + " : " + users.id;
        list.appendChild(userDiv);
      });
  })
  .catch(err => {
    alert("Error: " + err.message);
  });
}
}


  window.addEventListener('load', () => {
    document.getElementById("UserID").innerText= "User Id- " + sessionStorage.getItem('UserId');
    if(sessionStorage.getItem('isChatRoomOpen')){
      const roomname=sessionStorage.getItem('ChatRoomName');
      const status=sessionStorage.getItem('isChatRoomopen');
      const groupOrPrivate=sessionStorage.getItem('GroupOrPrivate');
      if (roomname) {
        const newDiv = document.createElement('div');
        newDiv.style.fontSize='20px';
        newDiv.style.color='blue';
        newDiv.textContent = roomname;
        document.getElementById("UsersContainer").appendChild(newDiv);
      }  

      {
        const newDiv = document.createElement('div');
        newDiv.style.fontSize='20px';
        newDiv.style.color='blue';
        if(groupOrPrivate)
          {
            newDiv.textContent = "Group";

          }else{
            newDiv.textContent = "Private";
          }
        document.getElementById("UsersContainer").appendChild(newDiv);
      }

        const newDiv = document.createElement('div');
        newDiv.style.fontSize='20px';
        newDiv.style.color='blue';
        newDiv.textContent = "chat room is open.";
        document.getElementById("UsersContainer").appendChild(newDiv);
      }  
      else{
        
          const newDiv = document.createElement('div');
        newDiv.style.fontSize='20px';
        newDiv.style.color='blue';
        newDiv.textContent = "No ChatRoom is open";
        document.getElementById("UsersContainer").appendChild(newDiv);
        
      }
});

 function closeChatRoom(){
  sessionStorage.removeItem('isChatRoomOpen');
  sessionStorage.removeItem('RoomId');
 location.reload();
}



function disableSearchContainer() {
  document.getElementById("SearchBar").disabled = true;
  document.getElementById("sendbtn").disabled = true;
}


if (!sessionStorage.getItem('isChatRoomOpen')) {
  disableSearchContainer();
}


window.addEventListener('load', ()=>{

  const senderId=sessionStorage.getItem("UserId");
  const roomId=sessionStorage.getItem('RoomId');

    fetch(`/messages/${roomId}`,{
      method:'GET'
    })
    .then(res=>{
      if(res.ok) return res.json();
      throw new Error("Messages can't be shown.");
    })
    .then(messages=>{
      // alert("Message displayed successfully");
      
      messages.forEach(msg => {

        const msgDiv = document.createElement('div');
        const senderName = msg.sender.username;
        const sender_Id = msg.sender.id;


        if(Number(senderId) === sender_Id){
          msgDiv.textContent ="You: " + msg.content;

        }
         else{
          msgDiv.textContent = senderName+": " + msg.content;
         }
         msgDiv.style.width='fit-content';
         msgDiv.style.backgroundColor ="#25D366";
        msgDiv.classList.add('message');
        document.getElementById('box').appendChild(msgDiv);
      });
    })
    .catch(err=>{
      alert("Error: " + err.message)
    })

})

document.addEventListener('visibilitychange', function () {
  if (document.visibilityState === 'visible') {
    location.reload();
  }
});

