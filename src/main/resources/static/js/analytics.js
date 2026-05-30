const genreCanvas = document.getElementById('genreChart');
const ratingsCanvas = document.getElementById('ratingsChart');
const popularityCanvas = document.getElementById('popularityChart');
const topMoviesCanvas = document.getElementById('topMoviesChart');
const yearTrendCanvas = document.getElementById('yearTrendChart');

function renderChart(canvas, config) {
    if (!canvas) return;
    new Chart(canvas, config);
}

async function loadAnalytics() {
    try {
        const analytics = await Api.getAnalytics();
        const genreLabels = Object.keys(analytics.genreDistribution || {});
        const genreValues = Object.values(analytics.genreDistribution || {});
        const ratingLabels = Object.keys(analytics.ratingsHistogram || {});
        const ratingValues = Object.values(analytics.ratingsHistogram || {});
        const popularityValues = analytics.popularity || [];
        const topMovies = analytics.topMovies || [];
        const yearLabels = Object.keys(analytics.releaseYearTrend || {});
        const yearValues = Object.values(analytics.releaseYearTrend || {});

        renderChart(genreCanvas, {
            type: 'doughnut',
            data: {
                labels: genreLabels,
                datasets: [{ data: genreValues, backgroundColor: ['#3b82f6', '#8b5cf6', '#ec4899', '#f97316', '#22c55e'] }]
            }
        });

        renderChart(ratingsCanvas, {
            type: 'bar',
            data: {
                labels: ratingLabels,
                datasets: [{ label: 'Movie Counts', data: ratingValues, backgroundColor: '#38bdf8' }]
            },
            options: { scales: { y: { beginAtZero: true } } }
        });

        renderChart(popularityCanvas, {
            type: 'line',
            data: {
                labels: popularityValues.map((_, index) => index + 1),
                datasets: [{ label: 'Popularity', data: popularityValues, borderColor: '#f59e0b', fill: false, tension: 0.35 }]
            },
            options: { scales: { x: { display: false } } }
        });

        renderChart(topMoviesCanvas, {
            type: 'bar',
            data: {
                labels: topMovies.map(movie => movie.title),
                datasets: [{ label: 'Popularity', data: topMovies.map(movie => movie.popularity), backgroundColor: '#a78bfa' }]
            },
            options: { indexAxis: 'y', scales: { x: { beginAtZero: true } } }
        });

        renderChart(yearTrendCanvas, {
            type: 'line',
            data: {
                labels: yearLabels,
                datasets: [{ label: 'Releases', data: yearValues, borderColor: '#22c55e', fill: true, backgroundColor: 'rgba(34, 197, 94, 0.2)' }]
            },
            options: { scales: { y: { beginAtZero: true } } }
        });
    } catch (error) {
        console.error('Analytics error', error);
    }
}

window.addEventListener('DOMContentLoaded', loadAnalytics);
