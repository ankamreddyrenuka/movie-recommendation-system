package com.cinematchai.controller;

import com.cinematchai.dto.WishlistDestinationDTO;
import com.cinematchai.model.WishlistDestination;
import com.cinematchai.service.WishlistService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @PostMapping
    public ResponseEntity<WishlistDestination> addWishlist(@Valid @RequestBody WishlistDestinationDTO dto) {
        return ResponseEntity.ok(wishlistService.addWishlist(dto));
    }

    @GetMapping
    public ResponseEntity<List<WishlistDestination>> getWishlist() {
        return ResponseEntity.ok(wishlistService.getWishlist());
    }

    @PutMapping("/{id}")
    public ResponseEntity<WishlistDestination> updateWishlist(@PathVariable("id") Long id, @Valid @RequestBody WishlistDestinationDTO dto) {
        return ResponseEntity.ok(wishlistService.updateWishlist(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWishlist(@PathVariable("id") Long id) {
        wishlistService.deleteWishlist(id);
        return ResponseEntity.noContent().build();
    }
}
