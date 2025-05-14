const settingsBtn = document.getElementById('settings-btn');
const settingsPanel = document.getElementById('settings-panel');
const goToChat= document.querySelector('.Chat');

window.addEventListener('DOMContentLoaded', function () {
  const savedLang = sessionStorage.getItem('targetLanguage');
  const languageSelector = document.getElementById('languageSelector');

  if (savedLang && languageSelector) {
    languageSelector.value = savedLang; // 👈 Set the selected value
  }

  languageSelector.addEventListener('change', function () {
    const selectedLang = this.value;
    sessionStorage.setItem('targetLanguage', selectedLang);
    alert("Language set to:  " + selectedLang);  // optional debug log
  });
});




settingsBtn.addEventListener('click', () => {
  settingsPanel.classList.toggle('hidden');
});


goToChat.addEventListener('click',() => {
  if(sessionStorage.getItem("login")==="true"){
    window.location.href= 'chatpage.html';
  }
  else{
    window.location.href= 'login.html';
  }
});


document.addEventListener('DOMContentLoaded', () => {
  const mode = document.getElementById('Mode');
  const options = document.getElementById('Options');

  const savedBackground = sessionStorage.getItem('OptionsBackground');
  if (savedBackground) {
    options.style.backgroundImage = 'linear-gradient(to right, black, grey)';// Change to black
  }

  mode.addEventListener('click', (event) => {
    event.preventDefault(); // Prevents the link from navigating
    mode.style.color = 'white';

    // Reset the color back after 1 second (1000ms)
    setTimeout(() => {
      mode.style.color = '';  // Reset to the original color (default or CSS defined color)
    }, 300);
    if (options.style.backgroundImage === 'linear-gradient(to right, orange, red)') {
      options.style.backgroundImage = 'linear-gradient(to right, black, grey)';
      sessionStorage.setItem('OptionsBackground', 'black');
      sessionStorage.setItem('BoxBackground', 'black');
    } else {
      options.style.backgroundImage = 'linear-gradient(to right, orange, red)';
      sessionStorage.setItem('OptionsBackground', '');
      sessionStorage.setItem('BoxBackground', '');
    }

  });
});



  