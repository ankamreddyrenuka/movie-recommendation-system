package com.cinematchai.service;

import com.cinematchai.dto.FavoriteDTO;
import com.cinematchai.exception.ResourceNotFoundException;
import com.cinematchai.model.FavoriteMovie;
import com.cinematchai.repository.FavoriteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    public FavoriteService(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    public FavoriteMovie createFavorite(FavoriteDTO dto) {
        FavoriteMovie favorite = new FavoriteMovie();
        favorite.setMovieId(dto.getMovieId());
        favorite.setTitle(dto.getTitle());
        favorite.setPosterPath(dto.getPosterPath());
        favorite.setGenres(dto.getGenres());
        favorite.setRating(dto.getRating());
        favorite.setReleaseDate(dto.getReleaseDate());
        return favoriteRepository.save(favorite);
    }

    public List<FavoriteMovie> getFavorites() {
        return favoriteRepository.findAll();
    }

    public FavoriteMovie updateFavorite(Long id, FavoriteDTO dto) {
        FavoriteMovie favorite = favoriteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Favorite movie not found with id " + id));
        favorite.setMovieId(dto.getMovieId());
        favorite.setTitle(dto.getTitle());
        favorite.setPosterPath(dto.getPosterPath());
        favorite.setGenres(dto.getGenres());
        favorite.setRating(dto.getRating());
        favorite.setReleaseDate(dto.getReleaseDate());
        return favoriteRepository.save(favorite);
    }

    public void deleteFavorite(Long id) {
        FavoriteMovie favorite = favoriteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Favorite movie not found with id " + id));
        favoriteRepository.delete(favorite);
    }
}
