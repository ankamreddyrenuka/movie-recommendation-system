package com.cinematchai.controller;

import com.cinematchai.dto.FavoriteDTO;
import com.cinematchai.model.FavoriteMovie;
import com.cinematchai.service.FavoriteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping
    public ResponseEntity<FavoriteMovie> addFavorite(@Valid @RequestBody FavoriteDTO favoriteDTO) {
        return ResponseEntity.ok(favoriteService.createFavorite(favoriteDTO));
    }

    @GetMapping
    public ResponseEntity<List<FavoriteMovie>> getFavorites() {
        return ResponseEntity.ok(favoriteService.getFavorites());
    }

    @PutMapping("/{id}")
    public ResponseEntity<FavoriteMovie> updateFavorite(@PathVariable Long id, @Valid @RequestBody FavoriteDTO favoriteDTO) {
        return ResponseEntity.ok(favoriteService.updateFavorite(id, favoriteDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFavorite(@PathVariable Long id) {
        favoriteService.deleteFavorite(id);
        return ResponseEntity.noContent().build();
    }
}
