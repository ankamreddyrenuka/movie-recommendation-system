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

    @GetMapping("/search")
    public ResponseEntity<List<Map<String, Object>>> searchDestinations(
            @RequestParam(name = "query", required = false) String query,
            @RequestParam(name = "region", required = false) String region,
            @RequestParam(name = "style", required = false) String style,
            @RequestParam(name = "budget", required = false) String budget,
            @RequestParam(name = "minRating", required = false) Double minRating,
            @RequestParam(name = "durationFrom", required = false) Integer durationFrom,
            @RequestParam(name = "durationTo", required = false) Integer durationTo
    ) {
        return ResponseEntity.ok(travelService.searchDestinations(query, region, style, budget, minRating, durationFrom, durationTo));
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
