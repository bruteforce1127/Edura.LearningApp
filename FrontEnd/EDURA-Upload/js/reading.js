document.addEventListener('DOMContentLoaded', function() {
    // Create dynamic reading elements
    const readingElements = document.querySelector('.reading-elements');
    const elements = ['📚', '📖', '📝', '🔤', '📘', '✏️', 'A', 'B', 'C', '🖋️', '📜'];
    
    for (let i = 0; i < 15; i++) {
        const element = document.createElement('span');
        element.className = 'element';
        element.textContent = elements[Math.floor(Math.random() * elements.length)];
        
        const left = Math.random() * 100;
        const animationDelay = Math.random() * 10;
        const animationDuration = 10 + Math.random() * 10;
        
        element.style.left = `${left}%`;
        element.style.animationDelay = `${animationDelay}s`;
        element.style.animationDuration = `${animationDuration}s`;
        
        readingElements.appendChild(element);
    }
});