function start() {
    fetch('/start', {method: 'POST'})
        .then(response => response.text())
        .then((message) => {

            let button = document.getElementById('startBtn');
            button.classList.add('start-active');
            console.log(document.getElementById('startBtn')); // Должен вернуть элемент кнопки
            showNotification(message, 'success');
        }).catch(error => {
        showNotification('Ошибка при старте: ' + error, 'error');
    })
}

function stop() {
    fetch('/stop', {method: 'POST'})
        .then(response => response.text())
        .then((message) => {
            showNotification(message, 'success');
            let button = document.getElementById('startBtn');
            button.classList.remove('start-active');
        })
        .catch(error => {
            showNotification('Ошибка при остановке: ' + error, 'error');
        })
}

function updateColor() {
    fetch('/color')
        .then(response => response.text())
        .then(color => {
            document.getElementById('color-box').style.backgroundColor = color;
        });
}

// script.js
function showNotification(message, type) {
    let notification = document.getElementById('notification');

    // Устанавливаем текст уведомления
    notification.textContent = message;

    // Устанавливаем цвет в зависимости от типа сообщения
    if (type === 'success') {
        notification.style.backgroundColor = '#4CAF50'; // Зеленый для успеха
    } else if (type === 'error') {
        notification.style.backgroundColor = '#f44336'; // Красный для ошибки
    }

    // Показываем уведомление
    notification.classList.add('show');

    // Прячем уведомление через 3 секунды
    setTimeout(function () {
        notification.classList.remove('show');
    }, 3000);
}


setInterval(updateColor, 2000);
