// Game tracking script - Add this to your existing scripts section
document.addEventListener('DOMContentLoaded', function() {
    // Define learning objectives for each game
    const gameData = {
        "Number Ninjas": {
            learning: "Addition and subtraction skills, number recognition, quick mental math",
            coins: 50
        },
        "Fraction Frenzy": {
            learning: "Understanding fractions, equivalent fractions, fraction operations",
            coins: 50
        },
        "Multiplication Quest": {
            learning: "Multiplication tables, patterns, mental multiplication strategies",
            coins: 50
        },
        "Geometry Genius": {
            learning: "Shape recognition, spatial reasoning, geometric properties",
            coins: 50
        },
        "Math Monsters": {
            learning: "Problem-solving, mixed operations, strategic thinking",
            coins: 50
        },
        "Puzzle Wizards": {
            learning: "Logical reasoning, pattern recognition, mathematical puzzles",
            coins: 50
        },
        "Storytime Express": {
            learning: "Reading comprehension, vocabulary development, story elements",
            coins: 50
        },
        "Phonics Fun": {
            learning: "Letter-sound relationships, phonemic awareness, word building",
            coins: 50
        },
        "Word Wizards": {
            learning: "Spelling, vocabulary expansion, word recognition",
            coins: 50
        },
        "Reading Quests": {
            learning: "Reading fluency, comprehension strategies, text analysis",
            coins: 50
        },
        "Vocabulary Voyage": {
            learning: "New words, word meanings, context clues, word relationships",
            coins: 50
        },
        "Comprehension Champions": {
            learning: "Understanding text, making inferences, summarizing information",
            coins: 50
        },
        "Arts & Crafts": {
            learning: "Creativity, fine motor skills, following instructions",
            coins: 50
        },
        "Puzzle Palace": {
            learning: "Critical thinking, problem-solving, spatial reasoning",
            coins: 50
        },
        "Science Lab": {
            learning: "Scientific concepts, cause and effect, observation skills",
            coins: 50
        },
        "Music Makers": {
            learning: "Rhythm, patterns, auditory discrimination, musical concepts",
            coins: 50
        },
        "Coding Corner": {
            learning: "Logical thinking, sequential reasoning, basic coding concepts",
            coins: 50
        },
        "Outdoor Adventures": {
            learning: "Nature awareness, physical skills, environmental knowledge",
            coins: 50
        }
    };

    // Track when a game is clicked
    const gameCards = document.querySelectorAll('.card');
    gameCards.forEach(card => {
        const link = card.querySelector('a');
        const gameTitle = card.querySelector('.card-title').textContent;
        
        // Add data attribute for learning objectives
        card.setAttribute('data-learning', gameData[gameTitle]?.learning || "Various educational skills");
        
        link.addEventListener('click', function(e) {
            // Don't prevent default - let the game open normally
            
            // Record game data
            const gameName = gameTitle;
            const learningObjectives = gameData[gameName]?.learning || "Various educational skills";
            const coinsEarned = gameData[gameName]?.coins || 50;
            
            console.log(`Starting game: ${gameName}`);
            
            // Store the game start time in sessionStorage
            const gameSessionData = {
                gameName: gameName,
                learning: learningObjectives,
                coins: coinsEarned,
                startTime: new Date().getTime(),
                username: "anmol"
            };
            
            sessionStorage.setItem('currentGameSession', JSON.stringify(gameSessionData));
            
            // Set a timeout to send the data after 10 seconds
            // We'll use a service worker or background sync to handle this
            // This is a simple approach - browser may cancel this if page changes
            setTimeout(function() {
                sendGameData(gameSessionData);
            }, 10000);
        });
    });
    
    // Function to send data to the API
    function sendGameData(gameSessionData) {
        // Prepare data according to your API model
        const apiData = {
            game: gameSessionData.gameName,
            learning: gameSessionData.learning,
            coins: gameSessionData.coins,
            username: gameSessionData.username
        };
        
        // Use Fetch API with keepalive to ensure request completes
        fetch('http://api.edura.example/api/gamerecord', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(apiData),
            keepalive: true // This helps ensure the request completes even if page changes
        })
        .then(response => {
            if (response.ok) {
                console.log('Game data sent successfully');
                sessionStorage.removeItem('currentGameSession');
            }
        })
        .catch(error => {
            console.error('Error sending game data:', error);
            // Store failed attempts for retry
            const failedAttempts = JSON.parse(sessionStorage.getItem('failedGameSessions') || '[]');
            failedAttempts.push(gameSessionData);
            sessionStorage.setItem('failedGameSessions', JSON.stringify(failedAttempts));
        });
    }
    
    // Check for page visibility changes to handle retry logic
    document.addEventListener('visibilitychange', function() {
        if (document.visibilityState === 'visible') {
            checkAndProcessPendingGameSessions();
        }
    });
    
    // Process any pending game sessions
    function checkAndProcessPendingGameSessions() {
        // Check if there was a game session that might not have completed
        const currentSession = sessionStorage.getItem('currentGameSession');
        if (currentSession) {
            const gameSessionData = JSON.parse(currentSession);
            const currentTime = new Date().getTime();
            const sessionStartTime = gameSessionData.startTime;
            
            // If it's been at least 10 seconds since game started
            if ((currentTime - sessionStartTime) >= 10000) {
                sendGameData(gameSessionData);
            }
        }
        
        // Check for failed attempts
        const failedAttempts = JSON.parse(sessionStorage.getItem('failedGameSessions') || '[]');
        if (failedAttempts.length > 0) {
            // Try to send failed attempts again
            failedAttempts.forEach(session => {
                sendGameData(session);
            });
            // Clear the failed attempts
            sessionStorage.removeItem('failedGameSessions');
        }
    }
    
    // Call once on page load to handle any pending sessions
    checkAndProcessPendingGameSessions();
});