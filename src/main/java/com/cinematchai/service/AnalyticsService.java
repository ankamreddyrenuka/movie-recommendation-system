package com.cinematchai.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AnalyticsService {

    public Map<String, Object> buildAnalytics(List<Map<String, Object>> trending) {
        Map<String, Integer> tagDistribution = new HashMap<>();
        Map<String, Integer> regionDistribution = new HashMap<>();
        List<Double> ratings = new ArrayList<>();
        List<Double> popularity = new ArrayList<>();
        List<Map<String, Object>> topDestinations = new ArrayList<>();

        for (Map<String, Object> destination : trending) {
            Object tagList = destination.get("tags");
            if (tagList instanceof List<?>) {
                for (Object tag : (List<?>) tagList) {
                    String tagName = tag.toString();
                    tagDistribution.put(tagName, tagDistribution.getOrDefault(tagName, 0) + 1);
                }
            }
            String region = String.valueOf(destination.getOrDefault("region", "Unknown"));
            regionDistribution.put(region, regionDistribution.getOrDefault(region, 0) + 1);
            Number ratingValue = (Number) destination.getOrDefault("rating", 0);
            Number pop = (Number) destination.getOrDefault("popularity", 0);
            ratings.add(ratingValue.doubleValue());
            popularity.add(pop.doubleValue());
            topDestinations.add(Map.of(
                    "name", destination.getOrDefault("name", "Unknown"),
                    "rating", ratingValue.doubleValue(),
                    "popularity", pop.doubleValue(),
                    "region", region
            ));
        }

        topDestinations.sort((a, b) -> Double.compare((Double) b.get("popularity"), (Double) a.get("popularity")));
        List<Map<String, Object>> topFive = topDestinations.size() > 5 ? topDestinations.subList(0, 5) : topDestinations;

        Map<String, Object> analytics = new HashMap<>();
        analytics.put("tagDistribution", tagDistribution);
        analytics.put("regionDistribution", regionDistribution);
        analytics.put("ratingsHistogram", buildHistogram(ratings));
        analytics.put("popularity", popularity);
        analytics.put("topDestinations", topFive);
        return analytics;
    }

    private Map<String, Integer> buildHistogram(List<Double> values) {
        Map<String, Integer> histogram = new TreeMap<>();
        for (Double value : values) {
            int bucket = (int) Math.floor(value);
            String key = bucket + " - " + (bucket + 1);
            histogram.put(key, histogram.getOrDefault(key, 0) + 1);
        }
        return histogram;
    }
}
