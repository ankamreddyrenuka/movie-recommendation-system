# TravelDNA – AI Powered Personalized Travel Planner

## Project Overview
TravelDNA is an AI-powered personalized travel planner and discovery platform. It pairs a lightweight vanilla-JS frontend with a Java 21 Spring Boot backend (Maven) and an H2 datastore for demo purposes.

The platform offers destination search, trending discovery, AI trip recommendations, saved trips (favorites), wishlist management, analytics dashboards, destination comparison, and a responsive glass UI.

## Architecture
- Frontend: HTML, CSS, Vanilla JavaScript, Fetch API, DOM Manipulation
- Backend: Java 21, Spring Boot 3, Maven, H2 Database, REST APIs
- Layers: Controller, Service, Repository, Model, DTO, Config
- Deployment: Render

## Folder Structure
```
traveldna/
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
│       ├── destination-details.html
│       ├── trip-planner.html
│       ├── travel-analytics.html
│       ├── saved-trips.html
│       ├── wishlist.html
│       ├── compare.html

│       ├── css/
│       └── js/
└── README.md
```

## API Documentation
### Destination Endpoints
- `GET /api/destinations/search?query={query}`
- `GET /api/destinations/{id}`
- `GET /api/destinations/trending`

### Planner / Recommendations
- `GET /api/planner/recommendations/{destinationId}`

### Analytics Endpoint
- `GET /api/analytics`

### Saved Trips (favorites) CRUD
- `POST /api/saved-trips`
- `GET /api/saved-trips`
- `PUT /api/saved-trips/{id}`
- `DELETE /api/saved-trips/{id}`

### Wishlist CRUD
- `POST /api/wishlist`
- `GET /api/wishlist`
- `PUT /api/wishlist/{id}`
- `DELETE /api/wishlist/{id}`

## Setup Instructions
1. Install Java 21 and Maven.
2. Clone or open the repository.
3. No external API keys are required for the demo seed data. If you integrate external providers, set their keys here.
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
3. Link the GitHub repository and set any required environment variables for external integrations.
4. Deploy.

## Frontend Pages
- `index.html`
- `search.html`
- `destination-details.html`
- `trip-planner.html`
- `travel-analytics.html`
- `saved-trips.html` (Saved Trips)
- `wishlist.html` (Wishlist)

- `compare.html`

## Features
- Destination search with images, region hints, popularity, budget, and overview
- Destination details with highlights, duration, travel style, and popularity
- Trending destinations dashboard built with DOM rendering
- Recommendation engine using tags, popularity and budget similarity
- Saved Trips CRUD powered by H2 database
- Wishlist CRUD with priority/status
- Destination comparison view
- Analytics dashboard using Chart.js
- Dark mode/light mode theme toggle
- Responsive glassmorphism UI

## Future Enhancements
- Add user authentication and personalized profiles
- Improve voice search accuracy with a speech service fallback
- Add infinite scrolling and lazy loading for search/trending results
- Add trip quiz and mood-based recommendations
- Extend analytics to actor insights and watch history

## Notes
This repository is GitHub-ready and designed for a direct Render deployment using `render.yaml`.
- Destination search with images, region hints, popularity, budget, and overview
- Destination details with highlights, duration, travel style, and popularity
- Trending destinations dashboard built with DOM rendering
- Recommendation engine using tags, popularity and budget similarity
- Saved Trips CRUD powered by H2 database
- Wishlist CRUD with priority/status
- Destination comparison view
