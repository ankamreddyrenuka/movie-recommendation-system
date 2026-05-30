const searchInput = document.getElementById('searchInput');
const searchButton = document.getElementById('searchButton');
const searchResults = document.getElementById('searchResults');

function renderSearchCard(movie) {
    const card = document.createElement('article');
    card.className = 'movie-card glass';
    card.innerHTML = `
        <img src="${movie.poster_path || 'https://via.placeholder.com/400x600?text=No+Image'}" alt="${movie.title}">
        <div class="movie-card-content">
            <h3>${movie.title}</h3>
            <p>${movie.release_date || 'Unknown Release'}</p>
            <p>${movie.overview?.substring(0, 110) || 'No description available'}...</p>
            <div class="card-actions">
                <span class="rating-pill">⭐ ${movie.vote_average || 'N/A'}</span>
                <a class="card-button" href="/movie-details.html?id=${movie.id}">Details</a>
            </div>
        </div>
    `;
    return card;
}

async function runSearch(query) {
    searchResults.innerHTML = '<div class="loading-card">Searching movies...</div>';
    try {
        const results = await Api.searchMovies(query);
        if (!results.length) {
            searchResults.innerHTML = '<div class="empty-state">No movie results found.</div>';
            return;
        }
        searchResults.innerHTML = '';
        results.forEach(movie => searchResults.appendChild(renderSearchCard(movie)));
    } catch (error) {
        searchResults.innerHTML = `<div class="error-message">${error.message}</div>`;
    }
}

window.addEventListener('DOMContentLoaded', () => {
    const params = new URLSearchParams(window.location.search);
    const query = params.get('query') || '';
    if (query) {
        searchInput.value = query;
        runSearch(query);
    }
    searchButton.addEventListener('click', () => {
        const q = searchInput.value.trim();
        if (q) runSearch(q);
    });
    searchInput.addEventListener('keydown', event => {
        if (event.key === 'Enter') searchButton.click();
    });
});
