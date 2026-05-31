package com.cinematchai.service;

import org.springframework.stereotype.Service;

@Service
public class TravelInsightService {

    public String buildInsight(String sourceName, String travelStyle, String region, String budgetRange) {
        String style = travelStyle != null && !travelStyle.isBlank() ? travelStyle : "immersive";
        String regionLabel = region != null && !region.isBlank() ? region : "global";
        String budget = budgetRange != null && !budgetRange.isBlank() ? budgetRange : "budget-friendly";
        return String.format("Because you enjoyed planning a %s trip in %s, this recommendation fits a similar travel style and %s spending profile.",
                style, regionLabel, budget);
    }
}
