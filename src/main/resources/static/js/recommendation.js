const recommendationMovieId = document.getElementById('recommendationMovieId');
const recommendButton = document.getElementById('recommendButton');
const recommendationResults = document.getElementById('recommendationResults');

function renderRecommendationCard(movie) {
    const card = document.createElement('article');
    card.className = 'movie-card glass';
    card.innerHTML = `
        <img src="${movie.posterPath || 'https://via.placeholder.com/400x600?text=No+Image'}" alt="${movie.title}">
        <div class="movie-card-content">
            <h3>${movie.title}</h3>
            <p>${movie.reason}</p>
            <div class="tag-row">
                <span>Score: ${movie.rating.toFixed(1)}</span>
                <span>Popularity: ${movie.popularity.toFixed(0)}</span>
            </div>
            <a class="card-button" href="/movie-details.html?id=${movie.movieId}">View Details</a>
        </div>
    `;
    return card;
}

async function loadRecommendations(movieId) {
    recommendationResults.innerHTML = '<div class="loading-card">Generating recommendations...</div>';
    try {
        const data = await Api.getRecommendations(movieId);
        if (!data.length) {
            recommendationResults.innerHTML = '<div class="empty-state">No recommendations available.</div>';
            return;
        }
        recommendationResults.innerHTML = '';
        data.forEach(item => recommendationResults.appendChild(renderRecommendationCard(item)));
    } catch (error) {
        recommendationResults.innerHTML = `<div class="error-message">${error.message}</div>`;
    }
}

window.addEventListener('DOMContentLoaded', () => {
    recommendButton.addEventListener('click', () => {
        const id = recommendationMovieId.value.trim();
        if (id) loadRecommendations(id);
    });
});
