// Проверка куки перед перенаправлением
function getCookie(name) {
  const cookies = document.cookie.split(';');
  for (let cookie of cookies) {
    const [key, value] = cookie.split('=');
    if (key.trim() === name) return value;
  }
  return null;
}

if (!getCookie('userLang') && navigator.language.startsWith('ru')) {
  window.location.href = '/ru/index.html';
}

// При ручном переключении устанавливаем куки
document.querySelector('.lang-switcher').addEventListener('click', () => {
  document.cookie = 'userLang=ru; path=/; max-age=31536000'; // Срок действия: 1 год
});