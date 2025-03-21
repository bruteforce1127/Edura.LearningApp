const dots = document.querySelectorAll('.color-dot');
    
    // Random movement for dots
    function animateDots() {
      dots.forEach(dot => {
        const randomX = Math.random() * 10 - 5;
        const randomY = Math.random() * 10 - 5;
        const currentTransform = getComputedStyle(dot).transform;
        dot.style.transform = `${currentTransform} translate(${randomX}px, ${randomY}px)`;
      });
      
      setTimeout(animateDots, 3000);
    }
    
    // Initialize animations
    setTimeout(animateDots, 1000);
    
    // Button click animation
    document.querySelector('.quiz-btn').addEventListener('click', function() {
      this.style.transform = 'scale(0.95)';
      setTimeout(() => {
        this.style.transform = 'scale(1)';
        // Navigate to quiz page - replace with actual navigation
        console.log('Navigating to quiz page...');
        // window.location.href = 'quiz-page.html';
      }, 150);
    });