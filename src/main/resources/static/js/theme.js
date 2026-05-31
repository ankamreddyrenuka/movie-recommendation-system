const themeToggleButtons = document.querySelectorAll('#themeToggle');

function applyThemeMode() {
    const theme = localStorage.getItem('traveldna-theme') || 'dark';
    document.documentElement.dataset.theme = theme;
    themeToggleButtons.forEach(button => {
        if (button) button.textContent = theme === 'dark' ? 'Light' : 'Dark';
    });
}

function bindThemeToggle() {
    themeToggleButtons.forEach(button => {
        button.addEventListener('click', () => {
            const nextTheme = document.documentElement.dataset.theme === 'dark' ? 'light' : 'dark';
            localStorage.setItem('traveldna-theme', nextTheme);
            applyThemeMode();
        });
    });
}

window.addEventListener('DOMContentLoaded', () => {
    applyThemeMode();
    bindThemeToggle();
});
