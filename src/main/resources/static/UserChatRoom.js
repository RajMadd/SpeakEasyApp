function AddMember(){
    document.getElementById('userChatRoomAddModal').style.display= 'flex';
}

function closeModal1(){
    document.getElementById('userChatRoomAddModal').style.display= 'none';
}


            

function addMember(){
    const user_Id=sessionStorage.getItem('UserId');
    const userId= document.getElementById('userId1').value.trim();
    const roomId= sessionStorage.getItem('RoomId');
    fetch(`/userchatroom/${userId}/${roomId}`, {
        method: 'POST',
        headers:{'Content-Type': 'application/json'}
    })
    .then( res =>{
        if(res.ok) return res.json();
        throw new Error("Failed to add new member.");
    })
    .then( data=>{
        alert("New member added successfully.");
        const privateuserId=sessionStorage.getItem('UserId');
        if(sessionStorage.getItem('Private'))
            {
                fetch(`/userchatroom/receiver/${userId}/${roomId}`,{
                 method:'GET'
                  })
                  .then( res1 =>{
                     if(res1.ok) return res1.json();
                    })
                  .then( data1 =>{
                      if(userId && data1===user_Id){
                      sessionStorage.setItem("privateReceiver", userId);
                      }
                    })
                   .catch(
                      err => {
                      alert("Error: " + err.message);
                    })
            
            }
        closeModal1();
    })
    .catch(err =>{
      alert("Error: " + err.message);
    });
}


function RemoveMember(){
    document.getElementById('userChatRoomRemoveModal').style.display= 'flex';
}

function closeModal2(){
    document.getElementById('userChatRoomRemoveModal').style.display= 'none';
}


function removeMember(){
    const userId= document.getElementById('userId2').value.trim();
    const roomId= sessionStorage.getItem('RoomId');
    fetch(`/userchatroom/${userId}/${roomId}`, {
        method: 'DELETE',
    })
    .then( res =>{
        if(res.ok) return res.text();
        throw new Error("Failed to remove member");
    })
    .then( data=>{
        alert("User removed successfully.");
        closeModal2();
    })
    .catch(err =>{
      alert("Error: " + err.message);
    });
}


function ExistingMember(){
    document.getElementById('userChatRoomExistingModal').style.display= 'flex';
    const list=document.getElementById('list');
    const roomId= sessionStorage.getItem('RoomId');
    fetch(`/userchatroom/chatroom/${roomId}`,{
        method:'GET'
    })
    .then( res =>{
        if(res.ok) return res.json();
        throw new Error("Failed to show members");
    })
    .then( data=>{
          
        data.forEach(user => {
            const userDiv = document.createElement('div');
            userDiv.style.backgroundColor="black";
            list.style.maxHeight = '300px';  
            list.style.overflowY = 'auto';
            userDiv.style.color='white';
            userDiv.style.margin = '8px';
            userDiv.style.padding = '8px';
            userDiv.style.border = '1px solid #ccc';
            userDiv.style.borderRadius = '5px';
            userDiv.textContent = `Username: ${user.username}, ID: ${user.id}`;
            list.appendChild(userDiv);
        });

    })
    .catch(err =>{
      alert("Error: " + err.message);
    });
}

function closeModal3(){
    document.getElementById('userChatRoomExistingModal').style.display= 'none';
    window.location.href='chatpage.html';
}


window.addEventListener('load', () => {
    const options = document.getElementsByClassName('Options');
    if(!sessionStorage.getItem('isChatRoomOpen')){
    for (let i = 0; i < options.length; i++) {
        options[i].style.display = 'none';
    }
}
});


function closeModal4(){
    document.getElementById('PrintUserList').style.display= 'none';
    window.location.href='chatpage.html';
}

function closeModal5(){
    document.getElementById('PrintSingleUserList').style.display= 'none';
    window.location.href='chatpage.html';
}
