package com.cinematchai.service;

import com.cinematchai.model.DestinationResponse;
import com.cinematchai.model.TripRecommendationResponse;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TripPlannerService {

    private final TravelService travelService;
    private final TravelInsightService travelInsightService;

    public TripPlannerService(TravelService travelService, TravelInsightService travelInsightService) {
        this.travelService = travelService;
        this.travelInsightService = travelInsightService;
    }

    public List<TripRecommendationResponse> recommend(String destinationId) {
        List<Map<String, Object>> trending = travelService.getTrendingDestinations();
        if (trending.isEmpty()) {
            return List.of();
        }
        var sourceOptional = travelService.getDestinationDetails(destinationId);
        if (sourceOptional.isEmpty()) {
            return List.of();
        }
        DestinationResponse source = sourceOptional.get();
        return trending.stream()
                .filter(destination -> !String.valueOf(destination.get("id")).equals(destinationId))
                .map(destination -> scoreRecommendation(source, destination))
                .sorted(Comparator.comparingDouble(TripRecommendationResponse::getScore).reversed())
                .limit(6)
                .collect(Collectors.toList());
    }

    private TripRecommendationResponse scoreRecommendation(DestinationResponse source, Map<String, Object> candidate) {
        List<String> candidateTags = parseTags(candidate.get("tags"));
        double candidateRating = parseDouble(candidate.get("rating"));
        double candidatePopularity = parseDouble(candidate.get("popularity"));

        double styleMatch = String.valueOf(candidate.getOrDefault("travel_style", "")).equalsIgnoreCase(source.getTravelStyle()) ? 1.25 : 0.9;
        double regionMatch = String.valueOf(candidate.getOrDefault("region", "")).equalsIgnoreCase(source.getRegion()) ? 1.15 : 0.95;
        double tagOverlap = candidateTags.stream().filter(source.getTags()::contains).count();
        double tagScore = 1 + Math.min(3, tagOverlap) * 0.15;
        double score = (0.5 * candidateRating + 0.4 * candidatePopularity / 10.0) * styleMatch * regionMatch * tagScore;

        String reason = travelInsightService.buildInsight(source.getName(), source.getTravelStyle(), source.getRegion(), source.getBudgetRange());

        return new TripRecommendationResponse(
                String.valueOf(candidate.getOrDefault("id", "")),
                String.valueOf(candidate.getOrDefault("name", "Unknown")),
                reason,
                candidateRating,
                candidatePopularity,
                String.valueOf(candidate.getOrDefault("image_url", "")),
                score
        );
    }

    @SuppressWarnings("unchecked")
    private List<String> parseTags(Object tags) {
        if (tags instanceof List<?>) {
            return ((List<?>) tags).stream().map(Object::toString).map(String::toLowerCase).collect(Collectors.toList());
        }
        return List.of();
    }

    private double parseDouble(Object value) {
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        try {
            return Double.parseDouble(String.valueOf(value));
        } catch (Exception ex) {
            return 0;
        }
    }
}
