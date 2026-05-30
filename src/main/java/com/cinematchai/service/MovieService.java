package com.cinematchai.service;

import com.cinematchai.model.MovieResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final RestTemplate restTemplate;

    @Value("${tmdb.api.key:}")
    private String tmdbApiKey;

    @Value("${omdb.api.key:}")
    private String omdbApiKey;

    private String tmdbBase = "https://api.themoviedb.org/3";
    private String tmdbImageBase = "https://image.tmdb.org/t/p/w500";

    public MovieService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private Map<String, Object> fetchJson(String url) {
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<>() {
                }
        );
        return response.getBody();
    }

    @PostConstruct
    public void validateKeys() {
        if (tmdbApiKey == null || tmdbApiKey.isBlank()) {
            System.out.println("Warning: TMDB API key is not configured. Please set TMDB_API_KEY environment variable.");
        }
    }

    public List<Map<String, Object>> searchMovies(String query) {
        if (query == null || query.isBlank()) {
            return Collections.emptyList();
        }
        try {
            String url = String.format("%s/search/movie?api_key=%s&query=%s&language=en-US&page=1&include_adult=false", tmdbBase, tmdbApiKey, encode(query));
            return parseMovieList(fetchJson(url));
        } catch (HttpClientErrorException ex) {
            return fallbackOmdbSearch(query);
        }
    }

    public MovieResponse getMovieDetails(String movieId) {
        try {
            String url = String.format("%s/movie/%s?api_key=%s&language=en-US", tmdbBase, movieId, tmdbApiKey);
            Map<String, Object> body = fetchJson(url);
            if (body == null) return null;
            MovieResponse movie = mapMovieDetails(body);

            movie.setCast(fetchCast(movieId));
            movie.setDirector(fetchDirector(movieId));
            movie.setTrailerUrl(fetchTrailer(movieId));
            return movie;
        } catch (Exception ex) {
            return fallbackOmdbDetails(movieId);
        }
    }

    public List<Map<String, Object>> getTrendingMovies() {
        try {
            String url = String.format("%s/trending/movie/week?api_key=%s", tmdbBase, tmdbApiKey);
            return parseMovieList(fetchJson(url));
        } catch (Exception ex) {
            return Collections.emptyList();
        }
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> parseMovieList(Map<String, Object> body) {
        if (body == null || !body.containsKey("results")) {
            return Collections.emptyList();
        }
        List<Map<String, Object>> results = (List<Map<String, Object>>) body.get("results");
        return results.stream().map(this::normalizeMovieSummary).collect(Collectors.toList());
    }

    private Map<String, Object> normalizeMovieSummary(Map<String, Object> source) {
        String poster = Optional.ofNullable(source.get("poster_path")).map(Object::toString).orElse("");
        return Map.of(
                "id", source.getOrDefault("id", ""),
                "title", source.getOrDefault("title", source.getOrDefault("name", "")),
                "overview", source.getOrDefault("overview", ""),
                "poster_path", poster.isBlank() ? "" : tmdbImageBase + poster,
                "vote_average", source.getOrDefault("vote_average", 0),
                "release_date", source.getOrDefault("release_date", source.getOrDefault("first_air_date", "")),
                "popularity", source.getOrDefault("popularity", 0),
                "genre_ids", source.getOrDefault("genre_ids", Collections.emptyList())
        );
    }

    private MovieResponse mapMovieDetails(Map<String, Object> body) {
        MovieResponse movie = new MovieResponse();
        movie.setId(String.valueOf(body.get("id")));
        movie.setTitle(String.valueOf(body.getOrDefault("title", body.getOrDefault("name", ""))));
        movie.setOverview(String.valueOf(body.getOrDefault("overview", "")));
        movie.setRating(parseDouble(body.get("vote_average")));
        movie.setPopularity(parseDouble(body.get("popularity")));
        movie.setReleaseDate(String.valueOf(body.getOrDefault("release_date", "")));
        movie.setRuntime((int) parseDouble(body.getOrDefault("runtime", 0)));
        movie.setPosterPath(Optional.ofNullable(body.get("poster_path")).map(Object::toString).filter(s -> !s.isBlank()).map(s -> tmdbImageBase + s).orElse(""));
        movie.setGenres(parseGenres(body));
        return movie;
    }

    @SuppressWarnings("unchecked")
    private List<String> fetchCast(String movieId) {
        try {
            String url = String.format("%s/movie/%s/credits?api_key=%s&language=en-US", tmdbBase, movieId, tmdbApiKey);
            Map<String, Object> body = fetchJson(url);
            if (body == null || !body.containsKey("cast")) return List.of();
            List<Map<String, Object>> castList = (List<Map<String, Object>>) body.get("cast");
            return castList.stream()
                    .limit(8)
                    .map(entry -> String.valueOf(entry.getOrDefault("name", "")))
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            return List.of();
        }
    }

    @SuppressWarnings("unchecked")
    private String fetchDirector(String movieId) {
        try {
            String url = String.format("%s/movie/%s/credits?api_key=%s&language=en-US", tmdbBase, movieId, tmdbApiKey);
            Map<String, Object> body = fetchJson(url);
            if (body == null || !body.containsKey("crew")) return "Unknown";
            List<Map<String, Object>> crew = (List<Map<String, Object>>) body.get("crew");
            return crew.stream()
                    .filter(entry -> "Director".equals(entry.get("job")))
                    .map(entry -> String.valueOf(entry.getOrDefault("name", "")))
                    .findFirst()
                    .orElse("Unknown");
        } catch (Exception ex) {
            return "Unknown";
        }
    }

    @SuppressWarnings("unchecked")
    private String fetchTrailer(String movieId) {
        try {
            String url = String.format("%s/movie/%s/videos?api_key=%s&language=en-US", tmdbBase, movieId, tmdbApiKey);
            Map<String, Object> body = fetchJson(url);
            if (body == null || !body.containsKey("results")) return "";
            List<Map<String, Object>> videos = (List<Map<String, Object>>) body.get("results");
            return videos.stream()
                    .filter(entry -> "Trailer".equals(entry.get("type")) && "YouTube".equals(entry.get("site")))
                    .map(entry -> String.format("https://www.youtube.com/watch?v=%s", entry.get("key")))
                    .findFirst()
                    .orElse("");
        } catch (Exception ex) {
            return "";
        }
    }

    @SuppressWarnings("unchecked")
    private List<String> parseGenres(Map<String, Object> body) {
        if (body.containsKey("genres")) {
            List<Map<String, Object>> genres = (List<Map<String, Object>>) body.get("genres");
            return genres.stream().map(entry -> String.valueOf(entry.getOrDefault("name", ""))).collect(Collectors.toList());
        }
        return List.of();
    }

    private double parseDouble(Object value) {
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        try {
            return Double.parseDouble(String.valueOf(value));
        } catch (Exception ex) {
            return 0;
        }
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> fallbackOmdbSearch(String query) {
        if (omdbApiKey == null || omdbApiKey.isBlank()) {
            return Collections.emptyList();
        }
        try {
            String url = String.format("https://www.omdbapi.com/?apikey=%s&s=%s&type=movie", omdbApiKey, encode(query));
            Map<String, Object> body = fetchJson(url);
            if (body == null || !"True".equals(body.get("Response"))) {
                return Collections.emptyList();
            }
            List<Map<String, Object>> search = (List<Map<String, Object>>) body.get("Search");
            return search.stream().map(item -> Map.of(
                    "id", item.getOrDefault("imdbID", ""),
                    "title", item.getOrDefault("Title", ""),
                    "overview", "",
                    "poster_path", item.getOrDefault("Poster", ""),
                    "vote_average", 0,
                    "release_date", item.getOrDefault("Year", ""),
                    "popularity", 0,
                    "genre_ids", Collections.emptyList()
            )).collect(Collectors.toList());
        } catch (Exception ex) {
            return Collections.emptyList();
        }
    }

    private MovieResponse fallbackOmdbDetails(String movieId) {
        if (omdbApiKey == null || omdbApiKey.isBlank()) {
            return null;
        }
        try {
            String url = String.format("https://www.omdbapi.com/?apikey=%s&i=%s&plot=full", omdbApiKey, encode(movieId));
            Map<String, Object> body = fetchJson(url);
            if (body == null || !"True".equals(body.get("Response"))) {
                return null;
            }
            MovieResponse responseMovie = new MovieResponse();
            responseMovie.setId(String.valueOf(body.getOrDefault("imdbID", movieId)));
            responseMovie.setTitle(String.valueOf(body.getOrDefault("Title", "")));
            responseMovie.setOverview(String.valueOf(body.getOrDefault("Plot", "")));
            responseMovie.setPosterPath(String.valueOf(body.getOrDefault("Poster", "")));
            responseMovie.setRating(parseDouble(body.getOrDefault("imdbRating", 0)));
            responseMovie.setReleaseDate(String.valueOf(body.getOrDefault("Released", "")));
            responseMovie.setGenres(List.of(String.valueOf(body.getOrDefault("Genre", "")).split(", ")));
            responseMovie.setDirector(String.valueOf(body.getOrDefault("Director", "")));
            responseMovie.setCast(List.of(String.valueOf(body.getOrDefault("Actors", "")).split(", ")));
            return responseMovie;
        } catch (Exception ex) {
            return null;
        }
    }

    private String encode(String input) {
        return Optional.ofNullable(input).map(s -> s.replace(" ", "%20")).orElse("");
    }
}
