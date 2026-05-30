package com.cinematchai.service;

import com.cinematchai.model.MovieResponse;
import com.cinematchai.model.RecommendationResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    private final MovieService movieService;
    private final AIRecommendationService aiRecommendationService;

    public RecommendationService(MovieService movieService, AIRecommendationService aiRecommendationService) {
        this.movieService = movieService;
        this.aiRecommendationService = aiRecommendationService;
    }

    public List<RecommendationResponse> recommend(String movieId) {
        List<Map<String, Object>> trending = movieService.getTrendingMovies();
        if (trending.isEmpty()) {
            return List.of();
        }
        MovieResponse source = movieService.getMovieDetails(movieId);
        if (source == null) {
            return List.of();
        }
        String primaryGenre = source.getGenres().isEmpty() ? "film" : source.getGenres().get(0);
        return trending.stream()
                .filter(movie -> !String.valueOf(movie.get("id")).equals(movieId))
                .map((Map<String, Object> movie) -> scoreRecommendation(source, movie, primaryGenre))
                .sorted(Comparator.comparingDouble(RecommendationResponse::getRating).reversed())
                .limit(6)
                .collect(Collectors.toList());
    }

    private RecommendationResponse scoreRecommendation(MovieResponse source, Map<String, Object> candidate, String primaryGenre) {
        List<String> candidateGenres = fetchGenreNames(candidate);
        double candidateRating = parseDouble(candidate.get("vote_average"));
        double candidatePopularity = parseDouble(candidate.get("popularity"));

        double genreMatch = candidateGenres.stream().anyMatch(g -> source.getGenres().contains(g)) ? 1.2 : 0.8;
        double ratingMatch = 1 - Math.abs(source.getRating() - candidateRating) / 10.0;
        double popularityMatch = candidatePopularity / Math.max(1, source.getPopularity());
        double score = genreMatch * ratingMatch * (0.4 + 0.6 * popularityMatch);

        String reason = aiRecommendationService.buildInsight(source.getTitle(), primaryGenre, source.getRating(), source.getPopularity());

        return new RecommendationResponse(
                String.valueOf(candidate.getOrDefault("id", "")),
                String.valueOf(candidate.getOrDefault("title", "Unknown")),
                reason,
                score * 10,
                candidatePopularity,
                String.valueOf(candidate.getOrDefault("poster_path", ""))
        );
    }

    private List<String> fetchGenreNames(Map<String, Object> movie) {
        Object genreIds = movie.get("genre_ids");
        if (genreIds instanceof List<?>) {
            return ((List<?>) genreIds).stream().map(Object::toString).collect(Collectors.toList());
        }
        return List.of();
    }

    private double parseDouble(Object value) {
        if (value instanceof Number) return ((Number) value).doubleValue();
        try {
            return Double.parseDouble(String.valueOf(value));
        } catch (Exception ex) {
            return 0;
        }
    }
}
