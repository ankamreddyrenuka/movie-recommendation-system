const savedTripsList = document.getElementById('favoritesList');
const saveSavedTrip = document.getElementById('saveFavorite');

function renderSavedTripCard(trip) {
    const card = document.createElement('article');
    card.className = 'destination-card glass';
    const img = trip.imageUrl || trip.image_url || 'https://via.placeholder.com/400x300?text=No+Image';
    const name = trip.name || trip.title || 'Unknown';
    const meta = trip.region || trip.country || '';
    card.innerHTML = `
        <img src="${img}" alt="${name}">
<div class="destination-card-content">
            <h3>${name}</h3>
            <p>${meta}</p>
            <div class="tag-row">
                <span>⭐ ${trip.rating || 'N/A'}</span>
                <span>${trip.recommendedDuration ? trip.recommendedDuration + ' days' : ''}</span>
            </div>
            <div class="card-actions">
                <button class="secondary-button" data-delete="${trip.id}">Remove</button>
                <a class="card-button" href="/destination-details.html?id=${trip.destinationId}">Details</a>
            </div>
        </div>
    `;
    return card;
}

async function refreshSavedTrips() {
    try {
        const trips = await Api.getSavedTrips();
        savedTripsList.innerHTML = '';
        trips.forEach(trip => savedTripsList.appendChild(renderSavedTripCard(trip)));
        savedTripsList.querySelectorAll('[data-delete]').forEach(button => {
            button.addEventListener('click', async () => {
                await Api.deleteSavedTrip(button.dataset.delete);
                refreshSavedTrips();
            });
        });
    } catch (error) {
        savedTripsList.innerHTML = `<div class="error-message">${error.message}</div>`;
    }
}

window.addEventListener('DOMContentLoaded', () => {
    refreshSavedTrips();
    saveSavedTrip?.addEventListener('click', async () => {
        const payload = {
            destinationId: document.getElementById('favoriteDestinationId').value.trim(),
            name: document.getElementById('favoriteTitle').value.trim(),
            imageUrl: document.getElementById('favoritePoster').value.trim(),
            notes: document.getElementById('favoriteGenres').value.trim(),
        };
        try {
            await Api.addSavedTrip(payload);
            refreshSavedTrips();
        } catch (error) {
            alert(`Unable to save trip: ${error.message}`);
        }
    });
});
