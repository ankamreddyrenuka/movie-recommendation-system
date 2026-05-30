package com.cinematchai.controller;

import com.cinematchai.service.AnalyticsService;
import com.cinematchai.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;
    private final MovieService movieService;

    public AnalyticsController(AnalyticsService analyticsService, MovieService movieService) {
        this.analyticsService = analyticsService;
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAnalytics() {
        List<Map<String, Object>> trending = movieService.getTrendingMovies();
        return ResponseEntity.ok(analyticsService.buildAnalytics(trending));
    }
}
