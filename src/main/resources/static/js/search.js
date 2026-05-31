const searchInput = document.getElementById('searchInput');
const categoryFilter = document.getElementById('categoryFilter');
const districtFilter = document.getElementById('districtFilter');
const budgetFilter = document.getElementById('budgetFilter');
const seasonFilter = document.getElementById('seasonFilter');
const ratingFilter = document.getElementById('ratingFilter');
const durationFrom = document.getElementById('durationFrom');
const durationTo = document.getElementById('durationTo');
const searchButton = document.getElementById('searchButton');
const searchResults = document.getElementById('searchResults');
const paginationControls = document.getElementById('paginationControls');

let currentResults = [];
let currentPage = 1;
const resultsPerPage = 8;

function renderSearchResult(dest) {
    const card = document.createElement('article');
    card.className = 'destination-card glass';
    const img = dest.image_url || dest.imageUrl || 'https://via.placeholder.com/400x300?text=No+Image';
    const name = dest.name || dest.title || 'Unknown';
    const category = dest.category || dest.travel_style || 'Destination';
    const district = dest.district || dest.region || '';
    const rating = dest.rating || 'N/A';
    const cost = dest.avg_cost_per_day ? `₹${dest.avg_cost_per_day}/day` : '';

    card.innerHTML = `
        <img src="${img}" alt="${name}">
        <div class="destination-card-content">
            <h3>${name}</h3>
            <p>${category} · ${district}</p>
            <div class="rating-row">
                <span class="rating-pill">⭐ ${rating}</span>
                <span class="cost-pill">${cost}</span>
            </div>
            <a class="card-button" href="/destination-details.html?id=${encodeURIComponent(dest.id || dest.destinationId || '')}">Details</a>
        </div>
    `;
    return card;
}

function renderPagination() {
    if (!paginationControls) return;
    const pageCount = Math.ceil(currentResults.length / resultsPerPage);
    if (pageCount <= 1) {
        paginationControls.innerHTML = '';
        return;
    }

    paginationControls.innerHTML = '';
    for (let page = 1; page <= pageCount; page++) {
        const button = document.createElement('button');
        button.textContent = page;
        button.className = page === currentPage ? 'pagination-button active' : 'pagination-button';
        button.addEventListener('click', () => updatePage(page));
        paginationControls.appendChild(button);
    }
}

function updatePage(page) {
    currentPage = page;
    const start = (page - 1) * resultsPerPage;
    const pageResults = currentResults.slice(start, start + resultsPerPage);
    searchResults.innerHTML = '';
    pageResults.forEach(result => searchResults.appendChild(renderSearchResult(result)));
    renderPagination();
}

async function performSearch(params) {
    searchResults.innerHTML = '<div class="loading-card">Searching destinations...</div>';
    try {
        currentResults = await Api.searchDestinations(params);
        if (!currentResults || !currentResults.length) {
            searchResults.innerHTML = '<div class="empty-state">No destinations found.</div>';
            paginationControls.innerHTML = '';
            return;
        }
        currentPage = 1;
        updatePage(1);
    } catch (error) {
        searchResults.innerHTML = `<div class="error-message">${error.message}</div>`;
        paginationControls.innerHTML = '';
    }
}

function buildSearchParams() {
    return {
        query: searchInput?.value.trim() || '',
        category: categoryFilter?.value || '',
        district: districtFilter?.value || '',
        budget: budgetFilter?.value || '',
        season: seasonFilter?.value || '',
        minRating: ratingFilter?.value ? parseFloat(ratingFilter.value) : null,
        durationFrom: durationFrom?.value ? parseInt(durationFrom.value, 10) : null,
        durationTo: durationTo?.value ? parseInt(durationTo.value, 10) : null,
    };
}

function updateUrl(params) {
    const searchParams = new URLSearchParams();
    Object.entries(params).forEach(([key, value]) => {
        if (value !== null && value !== undefined && value !== '') {
            searchParams.append(key, value);
        }
    });
    history.replaceState(null, '', `/search.html?${searchParams.toString()}`);
}

window.addEventListener('DOMContentLoaded', () => {
    const params = new URLSearchParams(window.location.search);
    if (searchInput) searchInput.value = params.get('query') || '';
    if (categoryFilter) categoryFilter.value = params.get('category') || '';
    if (districtFilter) districtFilter.value = params.get('district') || '';
    if (budgetFilter) budgetFilter.value = params.get('budget') || '';
    if (seasonFilter) seasonFilter.value = params.get('season') || '';
    if (ratingFilter) ratingFilter.value = params.get('minRating') || '';
    if (durationFrom) durationFrom.value = params.get('durationFrom') || '';
    if (durationTo) durationTo.value = params.get('durationTo') || '';

    const queryParams = buildSearchParams();
    const hasFilter = Object.values(queryParams).some(value => value);
    if (hasFilter) {
        performSearch(queryParams);
    }

    searchButton?.addEventListener('click', () => {
        const params = buildSearchParams();
        updateUrl(params);
        performSearch(params);
    });

    searchInput?.addEventListener('keydown', event => {
        if (event.key === 'Enter') {
            searchButton.click();
        }
    });
});
