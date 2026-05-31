package com.cinematchai.service;

import com.cinematchai.dto.WishlistDestinationDTO;
import com.cinematchai.exception.ResourceNotFoundException;
import com.cinematchai.model.WishlistDestination;
import com.cinematchai.repository.WishlistDestinationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {

    private final WishlistDestinationRepository wishlistDestinationRepository;

    public WishlistService(WishlistDestinationRepository wishlistDestinationRepository) {
        this.wishlistDestinationRepository = wishlistDestinationRepository;
    }

    public WishlistDestination addWishlist(WishlistDestinationDTO dto) {
        WishlistDestination item = new WishlistDestination();
        item.setDestinationId(dto.getDestinationId());
        item.setDestinationName(dto.getDestinationName());
        item.setImageUrl(dto.getImageUrl());
        item.setRegion(dto.getRegion());
        item.setPreferredSeason(dto.getPreferredSeason());
        item.setBudgetCategory(dto.getBudgetCategory());
        item.setDesiredActivities(dto.getDesiredActivities());
        item.setNotes(dto.getNotes());
        return wishlistDestinationRepository.save(item);
    }

    public List<WishlistDestination> getWishlist() {
        return wishlistDestinationRepository.findAll();
    }

    public WishlistDestination updateWishlist(Long id, WishlistDestinationDTO dto) {
        WishlistDestination item = wishlistDestinationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist destination not found with id " + id));
        item.setDestinationId(dto.getDestinationId());
        item.setDestinationName(dto.getDestinationName());
        item.setImageUrl(dto.getImageUrl());
        item.setRegion(dto.getRegion());
        item.setPreferredSeason(dto.getPreferredSeason());
        item.setBudgetCategory(dto.getBudgetCategory());
        item.setDesiredActivities(dto.getDesiredActivities());
        item.setNotes(dto.getNotes());
        return wishlistDestinationRepository.save(item);
    }

    public void deleteWishlist(Long id) {
        WishlistDestination item = wishlistDestinationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist destination not found with id " + id));
        wishlistDestinationRepository.delete(item);
    }
}
