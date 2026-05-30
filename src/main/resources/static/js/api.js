const API_BASE = '/api';

async function fetchJSON(url, options = {}) {
    const response = await fetch(url, {
        headers: { 'Content-Type': 'application/json' },
        ...options
    });
    if (!response.ok) {
        const errorData = await response.json().catch(() => ({}));
        const message = errorData.message || response.statusText;
        throw new Error(message);
    }
    return response.json();
}

const Api = {
    searchMovies: query => fetchJSON(`${API_BASE}/movies/search?query=${encodeURIComponent(query)}`),
    getMovie: id => fetchJSON(`${API_BASE}/movies/${encodeURIComponent(id)}`),
    getTrending: () => fetchJSON(`${API_BASE}/movies/trending`),
    getRecommendations: movieId => fetchJSON(`${API_BASE}/recommendations/${encodeURIComponent(movieId)}`),
    getAnalytics: () => fetchJSON(`${API_BASE}/analytics`),
    getFavorites: () => fetchJSON(`${API_BASE}/favorites`),
    addFavorite: payload => fetchJSON(`${API_BASE}/favorites`, { method: 'POST', body: JSON.stringify(payload) }),
    updateFavorite: (id, payload) => fetchJSON(`${API_BASE}/favorites/${id}`, { method: 'PUT', body: JSON.stringify(payload) }),
    deleteFavorite: id => fetch(`${API_BASE}/favorites/${id}`, { method: 'DELETE' }),
    getWatchlist: () => fetchJSON(`${API_BASE}/watchlist`),
    addWatchlist: payload => fetchJSON(`${API_BASE}/watchlist`, { method: 'POST', body: JSON.stringify(payload) }),
    updateWatchlist: (id, payload) => fetchJSON(`${API_BASE}/watchlist/${id}`, { method: 'PUT', body: JSON.stringify(payload) }),
    deleteWatchlist: id => fetch(`${API_BASE}/watchlist/${id}`, { method: 'DELETE' }),
};
