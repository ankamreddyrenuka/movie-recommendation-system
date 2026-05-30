package com.cinematchai.service;

import org.springframework.stereotype.Service;

@Service
public class AIRecommendationService {

    public String buildInsight(String sourceTitle, String primaryGenre, double rating, double popularity) {
        String sentiment = rating >= 7.0 ? "highly rated" : "well-rated";
        String engagement = popularity > 50 ? "strong audience engagement" : "healthy audience interest";
        return String.format("Because you enjoyed %s, which is a %s %s movie with %s, you may also enjoy a similar cinematic experience.",
                sourceTitle, sentiment, primaryGenre, engagement);
    }
}
