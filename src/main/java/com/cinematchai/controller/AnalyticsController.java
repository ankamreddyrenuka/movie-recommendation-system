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
        List<Map<String, Object>> destinations = travelService.getAllDestinations();
        return ResponseEntity.ok(analyticsService.buildAnalytics(destinations));
    }

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getSummary() {
        return ResponseEntity.ok(analyticsService.buildSummary(travelService.getAllDestinations()));
    }

    @GetMapping("/category-distribution")
    public ResponseEntity<Map<String, Integer>> getCategoryDistribution() {
        return ResponseEntity.ok(analyticsService.categoryDistribution(travelService.getAllDestinations()));
    }

    @GetMapping("/district-popularity")
    public ResponseEntity<Map<String, Integer>> getDistrictPopularity() {
        return ResponseEntity.ok(analyticsService.districtPopularity(travelService.getAllDestinations()));
    }

    @GetMapping("/ratings-by-category")
    public ResponseEntity<Map<String, Double>> getRatingsByCategory() {
        return ResponseEntity.ok(analyticsService.ratingsByCategory(travelService.getAllDestinations()));
    }

    @GetMapping("/budget-breakdown")
    public ResponseEntity<Map<String, Integer>> getBudgetBreakdown() {
        return ResponseEntity.ok(analyticsService.budgetBreakdown(travelService.getAllDestinations()));
    }

    @GetMapping("/trip-duration")
    public ResponseEntity<Map<String, Integer>> getTripDurationAnalysis() {
        return ResponseEntity.ok(analyticsService.tripDurationAnalysis(travelService.getAllDestinations()));
    }

    @GetMapping("/monthly-visitors")
    public ResponseEntity<List<Map<String, Object>>> getMonthlyVisitorsTrend() {
        return ResponseEntity.ok(analyticsService.monthlyVisitorsTrend(travelService.getAllDestinations()));
    }

    @GetMapping("/top-trending")
    public ResponseEntity<List<Map<String, Object>>> getTopTrending() {
        return ResponseEntity.ok(analyticsService.topTrending(travelService.getAllDestinations()));
    }

    @GetMapping("/insights")
    public ResponseEntity<List<String>> getInsights() {
        return ResponseEntity.ok(analyticsService.buildInsights(travelService.getAllDestinations()));
    }
}
