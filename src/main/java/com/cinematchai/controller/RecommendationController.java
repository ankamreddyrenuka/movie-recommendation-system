package com.cinematchai.controller;

import com.cinematchai.model.RecommendationResponse;
import com.cinematchai.service.RecommendationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<List<RecommendationResponse>> getRecommendations(@PathVariable String movieId) {
        return ResponseEntity.ok(recommendationService.recommend(movieId));
    }
}
