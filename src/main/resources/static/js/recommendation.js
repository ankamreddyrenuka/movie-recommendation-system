const recommendationDestinationId = document.getElementById('recommendationDestinationId');
const recommendButton = document.getElementById('recommendButton');
const recommendationResults = document.getElementById('recommendationResults');

function renderRecommendationCard(item) {
    const card = document.createElement('article');
    card.className = 'destination-card glass';
    const img = item.imageUrl || item.image_url || 'https://via.placeholder.com/400x300?text=No+Image';
    const name = item.destinationName || item.name || 'Unknown';
    const reason = item.reason || '';
    const score = typeof item.score === 'number' ? item.score.toFixed(1) : (item.score || 'N/A');
    const popularity = item.popularity ? Math.round(item.popularity) : 'N/A';

    card.innerHTML = `
        <img src="${img}" alt="${name}">
        <div class="card-content">
            <h3>${name}</h3>
            <p>${reason}</p>
            <div class="tag-row">
                <span>Score: ${score}</span>
                <span>Popularity: ${popularity}</span>
            </div>
            <a class="card-button" href="/destination-details.html?id=${encodeURIComponent(item.destinationId || item.id || '')}">View Details</a>
        </div>
    `;
    return card;
}

async function loadRecommendations(destinationId) {
    recommendationResults.innerHTML = '<div class="loading-card">Generating recommendations...</div>';
    try {
        const data = await Api.getRecommendations(destinationId);
        if (!data || !data.length) {
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
    recommendButton?.addEventListener('click', () => {
        const id = recommendationDestinationId.value.trim();
        if (id) loadRecommendations(id);
    });
});

