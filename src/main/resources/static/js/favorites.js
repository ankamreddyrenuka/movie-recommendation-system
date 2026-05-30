const favoritesList = document.getElementById('favoritesList');
const saveFavorite = document.getElementById('saveFavorite');

function renderFavoriteCard(movie) {
    const card = document.createElement('article');
    card.className = 'movie-card glass';
    card.innerHTML = `
        <img src="${movie.posterPath || 'https://via.placeholder.com/400x600?text=No+Image'}" alt="${movie.title}">
        <div class="movie-card-content">
            <h3>${movie.title}</h3>
            <p>${movie.genres || 'Genres not set'}</p>
            <div class="tag-row">
                <span>⭐ ${movie.rating}</span>
                <span>${movie.releaseDate || 'Unknown'}</span>
            </div>
            <div class="card-actions">
                <button class="secondary-button" data-delete="${movie.id}">Remove</button>
                <a class="card-button" href="/movie-details.html?id=${movie.movieId}">Details</a>
            </div>
        </div>
    `;
    return card;
}

async function refreshFavorites() {
    try {
        const favorites = await Api.getFavorites();
        favoritesList.innerHTML = '';
        favorites.forEach(movie => favoritesList.appendChild(renderFavoriteCard(movie)));
        favoritesList.querySelectorAll('[data-delete]').forEach(button => {
            button.addEventListener('click', async () => {
                await Api.deleteFavorite(button.dataset.delete);
                refreshFavorites();
            });
        });
    } catch (error) {
        favoritesList.innerHTML = `<div class="error-message">${error.message}</div>`;
    }
}

window.addEventListener('DOMContentLoaded', () => {
    refreshFavorites();
    saveFavorite?.addEventListener('click', async () => {
        const payload = {
            movieId: document.getElementById('favoriteMovieId').value.trim(),
            title: document.getElementById('favoriteTitle').value.trim(),
            posterPath: document.getElementById('favoritePoster').value.trim(),
            genres: document.getElementById('favoriteGenres').value.trim(),
            rating: parseFloat(document.getElementById('favoriteRating').value) || 0,
            releaseDate: document.getElementById('favoriteRelease').value.trim()
        };
        try {
            await Api.addFavorite(payload);
            refreshFavorites();
        } catch (error) {
            alert(`Unable to save favorite: ${error.message}`);
        }
    });
});
