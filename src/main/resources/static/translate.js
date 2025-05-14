function translateText(text, targetLang, callback) {
    fetch("https://libretranslate.com/translate", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        q: text,
        source: "auto",
        target: targetLang,
        format: "text"
      })
    })
    .then(response => response.json())
    .then(data => callback(data.translatedText))
    .catch(err => {
      console.error("Translation error:", err);
      callback(text); // fallback: return original if failed
    });
  }
  