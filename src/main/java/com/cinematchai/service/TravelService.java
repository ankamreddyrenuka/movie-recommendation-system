package com.cinematchai.service;

import com.cinematchai.model.DestinationEntity;
import com.cinematchai.model.DestinationResponse;
import com.cinematchai.repository.DestinationRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TravelService {

    private final DestinationRepository destinationRepository;

    public TravelService(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    public List<Map<String, Object>> getTrendingDestinations() {
        return destinationRepository.findAll().stream()
                .sorted(Comparator.comparingDouble(DestinationEntity::getPopularity).reversed())
                .limit(12)
                .map(this::entityToSummary)
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> getFeaturedDestinations() {
        return destinationRepository.findAll().stream()
                .sorted(Comparator.comparingDouble(DestinationEntity::getRating).reversed())
                .limit(8)
                .map(this::entityToSummary)
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> searchDestinations(String query, String region, String style, String budget, Double minRating, Integer durationFrom, Integer durationTo) {
        Set<Map<String, Object>> results = new LinkedHashSet<>();
        if (query != null && !query.isBlank()) {
            results.addAll(destinationRepository.findByNameContainingIgnoreCase(query).stream().map(this::entityToSummary).collect(Collectors.toList()));
            results.addAll(destinationRepository.findByRegionContainingIgnoreCase(query).stream().map(this::entityToSummary).collect(Collectors.toList()));
            results.addAll(destinationRepository.findByTravelStyleContainingIgnoreCase(query).stream().map(this::entityToSummary).collect(Collectors.toList()));
            results.addAll(destinationRepository.findByTagsContainingIgnoreCase(query).stream().map(this::entityToSummary).collect(Collectors.toList()));
        } else {
            results.addAll(destinationRepository.findAll().stream().map(this::entityToSummary).collect(Collectors.toList()));
        }

        return results.stream()
                .filter(destination -> filterByRegion(destination, region))
                .filter(destination -> filterByStyle(destination, style))
                .filter(destination -> filterByBudget(destination, budget))
                .filter(destination -> filterByRating(destination, minRating))
                .filter(destination -> filterByDuration(destination, durationFrom, durationTo))
                .limit(60)
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> getSuggestions(String query) {
        if (query == null || query.isBlank()) {
            return Collections.emptyList();
        }
        return destinationRepository.findByNameContainingIgnoreCase(query).stream()
                .map(this::entityToSummary)
                .limit(10)
                .collect(Collectors.toList());
    }

    public DestinationResponse getDestinationDetails(String destinationId) {
        return destinationRepository.findFirstByDestinationId(destinationId)
                .map(this::entityToResponse)
                .orElse(null);
    }

    public Map<String, Object> getComparison(String firstDestinationId, String secondDestinationId) {
        DestinationResponse first = getDestinationDetails(firstDestinationId);
        DestinationResponse second = getDestinationDetails(secondDestinationId);
        return Map.of("first", first, "second", second);
    }

    private boolean filterByRegion(Map<String, Object> destination, String region) {
        if (region == null || region.isBlank()) {
            return true;
        }
        return String.valueOf(destination.getOrDefault("region", "")).toLowerCase().contains(region.toLowerCase());
    }

    private boolean filterByStyle(Map<String, Object> destination, String style) {
        if (style == null || style.isBlank()) {
            return true;
        }
        return String.valueOf(destination.getOrDefault("travel_style", "")).toLowerCase().contains(style.toLowerCase());
    }

    private boolean filterByBudget(Map<String, Object> destination, String budget) {
        if (budget == null || budget.isBlank()) {
            return true;
        }
        return String.valueOf(destination.getOrDefault("budget_range", "")).toLowerCase().contains(budget.toLowerCase());
    }

    private boolean filterByRating(Map<String, Object> destination, Double minRating) {
        if (minRating == null) {
            return true;
        }
        Object rating = destination.get("rating");
        if (rating instanceof Number) {
            return ((Number) rating).doubleValue() >= minRating;
        }
        try {
            return Double.parseDouble(String.valueOf(rating)) >= minRating;
        } catch (Exception ex) {
            return false;
        }
    }

    private boolean filterByDuration(Map<String, Object> destination, Integer durationFrom, Integer durationTo) {
        if (durationFrom == null && durationTo == null) {
            return true;
        }
        Object duration = destination.get("duration_days");
        if (duration instanceof Number) {
            int days = ((Number) duration).intValue();
            if (durationFrom != null && days < durationFrom) {
                return false;
            }
            if (durationTo != null && days > durationTo) {
                return false;
            }
            return true;
        }
        try {
            int days = Integer.parseInt(String.valueOf(duration));
            if (durationFrom != null && days < durationFrom) {
                return false;
            }
            if (durationTo != null && days > durationTo) {
                return false;
            }
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private DestinationResponse entityToResponse(DestinationEntity entity) {
        DestinationResponse response = new DestinationResponse();
        response.setId(entity.getDestinationId());
        response.setName(entity.getName());
        response.setDescription(entity.getDescription());
        response.setImageUrl(entity.getImageUrl());
        response.setBackdropUrl(entity.getBackdropUrl());
        response.setRegion(entity.getRegion());
        response.setCountry(entity.getCountry());
        response.setTags(splitCsv(entity.getTags()));
        response.setBestTimeToVisit(entity.getBestTimeToVisit());
        response.setTravelStyle(entity.getTravelStyle());
        response.setBudgetRange(entity.getBudgetRange());
        response.setRecommendedDuration(entity.getRecommendedDuration());
        response.setRating(entity.getRating());
        response.setPopularity(entity.getPopularity());
        response.setHighlights(entity.getHighlights());
        return response;
    }

    private Map<String, Object> entityToSummary(DestinationEntity entity) {
        return Map.ofEntries(
                Map.entry("id", entity.getDestinationId()),
                Map.entry("name", entity.getName()),
                Map.entry("description", entity.getDescription()),
                Map.entry("image_url", entity.getImageUrl()),
                Map.entry("backdrop_url", entity.getBackdropUrl()),
                Map.entry("region", entity.getRegion()),
                Map.entry("country", entity.getCountry()),
                Map.entry("tags", splitCsv(entity.getTags())),
                Map.entry("best_time", entity.getBestTimeToVisit()),
                Map.entry("travel_style", entity.getTravelStyle()),
                Map.entry("budget_range", entity.getBudgetRange()),
                Map.entry("duration_days", entity.getRecommendedDuration()),
                Map.entry("rating", entity.getRating()),
                Map.entry("popularity", entity.getPopularity()),
                Map.entry("highlights", entity.getHighlights())
        );
    }

    private List<String> splitCsv(String csv) {
        if (csv == null || csv.isBlank()) {
            return Collections.emptyList();
        }
        return Arrays.stream(csv.split(","))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .collect(Collectors.toList());
    }
}
