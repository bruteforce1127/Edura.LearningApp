// Game tracking script

document.addEventListener('DOMContentLoaded', function() {
    const gameData = {
        "Number Ninjas": { learning: "Addition and subtraction skills", coins: 50 },
        "Fraction Frenzy": { learning: "Understanding fractions", coins: 50 },
        "Multiplication Quest": { learning: "Multiplication tables", coins: 50 },
        "Geometry Genius": { learning: "Shape recognition", coins: 50 },
        "Math Monsters": { learning: "Problem-solving", coins: 50 },
        "Puzzle Wizards": { learning: "Logical reasoning", coins: 50 }
    };

    function getLoginUsername() {
        return localStorage.getItem('loginUsername') || "guest";
    }

    function sendGameData(gameData) {
        const username = getLoginUsername();
        const apiUrl = `http://192.168.137.1:8081/childProfile/${username}`;
        
        fetch(apiUrl, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(gameData)
        }).then(response => {
            if (response.ok) {
                console.log('Game data sent successfully');
                localStorage.removeItem('pendingGameData');
            } else {
                console.error('Failed to send game data');
            }
        }).catch(error => console.error('Error sending game data:', error));
    }

    document.querySelectorAll('.card a').forEach(link => {
        link.addEventListener('click', function(event) {
            event.preventDefault();
            const gameName = this.closest('.card').querySelector('.card-title').textContent;
            const gameSessionData = {
                game: gameName,
                learning: gameData[gameName]?.learning || "Various skills",
                coins: gameData[gameName]?.coins || 50,
                username: getLoginUsername()
            };
            localStorage.setItem('pendingGameData', JSON.stringify(gameSessionData));
            setTimeout(() => sendGameData(gameSessionData), 10000);
            window.location.href = this.href;
        });
    });

    window.addEventListener('beforeunload', function() {
        const pendingData = localStorage.getItem('pendingGameData');
        if (pendingData) sendGameData(JSON.parse(pendingData));
    });
});