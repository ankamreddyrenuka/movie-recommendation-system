package com.cinematchai.controller;

import com.cinematchai.service.AnalyticsService;
import com.cinematchai.service.TravelService;
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
    private final TravelService travelService;

    public AnalyticsController(AnalyticsService analyticsService, TravelService travelService) {
        this.analyticsService = analyticsService;
        this.travelService = travelService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAnalytics() {
        List<Map<String, Object>> trending = travelService.getTrendingDestinations();
        return ResponseEntity.ok(analyticsService.buildAnalytics(trending));
    }
}
