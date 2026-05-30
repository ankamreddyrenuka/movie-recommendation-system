package com.cinematchai.repository;

import com.cinematchai.model.WatchlistMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchlistRepository extends JpaRepository<WatchlistMovie, Long> {
}
