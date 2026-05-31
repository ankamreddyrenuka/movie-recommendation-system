package com.cinematchai.service;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {

    public Map<String, Object> buildAnalytics(List<Map<String, Object>> destinations) {
        Map<String, Object> analytics = new HashMap<>();
        analytics.put("summary", buildSummary(destinations));
        analytics.put("categoryDistribution", categoryDistribution(destinations));
        analytics.put("districtPopularity", districtPopularity(destinations));
        analytics.put("ratingsByCategory", ratingsByCategory(destinations));
        analytics.put("budgetBreakdown", budgetBreakdown(destinations));
        analytics.put("tripDurationAnalysis", tripDurationAnalysis(destinations));
        analytics.put("monthlyVisitorsTrend", monthlyVisitorsTrend(destinations));
        analytics.put("topTrending", topTrending(destinations));
        analytics.put("insights", buildInsights(destinations));
        return analytics;
    }

    public Map<String, Object> buildSummary(List<Map<String, Object>> destinations) {
        int total = destinations.size();
        double totalRating = 0;
        double totalCost = 0;
        int totalDuration = 0;
        int totalVisitors = 0;
        Map<String, Integer> categoryCount = new HashMap<>();
        Map<String, Integer> districtCount = new HashMap<>();
        Map<String, Double> destinationPopularity = new HashMap<>();

        for (Map<String, Object> destination : destinations) {
            totalRating += asDouble(destination.get("rating"));
            totalCost += asDouble(destination.get("avg_cost_per_day"));
            totalDuration += asInt(destination.get("trip_duration"));
            totalVisitors += asInt(destination.get("monthly_visitors"));
            categoryCount.merge(String.valueOf(destination.getOrDefault("category", "Unknown")), 1, Integer::sum);
            districtCount.merge(String.valueOf(destination.getOrDefault("district", "Unknown")), 1, Integer::sum);
            destinationPopularity.put(String.valueOf(destination.getOrDefault("name", "Unknown")), asDouble(destination.get("popularity")));
        }

        String topCategory = categoryCount.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("N/A");
        String topDistrict = districtCount.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("N/A");
        String topDestination = destinationPopularity.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("N/A");

        Map<String, Object> summary = new LinkedHashMap<>();
        summary.put("totalDestinations", total);
        summary.put("averageRating", total > 0 ? round(totalRating / total, 2) : 0);
        summary.put("averageDailyCost", total > 0 ? round(totalCost / total, 2) : 0);
        summary.put("averageTripDuration", total > 0 ? round((double) totalDuration / total, 1) : 0);
        summary.put("totalMonthlyVisitors", totalVisitors);
        summary.put("topCategory", topCategory);
        summary.put("topDistrict", topDistrict);
        summary.put("topDestination", topDestination);
        return summary;
    }

    public Map<String, Integer> categoryDistribution(List<Map<String, Object>> destinations) {
        return destinations.stream()
                .map(dest -> String.valueOf(dest.getOrDefault("category", "Unknown")))
                .collect(Collectors.groupingBy(category -> category, Collectors.summingInt(value -> 1)));
    }

    public Map<String, Integer> districtPopularity(List<Map<String, Object>> destinations) {
        return destinations.stream()
                .map(dest -> String.valueOf(dest.getOrDefault("district", "Unknown")))
                .collect(Collectors.groupingBy(district -> district, Collectors.summingInt(value -> 1)));
    }

    public Map<String, Double> ratingsByCategory(List<Map<String, Object>> destinations) {
        Map<String, List<Double>> grouped = new HashMap<>();
        for (Map<String, Object> destination : destinations) {
            String category = String.valueOf(destination.getOrDefault("category", "Unknown"));
            grouped.computeIfAbsent(category, key -> new ArrayList<>()).add(asDouble(destination.get("rating")));
        }
        return grouped.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> round(entry.getValue().stream().mapToDouble(Double::doubleValue).average().orElse(0), 2)));
    }

    public Map<String, Integer> budgetBreakdown(List<Map<String, Object>> destinations) {
        return destinations.stream()
                .map(dest -> String.valueOf(dest.getOrDefault("budget_level", dest.getOrDefault("budget_range", "Unknown"))))
                .collect(Collectors.groupingBy(budget -> budget, Collectors.summingInt(value -> 1)));
    }

    public Map<String, Integer> tripDurationAnalysis(List<Map<String, Object>> destinations) {
        Map<String, Integer> buckets = new LinkedHashMap<>();
        buckets.put("1-2 days", 0);
        buckets.put("3-5 days", 0);
        buckets.put("6-8 days", 0);
        buckets.put("9+ days", 0);

        for (Map<String, Object> destination : destinations) {
            int duration = asInt(destination.get("trip_duration"));
            if (duration <= 2) {
                buckets.compute("1-2 days", (k, v) -> v + 1);
            } else if (duration <= 5) {
                buckets.compute("3-5 days", (k, v) -> v + 1);
            } else if (duration <= 8) {
                buckets.compute("6-8 days", (k, v) -> v + 1);
            } else {
                buckets.compute("9+ days", (k, v) -> v + 1);
            }
        }
        return buckets;
    }

    public List<Map<String, Object>> monthlyVisitorsTrend(List<Map<String, Object>> destinations) {
        return destinations.stream()
                .sorted(Comparator.comparingInt(dest -> -asInt(dest.get("monthly_visitors"))))
                .limit(8)
                .map(dest -> Map.of(
                        "name", dest.getOrDefault("name", "Unknown"),
                        "monthlyVisitors", asInt(dest.get("monthly_visitors"))
                ))
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> topTrending(List<Map<String, Object>> destinations) {
        return destinations.stream()
                .sorted(Comparator.comparingDouble(dest -> -asDouble(dest.get("popularity"))))
                .limit(6)
                .collect(Collectors.toList());
    }

    public List<String> buildInsights(List<Map<String, Object>> destinations) {
        Map<String, Integer> categoryCounts = categoryDistribution(destinations);
        Map<String, Integer> districtCounts = districtPopularity(destinations);
        Map<String, Integer> seasonCounts = destinations.stream()
                .map(dest -> String.valueOf(dest.getOrDefault("best_season", "Unknown")))
                .collect(Collectors.groupingBy(season -> season, Collectors.summingInt(value -> 1)));

        String dominantCategory = categoryCounts.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("N/A");
        String dominantDistrict = districtCounts.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("N/A");
        String dominantSeason = seasonCounts.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("N/A");

        return List.of(
                String.format("Temple and nature tourism are the strongest draws, with %s as the most represented category.", dominantCategory),
                String.format("Destinations in %s are the most common in the Andhra Pradesh portfolio.", dominantDistrict),
                String.format("Winter is the most popular travel season, capturing %d of the top destinations.", seasonCounts.getOrDefault(dominantSeason, 0)),
                String.format("Average stay in the seeded destinations is around %.1f days.", asDouble(buildSummary(destinations).get("averageTripDuration"))),
                String.format("Average daily travel cost across Andhra Pradesh destinations is approximately ₹%.0f.", asDouble(buildSummary(destinations).get("averageDailyCost")))
        );
    }

    private double asDouble(Object value) {
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        try {
            return Double.parseDouble(String.valueOf(value));
        } catch (Exception ex) {
            return 0;
        }
    }

    private int asInt(Object value) {
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        try {
            return Integer.parseInt(String.valueOf(value));
        } catch (Exception ex) {
            return 0;
        }
    }

    private double round(double value, int decimals) {
        double factor = Math.pow(10, decimals);
        return Math.round(value * factor) / factor;
    }
}
