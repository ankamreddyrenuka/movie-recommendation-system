const compareIdA = document.getElementById('compareIdA');
const compareIdB = document.getElementById('compareIdB');
const compareButton = document.getElementById('compareButton');
const compareResults = document.getElementById('compareResults');

function destinationCompareCard(dest) {
    return `
        <div class="compare-card glass">
            <img src="${dest.imageUrl || dest.image_url || 'https://via.placeholder.com/400x300?text=No+Image'}" alt="${dest.name}">
            <h3>${dest.name}</h3>
            <p>${dest.region || dest.country || 'Unknown'}</p>
            <div class="compare-stats">
                <span>Rating: ${dest.rating || 'N/A'}</span>
                <span>Days: ${dest.recommendedDuration || 'N/A'}</span>
                <span>Popularity: ${dest.popularity || 'N/A'}</span>
            </div>
            <a class="card-button" href="/destination-details.html?id=${encodeURIComponent(dest.destinationId || dest.id || '')}">Details</a>
        </div>
    `;
}

async function compareMovies() {
    compareResults.innerHTML = '<div class="loading-card">Comparing destinations...</div>';
    try {
        const [a, b] = await Promise.all([Api.getDestination(compareIdA.value.trim()), Api.getDestination(compareIdB.value.trim())]);
        compareResults.innerHTML = `
            <div class="compare-columns">
                ${destinationCompareCard(a)}
                ${destinationCompareCard(b)}
            </div>
            <div class="compare-summary glass">
                <h3>Comparison Results</h3>
                <p><strong>Higher Rating:</strong> ${ (a.rating||0) > (b.rating||0) ? a.name : ( (b.rating||0) > (a.rating||0) ? b.name : 'Tie' ) }</p>
                <p><strong>Longer Recommended Stay:</strong> ${ (a.recommendedDuration||0) > (b.recommendedDuration||0) ? a.name : ( (b.recommendedDuration||0) > (a.recommendedDuration||0) ? b.name : 'Tie' ) }</p>
                <p><strong>More Popular:</strong> ${ (a.popularity||0) > (b.popularity||0) ? a.name : ( (b.popularity||0) > (a.popularity||0) ? b.name : 'Tie' ) }</p>
                <p><strong>Different Regions:</strong> ${ a.region === b.region ? 'Same' : a.region + ' / ' + b.region }</p>
            </div>
        `;
    } catch (error) {
        compareResults.innerHTML = `<div class="error-message">${error.message}</div>`;
    }
}

window.addEventListener('DOMContentLoaded', () => {
    compareButton?.addEventListener('click', compareMovies);
});
