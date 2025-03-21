
        // Create stars
        const starsContainer = document.getElementById('stars');
        
        const numStars = Math.min(200, window.innerWidth / 5); // Adjust number of stars based on screen size
        
        for (let i = 0; i < numStars; i++) {
            const star = document.createElement('div');
            star.classList.add('star');
            
            // Random size
            const size = Math.random() * 3;
            star.style.width = `${size}px`;
            star.style.height = `${size}px`;
            
            // Random position
            const left = Math.random() * 100;
            const top = Math.random() * 100;
            star.style.left = `${left}%`;
            star.style.top = `${top}%`;
            
            // Random twinkle delay
            star.style.animationDelay = `${Math.random() * 5}s`;
            
            starsContainer.appendChild(star);
        }
        
        // Create shooting stars
        function createShootingStar() {
            const shootingStar = document.createElement('div');
            shootingStar.classList.add('shooting-star');
            
            // Random starting position (top half of the screen, along left edge)
            const top = Math.random() * 30;
            shootingStar.style.top = `${top}%`;
            shootingStar.style.left = '0';
            
            // Random duration
            const duration = 2 + Math.random() * 3;
            shootingStar.style.animationDuration = `${duration}s`;
            
            document.body.appendChild(shootingStar);
            
            // Remove after animation
            setTimeout(() => {
                shootingStar.remove();
            }, duration * 1000);
        }
        
        // Create a shooting star every 2-5 seconds
        // Reduce on mobile for performance
        const shootingStarInterval = window.innerWidth > 768 ? 
            (2000 + Math.random() * 3000) : (5000 + Math.random() * 5000);
            
        setInterval(() => {
            createShootingStar();
        }, shootingStarInterval);
        
        // Create initial shooting stars - fewer on mobile
        const initialStars = window.innerWidth > 768 ? 3 : 1;
        for (let i = 0; i < initialStars; i++) {
            setTimeout(createShootingStar, i * 1000);
        }
        
        // Start button functionality
        const startButton = document.getElementById('startButton');
        const introScreen = document.getElementById('introScreen');
        const gameWrapper = document.getElementById('gameWrapper');
        
        startButton.addEventListener('click', () => {
            // Fade out intro screen
            introScreen.style.animation = 'fadeOut 0.8s ease forwards';
            
            // After animation completes, hide intro and show game
            setTimeout(() => {
                introScreen.style.display = 'none';
                gameWrapper.style.display = 'block';
            }, 800);
        });
        
        // Fullscreen functionality
        const fullscreenBtn = document.getElementById('fullscreenBtn');
        const gameFrame = document.getElementById('gameFrame');
        
        fullscreenBtn.addEventListener('click', () => {
            if (gameFrame.requestFullscreen) {
                gameFrame.requestFullscreen();
            } else if (gameFrame.webkitRequestFullscreen) { /* Safari */
                gameFrame.webkitRequestFullscreen();
            } else if (gameFrame.msRequestFullscreen) { /* IE11 */
                gameFrame.msRequestFullscreen();
            }
        });
        
        // Handle window resize for better responsiveness
        window.addEventListener('resize', function() {
            // Update container height based on window dimensions
            const container = document.querySelector('.container');
            if (window.innerHeight < 500 && window.innerWidth > window.innerHeight) {
                // Landscape mode on small screens
                container.style.height = '95vh';
            } else if (window.innerHeight < 700) {
                container.style.height = '85vh';
            } else {
                container.style.height = '90vh';
            }
        });
        
        // Initial call to set proper height
        window.dispatchEvent(new Event('resize'));
