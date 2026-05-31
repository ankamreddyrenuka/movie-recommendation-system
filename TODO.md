# TravelDNA completion TODO

## Step 1 — Rename project completely to TravelDNA
- [x] pom.xml: change artifactId to `traveldna`
- [x] Dockerfile: update jar filename to `target/traveldna-1.0.0.jar`
- [x] render.yaml: update startCommand to `java -jar target/traveldna-1.0.0.jar`
- [x] README.md: update project naming + remove outdated movie wording
- [ ] Frontend browser titles / page titles: align to TravelDNA branding



## Step 2 — Remove ALL remaining movie references
- [ ] Run repo-wide search for: movie, Movie, moviedetails, recommendation, recommendations, watchlist (if movie-related), favorites (if movie-related)
- [ ] Update any lingering HTML/JS/CSS text and filenames only if they’re actually still movie-based

## Step 3 — Frontend redesign for TravelDNA
- [ ] Home Page: hero carousel + featured/popular sections + modern nav/footer
- [ ] Destinations Page: destinations list + search suggestions
- [ ] Destination Details Page: destination highlights + duration/budget/travel style
- [ ] Saved Trips Page: modern cards with images + delete + empty state
- [ ] Wishlist Page: wishlist destinations UI
- [ ] Analytics Page: Chart.js integration

## Step 4 — Add modern travel UI interactivity
- [ ] Dark/Light mode toggle wiring
- [ ] Budget slider + Duration slider
- [ ] Interest selection chips
- [ ] Search suggestions
- [ ] Toast notifications
- [ ] Skeleton loaders
- [ ] Infinite scroll

## Step 5 — Hero Carousel destinations
- [ ] Bali, Goa, Kerala, Paris, Tokyo, Dubai

## Step 6 — Final verification

- [ ] Frontend routes match backend APIs
- [ ] Dockerfile matches artifact name
- [ ] render.yaml matches artifact name

