const summaryGrid = document.getElementById('summaryGrid');
const insightsList = document.getElementById('insightsList');
const topTrendingList = document.getElementById('topTrendingList');
const mapContainer = document.getElementById('apMap');
const categoryCanvas = document.getElementById('categoryChart');
const durationCanvas = document.getElementById('durationChart');
const ratingCanvas = document.getElementById('ratingChart');
const budgetCanvas = document.getElementById('budgetChart');
const visitorsCanvas = document.getElementById('visitorsChart');

function renderChart(canvas, config) {
    if (!canvas) return;
    new Chart(canvas, config);
}

function createCard(title, value, subtitle) {
    const card = document.createElement('article');
    card.className = 'summary-card glass';
    card.innerHTML = `
        <h3>${title}</h3>
        <p class="card-value">${value}</p>
        <span>${subtitle}</span>
    `;
    return card;
}

function renderSummary(summary) {
    if (!summaryGrid || !summary) return;
    summaryGrid.innerHTML = '';
    summaryGrid.appendChild(createCard('Destinations', summary.totalDestinations, 'Andhra Pradesh portfolio'));
    summaryGrid.appendChild(createCard('Avg. Rating', summary.averageRating, 'Trusted traveler score'));
    summaryGrid.appendChild(createCard('Avg. Cost/Day', `₹${summary.averageDailyCost}`, 'Budget estimation'));
    summaryGrid.appendChild(createCard('Avg. Stay', `${summary.averageTripDuration} days`, 'Recommended itinerary'));
    summaryGrid.appendChild(createCard('Popular District', summary.topDistrict, 'Most represented region'));
    summaryGrid.appendChild(createCard('Top Destination', summary.topDestination, 'Visitor interest'));
}

function renderInsights(insights) {
    if (!insightsList || !Array.isArray(insights)) return;
    insightsList.innerHTML = insights.map(item => `<li>${item}</li>`).join('');
}

function renderTopTrending(destinations) {
    if (!topTrendingList) return;
    topTrendingList.innerHTML = '';
    destinations.forEach(dest => {
        const item = document.createElement('div');
        item.className = 'ranking-row';
        item.innerHTML = `
            <span>${dest.name || 'Unknown'}</span>
            <div class="ranking-bar-wrapper">
                <div class="ranking-bar" style="width: ${Math.min(100, (dest.popularity || 0) / 1.5)}%"></div>
            </div>
            <span>⭐ ${dest.popularity || 0}</span>
        `;
        topTrendingList.appendChild(item);
    });
}

function renderMap(destinations) {
    if (!mapContainer || !window.L) return;
    const map = L.map('apMap', { scrollWheelZoom: false }).setView([16.5, 80.6], 7);
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '© OpenStreetMap contributors'
    }).addTo(map);

    destinations.forEach(dest => {
        const lat = parseFloat(dest.latitude || dest.lat || 0);
        const lng = parseFloat(dest.longitude || dest.lng || 0);
        if (!lat || !lng) return;
        const marker = L.marker([lat, lng]).addTo(map);
        marker.bindPopup(`
            <strong>${dest.name}</strong><br>
            ${dest.category || 'Destination'}<br>
            ${dest.district || dest.region || ''}<br>
            ⭐ ${dest.rating || 'N/A'}
        `);
    });
}

async function loadAnalytics() {
    try {
        const analytics = await Api.getAnalytics();
        renderSummary(analytics.summary);
        renderInsights(analytics.insights);
        renderTopTrending(analytics.topTrending || []);
        renderMap(analytics.topTrending || []);

        renderChart(categoryCanvas, {
            type: 'doughnut',
            data: {
                labels: Object.keys(analytics.categoryDistribution || {}),
                datasets: [{
                    data: Object.values(analytics.categoryDistribution || {}),
                    backgroundColor: ['#3b82f6', '#8b5cf6', '#ec4899', '#f97316', '#22c55e', '#c084fc']
                }]
            }
        });

        renderChart(durationCanvas, {
            type: 'bar',
            data: {
                labels: Object.keys(analytics.tripDurationAnalysis || {}),
                datasets: [{
                    label: 'Destinations',
                    data: Object.values(analytics.tripDurationAnalysis || {}),
                    backgroundColor: '#38bdf8'
                }]
            },
            options: { scales: { y: { beginAtZero: true } } }
        });

        renderChart(ratingCanvas, {
            type: 'radar',
            data: {
                labels: Object.keys(analytics.ratingsByCategory || {}),
                datasets: [{
                    label: 'Average Rating',
                    data: Object.values(analytics.ratingsByCategory || {}),
                    borderColor: '#f59e0b',
                    backgroundColor: 'rgba(245, 158, 11, 0.2)',
                    fill: true
                }]
            },
            options: { scales: { r: { beginAtZero: true, max: 5 } } }
        });

        renderChart(budgetCanvas, {
            type: 'doughnut',
            data: {
                labels: Object.keys(analytics.budgetBreakdown || {}),
                datasets: [{
                    data: Object.values(analytics.budgetBreakdown || {}),
                    backgroundColor: ['#f97316', '#22c55e', '#3082f1']
                }]
            }
        });

        renderChart(visitorsCanvas, {
            type: 'line',
            data: {
                labels: (analytics.monthlyVisitorsTrend || []).map(item => item.name),
                datasets: [{
                    label: 'Monthly Visitors',
                    data: (analytics.monthlyVisitorsTrend || []).map(item => item.monthlyVisitors),
                    borderColor: '#22c55e',
                    fill: true,
                    backgroundColor: 'rgba(34, 197, 94, 0.2)',
                    tension: 0.35
                }]
            },
            options: { scales: { y: { beginAtZero: true } } }
        });
    } catch (error) {
        console.error('Analytics error', error);
    }
}

window.addEventListener('DOMContentLoaded', loadAnalytics);

