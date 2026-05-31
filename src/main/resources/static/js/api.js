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
    // Destinations / search
    searchDestinations: query => fetchJSON(`${API_BASE}/destinations/search?query=${encodeURIComponent(query)}`),
    getDestination: id => fetchJSON(`${API_BASE}/destinations/${encodeURIComponent(id)}`),
    getTrending: () => fetchJSON(`${API_BASE}/destinations/trending`),
    // Planner recommendations
    getRecommendations: destinationId => fetchJSON(`${API_BASE}/planner/recommendations/${encodeURIComponent(destinationId)}`),
    getAnalytics: () => fetchJSON(`${API_BASE}/analytics`),

    // Saved trips (replaces favorites)
    getSavedTrips: () => fetchJSON(`${API_BASE}/saved-trips`),
    addSavedTrip: payload => fetchJSON(`${API_BASE}/saved-trips`, { method: 'POST', body: JSON.stringify(payload) }),
    updateSavedTrip: (id, payload) => fetchJSON(`${API_BASE}/saved-trips/${id}`, { method: 'PUT', body: JSON.stringify(payload) }),
    deleteSavedTrip: id => fetch(`${API_BASE}/saved-trips/${id}`, { method: 'DELETE' }),

    // Wishlist
    getWishlist: () => fetchJSON(`${API_BASE}/wishlist`),
    addWishlist: payload => fetchJSON(`${API_BASE}/wishlist`, { method: 'POST', body: JSON.stringify(payload) }),
    updateWishlist: (id, payload) => fetchJSON(`${API_BASE}/wishlist/${id}`, { method: 'PUT', body: JSON.stringify(payload) }),
    deleteWishlist: id => fetch(`${API_BASE}/wishlist/${id}`, { method: 'DELETE' }),
};
