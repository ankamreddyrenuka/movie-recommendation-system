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
    searchDestinations: params => {
        const query = new URLSearchParams();
        if (params?.query) query.append('query', params.query);
        if (params?.category) query.append('category', params.category);
        if (params?.district) query.append('district', params.district);
        if (params?.budget) query.append('budget', params.budget);
        if (params?.season) query.append('season', params.season);
        if (params?.minRating) query.append('minRating', params.minRating);
        if (params?.durationFrom) query.append('durationFrom', params.durationFrom);
        if (params?.durationTo) query.append('durationTo', params.durationTo);
        return fetchJSON(`${API_BASE}/destinations/search?${query.toString()}`);
    },
    getAllDestinations: () => fetchJSON(`${API_BASE}/destinations`),
    getDestinationsByCategory: category => fetchJSON(`${API_BASE}/destinations/category/${encodeURIComponent(category)}`),
    getDestinationsByDistrict: district => fetchJSON(`${API_BASE}/destinations/district/${encodeURIComponent(district)}`),
    getDestination: id => fetchJSON(`${API_BASE}/destinations/${encodeURIComponent(id)}`),
    getTrending: () => fetchJSON(`${API_BASE}/destinations/trending`),
    getAnalytics: () => fetchJSON(`${API_BASE}/analytics`),

    getRecommendations: destinationId => fetchJSON(`${API_BASE}/planner/recommendations/${encodeURIComponent(destinationId)}`),

    getSavedTrips: () => fetchJSON(`${API_BASE}/saved-trips`),
    addSavedTrip: payload => fetchJSON(`${API_BASE}/saved-trips`, { method: 'POST', body: JSON.stringify(payload) }),
    updateSavedTrip: (id, payload) => fetchJSON(`${API_BASE}/saved-trips/${id}`, { method: 'PUT', body: JSON.stringify(payload) }),
    deleteSavedTrip: id => fetch(`${API_BASE}/saved-trips/${id}`, { method: 'DELETE' }),

    getWishlist: () => fetchJSON(`${API_BASE}/wishlist`),
    addWishlist: payload => fetchJSON(`${API_BASE}/wishlist`, { method: 'POST', body: JSON.stringify(payload) }),
    updateWishlist: (id, payload) => fetchJSON(`${API_BASE}/wishlist/${id}`, { method: 'PUT', body: JSON.stringify(payload) }),
    deleteWishlist: id => fetch(`${API_BASE}/wishlist/${id}`, { method: 'DELETE' }),
};
