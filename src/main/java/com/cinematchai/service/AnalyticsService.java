package com.cinematchai.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AnalyticsService {

    public Map<String, Object> buildAnalytics(List<Map<String, Object>> trending) {
        Map<String, Integer> genreDistribution = new HashMap<>();
        Map<Integer, Integer> yearTrend = new TreeMap<>();
        List<Double> ratings = new ArrayList<>();
        List<Double> popularity = new ArrayList<>();
        List<Map<String, Object>> topMovies = new ArrayList<>();

        for (Map<String, Object> movie : trending) {
            Object genreList = movie.get("genres");
            if (genreList instanceof List<?>) {
                for (Object genre : (List<?>) genreList) {
                    String genreName = genre.toString();
                    genreDistribution.put(genreName, genreDistribution.getOrDefault(genreName, 0) + 1);
                }
            }
            String releaseDate = (String) movie.getOrDefault("release_date", "0000");
            if (releaseDate.length() >= 4) {
                try {
                    int year = Integer.parseInt(releaseDate.substring(0, 4));
                    yearTrend.put(year, yearTrend.getOrDefault(year, 0) + 1);
                } catch (NumberFormatException ignored) {
                }
            }
            Number voteAvg = (Number) movie.getOrDefault("vote_average", 0);
            Number pop = (Number) movie.getOrDefault("popularity", 0);
            ratings.add(voteAvg.doubleValue());
            popularity.add(pop.doubleValue());
            topMovies.add(Map.of(
                    "title", movie.getOrDefault("title", "Unknown"),
                    "rating", voteAvg.doubleValue(),
                    "popularity", pop.doubleValue()
            ));
        }

        topMovies.sort((a, b) -> Double.compare((Double) b.get("popularity"), (Double) a.get("popularity")));
        List<Map<String, Object>> topFive = topMovies.size() > 5 ? topMovies.subList(0, 5) : topMovies;

        Map<String, Object> analytics = new HashMap<>();
        analytics.put("genreDistribution", genreDistribution);
        analytics.put("ratingsHistogram", buildHistogram(ratings));
        analytics.put("popularity", popularity);
        analytics.put("topMovies", topFive);
        analytics.put("releaseYearTrend", yearTrend);
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
