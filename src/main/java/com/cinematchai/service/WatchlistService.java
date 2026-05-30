package com.cinematchai.service;

import com.cinematchai.dto.WatchlistDTO;
import com.cinematchai.exception.ResourceNotFoundException;
import com.cinematchai.model.WatchlistMovie;
import com.cinematchai.repository.WatchlistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WatchlistService {

    private final WatchlistRepository watchlistRepository;

    public WatchlistService(WatchlistRepository watchlistRepository) {
        this.watchlistRepository = watchlistRepository;
    }

    public WatchlistMovie addWatchlist(WatchlistDTO dto) {
        WatchlistMovie item = new WatchlistMovie();
        item.setMovieId(dto.getMovieId());
        item.setTitle(dto.getTitle());
        item.setPosterPath(dto.getPosterPath());
        item.setStatus(dto.getStatus() != null ? dto.getStatus() : "To Watch");
        item.setNotes(dto.getNotes());
        item.setWatched(dto.getWatched() != null && dto.getWatched());
        return watchlistRepository.save(item);
    }

    public List<WatchlistMovie> getWatchlist() {
        return watchlistRepository.findAll();
    }

    public WatchlistMovie updateWatchlist(Long id, WatchlistDTO dto) {
        WatchlistMovie item = watchlistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Watchlist movie not found with id " + id));
        item.setMovieId(dto.getMovieId());
        item.setTitle(dto.getTitle());
        item.setPosterPath(dto.getPosterPath());
        item.setStatus(dto.getStatus() != null ? dto.getStatus() : item.getStatus());
        item.setNotes(dto.getNotes());
        item.setWatched(dto.getWatched() != null && dto.getWatched());
        return watchlistRepository.save(item);
    }

    public void deleteWatchlist(Long id) {
        WatchlistMovie item = watchlistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Watchlist movie not found with id " + id));
        watchlistRepository.delete(item);
    }
}
