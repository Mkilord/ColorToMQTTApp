let isStarted = false;

function start() {
    fetch('/start', {method: 'POST'})
        .then(response => response.text())
        .then((message) => {
            document.getElementById('startBtn').classList.add('start-active');
            showNotification(message, 'success');
            isStarted = true;
            updateColorLoop()
        }).catch(error => {
        isStarted = false;
        showNotification('Ошибка при старте: ' + error, 'error');
    })
}

function stop() {
    fetch('/stop', {method: 'POST'})
        .then(response => response.text())
        .then((message) => {
            showNotification(message, 'success');
            document.getElementById('startBtn').classList.remove('start-active');
            isStarted = false;
        })
        .catch(error => {
            showNotification('Ошибка при остановке: ' + error, 'error');
        })
}

function updateColorLoop() {
    if (!isStarted) return;

    fetch('/color')
        .then(response => response.text())
        .then(color => {
            console.log(color);
            document.getElementById('color-box').style.backgroundColor = color;
        })
        .finally(() => {
            if (isStarted) {
                setTimeout(updateColorLoop, 500); // Запускаем следующий вызов
            }
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
