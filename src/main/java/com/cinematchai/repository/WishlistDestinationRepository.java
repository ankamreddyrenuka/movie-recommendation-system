package com.cinematchai.repository;

import com.cinematchai.model.WishlistDestination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistDestinationRepository extends JpaRepository<WishlistDestination, Long> {
}
