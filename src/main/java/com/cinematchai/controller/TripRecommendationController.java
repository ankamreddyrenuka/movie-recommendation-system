package com.cinematchai.controller;

import com.cinematchai.model.TripRecommendationResponse;
import com.cinematchai.service.TripPlannerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/planner")
public class TripRecommendationController {

    private final TripPlannerService tripPlannerService;

    public TripRecommendationController(TripPlannerService tripPlannerService) {
        this.tripPlannerService = tripPlannerService;
    }

    @GetMapping("/recommendations/{destinationId}")
    public ResponseEntity<List<TripRecommendationResponse>> getRecommendations(@PathVariable String destinationId) {
        return ResponseEntity.ok(tripPlannerService.recommend(destinationId));
    }
}
