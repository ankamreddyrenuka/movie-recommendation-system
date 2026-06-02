package com.cinematchai.controller;

import com.cinematchai.dto.SavedTripDTO;
import com.cinematchai.model.SavedTrip;
import com.cinematchai.service.SavedTripsService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/saved-trips")
public class SavedTripsController {

    private final SavedTripsService savedTripsService;

    public SavedTripsController(SavedTripsService savedTripsService) {
        this.savedTripsService = savedTripsService;
    }

    @PostMapping
    public ResponseEntity<SavedTrip> addSavedTrip(@Valid @RequestBody SavedTripDTO dto) {
        return ResponseEntity.ok(savedTripsService.createSavedTrip(dto));
    }

    @GetMapping
    public ResponseEntity<List<SavedTrip>> getSavedTrips() {
        return ResponseEntity.ok(savedTripsService.getSavedTrips());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SavedTrip> updateSavedTrip(@PathVariable("id") Long id, @Valid @RequestBody SavedTripDTO dto) {
        return ResponseEntity.ok(savedTripsService.updateSavedTrip(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSavedTrip(@PathVariable("id") Long id) {
        savedTripsService.deleteSavedTrip(id);
        return ResponseEntity.noContent().build();
    }
}
