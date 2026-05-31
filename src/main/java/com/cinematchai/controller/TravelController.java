package com.cinematchai.controller;

import com.cinematchai.model.DestinationResponse;
import com.cinematchai.service.TravelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/destinations")
public class TravelController {

    private final TravelService travelService;

    public TravelController(TravelService travelService) {
        this.travelService = travelService;
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllDestinations() {
        return ResponseEntity.ok(travelService.getAllDestinations());
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Map<String, Object>>> getByCategory(@PathVariable String category) {
        return ResponseEntity.ok(travelService.getDestinationsByCategory(category));
    }

    @GetMapping("/district/{district}")
    public ResponseEntity<List<Map<String, Object>>> getByDistrict(@PathVariable String district) {
        return ResponseEntity.ok(travelService.getDestinationsByDistrict(district));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Map<String, Object>>> searchDestinations(
            @RequestParam(name = "query", required = false) String query,
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "district", required = false) String district,
            @RequestParam(name = "budget", required = false) String budget,
            @RequestParam(name = "season", required = false) String season,
            @RequestParam(name = "minRating", required = false) Double minRating,
            @RequestParam(name = "durationFrom", required = false) Integer durationFrom,
            @RequestParam(name = "durationTo", required = false) Integer durationTo
    ) {
        return ResponseEntity.ok(travelService.searchDestinations(query, category, district, budget, season, minRating, durationFrom, durationTo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DestinationResponse> getDestinationDetails(@PathVariable String id) {
        DestinationResponse destination = travelService.getDestinationDetails(id);
        return ResponseEntity.ok(destination);
    }

    @GetMapping("/trending")
    public ResponseEntity<List<Map<String, Object>>> getTrendingDestinations() {
        return ResponseEntity.ok(travelService.getTrendingDestinations());
    }

    @GetMapping("/featured")
    public ResponseEntity<List<Map<String, Object>>> getFeaturedDestinations() {
        return ResponseEntity.ok(travelService.getFeaturedDestinations());
    }

    @GetMapping("/suggestions")
    public ResponseEntity<List<Map<String, Object>>> getSuggestions(@RequestParam(name = "query", required = false) String query) {
        return ResponseEntity.ok(travelService.getSuggestions(query));
    }
}
