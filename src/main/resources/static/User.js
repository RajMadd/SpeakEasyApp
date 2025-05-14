function updateUser(){
    document.getElementById('UserUpdate').style.display= 'flex';
}
function closeModal1(){
    document.getElementById('UserUpdate').style.display= 'none';
}

function UpdateUser() {

    const userId = document.getElementById('userId1').value.trim();
    const userName = document.getElementById('userName1').value.trim();
    const email = document.getElementById('email1').value.trim();
    const oldpwd = document.getElementById('oldpwd1').value.trim();
    const newpwd = document.getElementById('newpwd1').value.trim();

  
    if (!oldpwd || !userId) {
      alert("Please enter the current password and user Id.");
      return;
    }
  
    const updateData = {};
    if (userName) updateData.username = userName;
    if (email) updateData.email = email;
    if (newpwd) updateData.password = newpwd;
    updateData.oldpwd = oldpwd;

    fetch(`/users/${userId}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(updateData)
    })
    .then(res => {
      if (res.ok) return res.json();
      throw new Error("Failed to update the user");
    })
    .then(data => {
      alert("User updated successfully.");
      closeModal1();
    })
    .catch(err => {
      alert("Error: " + err.message);
    });
  }

function deleteAccount(){
    document.getElementById('UserDelete').style.display= 'flex';
}
function closeModal2(){
    document.getElementById('UserDelete').style.display= 'none';
}


function DeleteAccount() {

    const userId = document.getElementById('userId2').value.trim();
  
    if (!userId) {
      alert("Please enter the user Id to be deleted.");
      return;
    }
  
    fetch(`/users/${userId}`, {
      method: 'DELETE'
    })
    .then(res => {
      if (res.ok) return res.text();
      throw new Error("Failed to delete the user.");
    })
    .then(data => {
      alert("User deleted successfully.");
      closeModal2();
    })
    .catch(err => {
      alert("Error: " + err.message);
    });
  }
