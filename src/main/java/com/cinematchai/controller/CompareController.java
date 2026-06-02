package com.cinematchai.controller;

import com.cinematchai.dto.CompareRequest;
import com.cinematchai.model.DestinationResponse;
import com.cinematchai.service.TravelService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compare")
public class CompareController {

    private final TravelService travelService;

    public CompareController(TravelService travelService) {
        this.travelService = travelService;
    }

    @PostMapping
    public ResponseEntity<List<DestinationResponse>> compare(@Valid @RequestBody CompareRequest request) {
        List<DestinationResponse> comparison = travelService.compareDestinations(request.getDestinationIds());
        if (comparison.size() < 2) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(comparison);
    }
}
