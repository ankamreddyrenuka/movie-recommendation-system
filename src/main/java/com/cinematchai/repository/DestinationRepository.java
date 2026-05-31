package com.cinematchai.repository;

import com.cinematchai.model.DestinationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DestinationRepository extends JpaRepository<DestinationEntity, Long> {
    Optional<DestinationEntity> findFirstByDestinationId(String destinationId);
    List<DestinationEntity> findByNameContainingIgnoreCase(String name);
    List<DestinationEntity> findByRegionContainingIgnoreCase(String region);
    List<DestinationEntity> findByDistrictContainingIgnoreCase(String district);
    List<DestinationEntity> findByCategoryContainingIgnoreCase(String category);
    List<DestinationEntity> findByTravelStyleContainingIgnoreCase(String travelStyle);
    List<DestinationEntity> findByTagsContainingIgnoreCase(String tag);
    List<DestinationEntity> findByBudgetLevelContainingIgnoreCase(String budgetLevel);
    List<DestinationEntity> findByBestSeasonContainingIgnoreCase(String bestSeason);
    List<DestinationEntity> findByBudgetRangeContainingIgnoreCase(String budgetRange);
}
