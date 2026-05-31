package com.cinematchai.repository;

import com.cinematchai.model.SavedTrip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavedTripRepository extends JpaRepository<SavedTrip, Long> {
}
