const themeToggle = document.getElementById('themeToggle');
const homeSearchButton = document.getElementById('homeSearchButton');
const homeSearchInput = document.getElementById('homeSearchInput');
const voiceSearchButton = document.getElementById('voiceSearchButton');

function renderMovieCard(movie) {
    const card = document.createElement('article');
    card.className = 'movie-card glass';
    card.innerHTML = `
        <img src="${movie.poster_path || 'https://via.placeholder.com/400x600?text=No+Image'}" alt="${movie.title}">
        <div class="movie-card-content">
            <h3>${movie.title}</h3>
            <p>${movie.release_date || 'Unknown Release'}</p>
            <div class="rating-pill">⭐ ${movie.vote_average || movie.rating || 'N/A'}</div>
            <a class="card-button" href="/movie-details.html?id=${movie.id}">Details</a>
        </div>
    `;
    return card;
}

async function loadTrending() {
    const container = document.getElementById('trendingGrid');
    if (!container) return;
    container.innerHTML = '<div class="loading-card">Loading trending movies...</div>';
    try {
        const trending = await Api.getTrending();
        container.innerHTML = '';
        trending.slice(0, 12).forEach(movie => container.appendChild(renderMovieCard(movie)));
    } catch (error) {
        container.innerHTML = `<div class="error-message">Unable to load trending movies. ${error.message}</div>`;
    }
}

function attachHomeSearch() {
    if (!homeSearchButton || !homeSearchInput) return;
    homeSearchButton.addEventListener('click', () => {
        window.location.href = `/search.html?query=${encodeURIComponent(homeSearchInput.value)}`;
    });
    voiceSearchButton?.addEventListener('click', async () => {
        if (!('webkitSpeechRecognition' in window) && !('SpeechRecognition' in window)) {
            alert('Voice search is not supported in this browser.');
            return;
        }
        const recognition = new (window.SpeechRecognition || window.webkitSpeechRecognition)();
        recognition.lang = 'en-US';
        recognition.onresult = event => {
            homeSearchInput.value = event.results[0][0].transcript;
            homeSearchButton.click();
        };
        recognition.start();
    });
}

function applyTheme() {
    const current = localStorage.getItem('cinematch-theme') || 'dark';
    document.documentElement.dataset.theme = current;
    if (themeToggle) {
        themeToggle.textContent = current === 'dark' ? 'Light' : 'Dark';
    }
}

function bindThemeButton() {
    themeToggle?.addEventListener('click', () => {
        const nextTheme = document.documentElement.dataset.theme === 'dark' ? 'light' : 'dark';
        localStorage.setItem('cinematch-theme', nextTheme);
        applyTheme();
    });
}

function parseQueryString() {
    const params = new URLSearchParams(window.location.search);
    return Object.fromEntries(params.entries());
}

async function renderMovieDetails() {
    const detailsContainer = document.getElementById('movieDetails');
    if (!detailsContainer) return;
    const params = parseQueryString();
    const id = params.id;
    if (!id) {
        detailsContainer.innerHTML = '<div class="error-message">No movie selected.</div>';
        return;
    }
    detailsContainer.innerHTML = '<div class="loading-card">Loading movie details...</div>';
    try {
        const movie = await Api.getMovie(id);
        detailsContainer.innerHTML = `
            <div class="detail-card glass">
                <img src="${movie.posterPath || 'https://via.placeholder.com/400x600?text=No+Image'}" alt="${movie.title}">
                <div class="detail-content">
                    <h1>${movie.title}</h1>
                    <p>${movie.overview || 'No overview available.'}</p>
                    <div class="tag-row">
                        <span>${movie.releaseDate || 'Unknown'}</span>
                        <span>⭐ ${movie.rating}</span>
                        <span>${movie.runtime} min</span>
                    </div>
                    <div class="detail-meta">
                        <p><strong>Director:</strong> ${movie.director || 'Unknown'}</p>
                        <p><strong>Genres:</strong> ${movie.genres.join(', ') || 'N/A'}</p>
                        <p><strong>Popularity:</strong> ${movie.popularity}</p>
                    </div>
                    <div class="cast-list">
                        <h4>Cast</h4>
                        <p>${movie.cast.slice(0, 6).join(', ') || 'N/A'}</p>
                    </div>
                    <a class="primary-button" href="${movie.trailerUrl || '#'}" target="_blank">Watch Trailer</a>
                </div>
            </div>
        `;
    } catch (error) {
        detailsContainer.innerHTML = `<div class="error-message">Unable to load movie details. ${error.message}</div>`;
    }
}

window.addEventListener('DOMContentLoaded', () => {
    applyTheme();
    bindThemeButton();
    attachHomeSearch();
    loadTrending();
    renderMovieDetails();
});
