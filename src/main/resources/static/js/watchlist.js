const wishlistItems = document.getElementById('watchlistItems');
const saveWishlist = document.getElementById('saveWatchlist');

function renderWishlistCard(item) {
    const card = document.createElement('article');
    card.className = 'destination-card glass';
    const img = item.imageUrl || item.image_url || 'https://via.placeholder.com/400x300?text=No+Image';
    const name = item.name || item.title || 'Unknown';
    card.innerHTML = `
        <img src="${img}" alt="${name}">
<div class="destination-card-content">
            <h3>${name}</h3>
            <p>${item.notes || 'No notes'}</p>
            <div class="tag-row">
                <span>${item.priority || 'Normal'}</span>
            </div>
            <div class="card-actions">
                <button class="secondary-button" data-delete="${item.id ?? item.destinationId ?? ''}">Remove</button>

            </div>
        </div>
    `;
    return card;
}

async function refreshWishlist() {
    try {
        const items = await Api.getWishlist();
        wishlistItems.innerHTML = '';
        items.forEach(item => wishlistItems.appendChild(renderWishlistCard(item)));
        wishlistItems.querySelectorAll('[data-delete]').forEach(button => {
            button.addEventListener('click', async () => {
                const id = button.dataset.delete;
                if (!id) return;
                await Api.deleteWishlist(id);
                refreshWishlist();

            });
        });
    } catch (error) {
        wishlistItems.innerHTML = `<div class="error-message">${error.message}</div>`;
    }
}

window.addEventListener('DOMContentLoaded', () => {
    refreshWishlist();
    saveWishlist?.addEventListener('click', async () => {
        const payload = {
            destinationId: document.getElementById('watchlistDestinationId').value.trim(),
            name: document.getElementById('watchlistTitle').value.trim(),
            imageUrl: document.getElementById('watchlistPoster').value.trim(),
            notes: document.getElementById('watchlistNotes').value.trim(),
            priority: document.getElementById('watchlistStatus').value.trim() || 'Normal'
        };
        try {
            await Api.addWishlist(payload);
            refreshWishlist();
        } catch (error) {
            alert(`Unable to save item: ${error.message}`);
        }
    });
});
