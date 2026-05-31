const searchInput = document.getElementById('searchInput');
const searchButton = document.getElementById('searchButton');
const searchResults = document.getElementById('searchResults');

function renderSearchResult(dest) {
    const card = document.createElement('article');
    card.className = 'destination-card glass';
    const img = dest.image_url || dest.imageUrl || 'https://via.placeholder.com/400x300?text=No+Image';
    const name = dest.name || dest.title || 'Unknown';
    const region = dest.region || dest.country || '';
    const rating = dest.rating || dest.vote_average || 'N/A';
    card.innerHTML = `
        <img src="${img}" alt="${name}">
        <div class="movie-card-content">
            <h3>${name}</h3>
            <p>${region}</p>
            <div class="rating-pill">⭐ ${rating}</div>
            <a class="card-button" href="/destination-details.html?id=${encodeURIComponent(dest.id || dest.destinationId || '')}">Details</a>
        </div>
    `;
    return card;
}

async function performSearch(query) {
    searchResults.innerHTML = '<div class="loading-card">Searching destinations...</div>';
    try {
        const results = await Api.searchDestinations(query);
        if (!results || !results.length) {
            searchResults.innerHTML = '<div class="empty-state">No destinations found.</div>';
            return;
        }
        searchResults.innerHTML = '';
        results.forEach(r => searchResults.appendChild(renderSearchResult(r)));
    } catch (error) {
        searchResults.innerHTML = `<div class="error-message">${error.message}</div>`;
    }
}

window.addEventListener('DOMContentLoaded', () => {
    const params = new URLSearchParams(window.location.search);
    const query = params.get('query') || '';
    if (query) {
        searchInput.value = query;
        performSearch(query);
    }
    searchButton.addEventListener('click', () => {
        const q = searchInput.value.trim();
        if (q) performSearch(q);
    });
    searchInput.addEventListener('keydown', event => {
        if (event.key === 'Enter') searchButton.click();
    });
});
