const compareIdA = document.getElementById('compareIdA');
const compareIdB = document.getElementById('compareIdB');
const compareButton = document.getElementById('compareButton');
const compareResults = document.getElementById('compareResults');

function movieCompareCard(movie) {
    return `
        <div class="compare-card glass">
            <img src="${movie.posterPath || 'https://via.placeholder.com/400x600?text=No+Image'}" alt="${movie.title}">
            <h3>${movie.title}</h3>
            <p>${movie.releaseDate || 'Unknown Release'}</p>
            <div class="compare-stats">
                <span>Rating: ${movie.rating}</span>
                <span>Runtime: ${movie.runtime} min</span>
                <span>Popularity: ${movie.popularity}</span>
            </div>
            <a class="card-button" href="/movie-details.html?id=${movie.id}">Details</a>
        </div>
    `;
}

async function compareMovies() {
    compareResults.innerHTML = '<div class="loading-card">Comparing movies...</div>';
    try {
        const [movieA, movieB] = await Promise.all([Api.getMovie(compareIdA.value.trim()), Api.getMovie(compareIdB.value.trim())]);
        compareResults.innerHTML = `
            <div class="compare-columns">
                ${movieCompareCard(movieA)}
                ${movieCompareCard(movieB)}
            </div>
            <div class="compare-summary glass">
                <h3>Comparison Results</h3>
                <p><strong>Higher Rating:</strong> ${movieA.rating > movieB.rating ? movieA.title : movieB.rating > movieA.rating ? movieB.title : 'Tie'}</p>
                <p><strong>Longer Runtime:</strong> ${movieA.runtime > movieB.runtime ? movieA.title : movieB.runtime > movieA.runtime ? movieB.title : 'Tie'}</p>
                <p><strong>More Popular:</strong> ${movieA.popularity > movieB.popularity ? movieA.title : movieB.popularity > movieA.popularity ? movieB.title : 'Tie'}</p>
                <p><strong>Recent Release:</strong> ${new Date(movieA.releaseDate).getFullYear() > new Date(movieB.releaseDate).getFullYear() ? movieA.title : new Date(movieB.releaseDate).getFullYear() > new Date(movieA.releaseDate).getFullYear() ? movieB.title : 'Tie'}</p>
            </div>
        `;
    } catch (error) {
        compareResults.innerHTML = `<div class="error-message">${error.message}</div>`;
    }
}

window.addEventListener('DOMContentLoaded', () => {
    compareButton?.addEventListener('click', compareMovies);
});
