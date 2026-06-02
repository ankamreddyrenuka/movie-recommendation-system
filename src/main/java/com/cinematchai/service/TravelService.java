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

    public List<Map<String, Object>> getAllDestinations() {
        return destinationRepository.findAll().stream()
                .map(this::entityToSummary)
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> getDestinationsByCategory(String category) {
        return destinationRepository.findByCategoryContainingIgnoreCase(category).stream()
                .map(this::entityToSummary)
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> getDestinationsByDistrict(String district) {
        return destinationRepository.findByDistrictContainingIgnoreCase(district).stream()
                .map(this::entityToSummary)
                .collect(Collectors.toList());
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

    public List<Map<String, Object>> searchDestinations(String query, String category, String district, String budget,
                                                        String season, Double minRating, Integer durationFrom, Integer durationTo) {
        Set<Map<String, Object>> results = new LinkedHashSet<>();
        if (query != null && !query.isBlank()) {
            results.addAll(destinationRepository.findByNameContainingIgnoreCase(query).stream().map(this::entityToSummary).collect(Collectors.toList()));
            results.addAll(destinationRepository.findByDistrictContainingIgnoreCase(query).stream().map(this::entityToSummary).collect(Collectors.toList()));
            results.addAll(destinationRepository.findByCategoryContainingIgnoreCase(query).stream().map(this::entityToSummary).collect(Collectors.toList()));
            results.addAll(destinationRepository.findByTagsContainingIgnoreCase(query).stream().map(this::entityToSummary).collect(Collectors.toList()));
        } else {
            results.addAll(destinationRepository.findAll().stream().map(this::entityToSummary).collect(Collectors.toList()));
        }

        return results.stream()
                .filter(destination -> filterByCategory(destination, category))
                .filter(destination -> filterByDistrict(destination, district))
                .filter(destination -> filterByBudget(destination, budget))
                .filter(destination -> filterBySeason(destination, season))
                .filter(destination -> filterByRating(destination, minRating))
                .filter(destination -> filterByDuration(destination, durationFrom, durationTo))
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

    public Optional<DestinationResponse> getDestinationDetails(String destinationId) {
        return destinationRepository.findFirstByDestinationId(destinationId)
                .map(this::entityToResponse);
    }

    public List<DestinationResponse> compareDestinations(List<String> destinationIds) {
        if (destinationIds == null) {
            return Collections.emptyList();
        }
        return destinationIds.stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(id -> !id.isBlank())
                .map(this::getDestinationDetails)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
    }

    private boolean filterByCategory(Map<String, Object> destination, String category) {
        if (category == null || category.isBlank()) {
            return true;
        }
        return String.valueOf(destination.getOrDefault("category", "")).toLowerCase().contains(category.toLowerCase());
    }

    private boolean filterByDistrict(Map<String, Object> destination, String district) {
        if (district == null || district.isBlank()) {
            return true;
        }
        return String.valueOf(destination.getOrDefault("district", "")).toLowerCase().contains(district.toLowerCase());
    }

    private boolean filterByBudget(Map<String, Object> destination, String budget) {
        if (budget == null || budget.isBlank()) {
            return true;
        }
        return String.valueOf(destination.getOrDefault("budget_level", destination.getOrDefault("budget_range", ""))).toLowerCase().contains(budget.toLowerCase());
    }

    private boolean filterBySeason(Map<String, Object> destination, String season) {
        if (season == null || season.isBlank()) {
            return true;
        }
        return String.valueOf(destination.getOrDefault("best_season", "")).toLowerCase().contains(season.toLowerCase());
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
        Object duration = destination.get("trip_duration");
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
        response.setDistrict(entity.getDistrict());
        response.setAddress(entity.getAddress());
        response.setCategory(entity.getCategory());
        response.setTags(splitCsv(entity.getTags()));
        response.setBestTimeToVisit(entity.getBestTimeToVisit());
        response.setTravelStyle(entity.getTravelStyle());
        response.setBudgetLevel(entity.getBudgetLevel());
        response.setBudgetRange(entity.getBudgetRange());
        response.setTripDuration(entity.getTripDuration());
        response.setRecommendedDuration(entity.getRecommendedDuration());
        response.setBestSeason(entity.getBestSeason());
        response.setAvgCostPerDay(entity.getAvgCostPerDay());
        response.setMonthlyVisitors(entity.getMonthlyVisitors());
        response.setRating(entity.getRating());
        response.setPopularity(entity.getPopularity());
        response.setLatitude(entity.getLatitude());
        response.setLongitude(entity.getLongitude());
        if (entity.getLatitude() != null && entity.getLongitude() != null) {
            response.setGoogleMapsUrl("https://www.google.com/maps/search/?api=1&query=" + entity.getLatitude() + "," + entity.getLongitude());
        }
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
                Map.entry("district", entity.getDistrict()),
                Map.entry("address", entity.getAddress()),
                Map.entry("category", entity.getCategory()),
                Map.entry("tags", splitCsv(entity.getTags())),
                Map.entry("best_time", entity.getBestTimeToVisit()),
                Map.entry("travel_style", entity.getTravelStyle()),
                Map.entry("budget_level", entity.getBudgetLevel()),
                Map.entry("budget_range", entity.getBudgetRange()),
                Map.entry("trip_duration", entity.getTripDuration()),
                Map.entry("recommended_duration", entity.getRecommendedDuration()),
                Map.entry("best_season", entity.getBestSeason()),
                Map.entry("avg_cost_per_day", entity.getAvgCostPerDay()),
                Map.entry("monthly_visitors", entity.getMonthlyVisitors()),
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
