const themeToggle = document.getElementById('themeToggle');
const homeSearchButton = document.getElementById('homeSearchButton');
const homeSearchInput = document.getElementById('homeSearchInput');
const voiceSearchButton = document.getElementById('voiceSearchButton');

function renderDestinationCard(destination) {
    // Adapted to destination summary fields (supports both snake_case and camelCase)
    const card = document.createElement('article');
    card.className = 'destination-card glass';

    const img =
        destination.image_url ||
        destination.imageUrl ||
        'https://via.placeholder.com/400x300?text=No+Image';

    const name = destination.name || destination.title || 'Unknown';
    const region = destination.region || destination.country || '';
    const rating = destination.rating || destination.vote_average || 'N/A';

    card.innerHTML = `
        <img src="${img}" alt="${name}">
        <div class="destination-card-content">
            <h3>${name}</h3>
            <p>${region}</p>
            <div class="rating-pill">⭐ ${rating}</div>
            <a class="card-button" href="/destination-details.html?id=${encodeURIComponent(
                destination.id || destination.destinationId || ''
            )}">Details</a>
        </div>
    `;

    return card;
}

async function loadTrending() {
    const container = document.getElementById('trendingGrid');
    if (!container) return;

    container.innerHTML = '<div class="loading-card">Loading trending destinations...</div>';
    try {
        const trending = await Api.getTrending();
        container.innerHTML = '';
        trending
            .slice(0, 12)
            .forEach(destination => container.appendChild(renderDestinationCard(destination)));

    } catch (error) {
        container.innerHTML = `<div class="error-message">Unable to load trending destinations. ${error.message}</div>`;
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
    const current = localStorage.getItem('traveldna-theme') || 'dark';
    document.documentElement.dataset.theme = current;
    if (themeToggle) {
        themeToggle.textContent = current === 'dark' ? 'Light' : 'Dark';
    }
}

function bindThemeButton() {
    themeToggle?.addEventListener('click', () => {
        const nextTheme = document.documentElement.dataset.theme === 'dark' ? 'light' : 'dark';
        localStorage.setItem('traveldna-theme', nextTheme);
        applyTheme();
    });
}

function parseQueryString() {
    const params = new URLSearchParams(window.location.search);
    return Object.fromEntries(params.entries());
}

async function renderDestinationDetails() {
    const detailsContainer = document.getElementById('destinationDetails');

    if (!detailsContainer) return;


    const params = parseQueryString();
    const id = params.id;

    if (!id) {
        detailsContainer.innerHTML = '<div class="error-message">No destination selected.</div>';
        return;
    }

    detailsContainer.innerHTML = '<div class="loading-card">Loading destination details...</div>';

    try {
        const dest = await Api.getDestination(id);
        detailsContainer.innerHTML = `
            <div class="detail-card glass">
                <img src="${dest.imageUrl || dest.image_url || 'https://via.placeholder.com/400x300?text=No+Image'}" alt="${dest.name}">
                <div class="detail-content">
                    <h1>${dest.name}</h1>
                    <p>${dest.description || 'No description available.'}</p>
                    <div class="tag-row">
                        <span>${dest.region || dest.country || 'Unknown'}</span>
                        <span>⭐ ${dest.rating || 'N/A'}</span>
                        <span>${dest.recommendedDuration ? dest.recommendedDuration + ' days' : ''}</span>
                    </div>
                    <div class="detail-meta">
                        <p><strong>Travel style:</strong> ${dest.travelStyle || 'N/A'}</p>
                        <p><strong>Budget:</strong> ${dest.budgetRange || 'N/A'}</p>
                        <p><strong>Highlights:</strong> ${dest.highlights || 'N/A'}</p>
                    </div>
                    <a class="primary-button" href="/" target="_self">Plan Trip</a>
                </div>
            </div>
        `;
    } catch (error) {
        detailsContainer.innerHTML = `<div class="error-message">Unable to load destination details. ${error.message}</div>`;
    }
}

window.addEventListener('DOMContentLoaded', () => {
    applyTheme();
    bindThemeButton();
    attachHomeSearch();
    loadTrending();
    renderDestinationDetails();
});

