package com.cinematchai.controller;

import com.cinematchai.dto.WatchlistDTO;
import com.cinematchai.model.WatchlistMovie;
import com.cinematchai.service.WatchlistService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/watchlist")
public class WatchlistController {

    private final WatchlistService watchlistService;

    public WatchlistController(WatchlistService watchlistService) {
        this.watchlistService = watchlistService;
    }

    @PostMapping
    public ResponseEntity<WatchlistMovie> addWatchlist(@Valid @RequestBody WatchlistDTO dto) {
        return ResponseEntity.ok(watchlistService.addWatchlist(dto));
    }

    @GetMapping
    public ResponseEntity<List<WatchlistMovie>> getWatchlist() {
        return ResponseEntity.ok(watchlistService.getWatchlist());
    }

    @PutMapping("/{id}")
    public ResponseEntity<WatchlistMovie> updateWatchlist(@PathVariable Long id, @Valid @RequestBody WatchlistDTO dto) {
        return ResponseEntity.ok(watchlistService.updateWatchlist(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWatchlist(@PathVariable Long id) {
        watchlistService.deleteWatchlist(id);
        return ResponseEntity.noContent().build();
    }
}
