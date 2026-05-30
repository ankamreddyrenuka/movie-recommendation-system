const watchlistItems = document.getElementById('watchlistItems');
const saveWatchlist = document.getElementById('saveWatchlist');

function renderWatchlistCard(item) {
    const card = document.createElement('article');
    card.className = 'movie-card glass';
    card.innerHTML = `
        <img src="${item.posterPath || 'https://via.placeholder.com/400x600?text=No+Image'}" alt="${item.title}">
        <div class="movie-card-content">
            <h3>${item.title}</h3>
            <p>${item.status || 'No status'}</p>
            <div class="tag-row">
                <span>${item.watched ? 'Watched' : 'Unwatched'}</span>
                <span>${item.notes || 'No notes'}</span>
            </div>
            <div class="card-actions">
                <button class="secondary-button" data-toggle="${item.id}">${item.watched ? 'Mark Unwatched' : 'Mark Watched'}</button>
                <button class="secondary-button" data-delete="${item.id}">Remove</button>
            </div>
        </div>
    `;
    return card;
}

async function refreshWatchlist() {
    try {
        const items = await Api.getWatchlist();
        watchlistItems.innerHTML = '';
        items.forEach(item => watchlistItems.appendChild(renderWatchlistCard(item)));
        watchlistItems.querySelectorAll('[data-delete]').forEach(button => {
            button.addEventListener('click', async () => {
                await Api.deleteWatchlist(button.dataset.delete);
                refreshWatchlist();
            });
        });
        watchlistItems.querySelectorAll('[data-toggle]').forEach(button => {
            button.addEventListener('click', async () => {
                const id = button.dataset.toggle;
                const item = items.find(w => String(w.id) === id);
                await Api.updateWatchlist(id, {
                    movieId: item.movieId,
                    title: item.title,
                    posterPath: item.posterPath,
                    status: item.status,
                    notes: item.notes,
                    watched: !item.watched
                });
                refreshWatchlist();
            });
        });
    } catch (error) {
        watchlistItems.innerHTML = `<div class="error-message">${error.message}</div>`;
    }
}

window.addEventListener('DOMContentLoaded', () => {
    refreshWatchlist();
    saveWatchlist?.addEventListener('click', async () => {
        const payload = {
            movieId: document.getElementById('watchlistMovieId').value.trim(),
            title: document.getElementById('watchlistTitle').value.trim(),
            posterPath: document.getElementById('watchlistPoster').value.trim(),
            status: document.getElementById('watchlistStatus').value.trim() || 'To Watch',
            notes: document.getElementById('watchlistNotes').value.trim(),
            watched: false
        };
        try {
            await Api.addWatchlist(payload);
            refreshWatchlist();
        } catch (error) {
            alert(`Unable to save item: ${error.message}`);
        }
    });
});
