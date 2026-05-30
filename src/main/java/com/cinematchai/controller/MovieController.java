package com.cinematchai.controller;

import com.cinematchai.model.MovieResponse;
import com.cinematchai.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<Map<String, Object>>> searchMovies(@RequestParam String query) {
        return ResponseEntity.ok(movieService.searchMovies(query));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> getMovieDetails(@PathVariable String id) {
        MovieResponse movie = movieService.getMovieDetails(id);
        return ResponseEntity.ok(movie);
    }

    @GetMapping("/trending")
    public ResponseEntity<List<Map<String, Object>>> getTrendingMovies() {
        return ResponseEntity.ok(movieService.getTrendingMovies());
    }
}
