# CineMatch AI – Movie Recommendation & Analytics Platform

## Project Overview
CineMatch AI is a production-ready movie recommendation and analytics platform built with a pure frontend using HTML, CSS, and vanilla JavaScript, and a Java 21 Spring Boot backend with REST APIs and layered architecture.

The platform offers search, trending discovery, AI-generated recommendation insights, favorites management, watchlist CRUD, analytics dashboards, comparison tools, and responsive dark/light glassmorphism design.

## Architecture
- Frontend: HTML, CSS, Vanilla JavaScript, Fetch API, DOM Manipulation
- Backend: Java 21, Spring Boot 3, Maven, H2 Database, REST APIs
- Layers: Controller, Service, Repository, Model, DTO, Config
- Deployment: Render

## Folder Structure
```
movie-recommendation-system/
├── pom.xml
├── render.yaml
├── src/main/java/com/cinematchai/
│   ├── config/
│   ├── controller/
│   ├── dto/
│   ├── exception/
│   ├── model/
│   ├── repository/
│   ├── service/
│   └── CineMatchAiApplication.java
├── src/main/resources/
│   ├── application.properties
│   └── static/
│       ├── index.html
│       ├── search.html
│       ├── movie-details.html
│       ├── recommendations.html
│       ├── analytics.html
│       ├── favorites.html
│       ├── watchlist.html
│       ├── compare.html
│       ├── css/
│       └── js/
└── README.md
```

## API Documentation
### Movie Endpoints
- `GET /api/movies/search?query={query}`
- `GET /api/movies/{id}`
- `GET /api/movies/trending`

### Recommendation Endpoint
- `GET /api/recommendations/{movieId}`

### Analytics Endpoint
- `GET /api/analytics`

### Favorites CRUD
- `POST /api/favorites`
- `GET /api/favorites`
- `PUT /api/favorites/{id}`
- `DELETE /api/favorites/{id}`

### Watchlist CRUD
- `POST /api/watchlist`
- `GET /api/watchlist`
- `PUT /api/watchlist/{id}`
- `DELETE /api/watchlist/{id}`

## Setup Instructions
1. Install Java 21 and Maven.
2. Clone or open the repository.
3. Set environment variables:
   - `TMDB_API_KEY`
   - `OMDB_API_KEY` (optional fallback)
4. Build the project:
   ```bash
   mvn clean install
   ```
5. Run the app:
   ```bash
   mvn spring-boot:run
   ```
6. Open `http://localhost:8080/index.html` in your browser.

## Render Deployment Steps
1. Add `render.yaml` to the repository.
2. Create a new Render Web Service.
3. Link the GitHub repository.
4. Set environment secrets on Render:
   - `TMDB_API_KEY`
   - `OMDB_API_KEY`
5. Deploy.

## Frontend Pages
- `index.html`
- `search.html`
- `movie-details.html`
- `recommendations.html`
- `analytics.html`
- `favorites.html`
- `watchlist.html`
- `compare.html`

## Features
- Movie search with poster, title, genre hints, rating, release date, and overview
- Movie details with cast, runtime, director, trailer, ratings, and popularity
- Trending movies dashboard built with DOM rendering
- Recommendation engine using genre, rating, and popularity similarity
- Favorites CRUD powered by H2 database
- Watchlist CRUD with watched/unwatched status
- Movie comparison view
- Analytics dashboard using Chart.js
- Dark mode/light mode theme toggle
- Responsive glassmorphism UI

## Future Enhancements
- Add user authentication and personalized profiles
- Improve voice search accuracy with a speech service fallback
- Add infinite scrolling and lazy loading for search/trending results
- Add movie quiz and mood-based recommendations
- Extend analytics to actor insights and watch history

## Notes
This repository is GitHub-ready and designed for a direct Render deployment using `render.yaml`.
