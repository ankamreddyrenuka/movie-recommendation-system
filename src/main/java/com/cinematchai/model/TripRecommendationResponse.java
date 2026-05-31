package com.cinematchai.model;

public class TripRecommendationResponse {
    private String destinationId;
    private String destinationName;
    private String reason;
    private double rating;
    private double popularity;
    private String imageUrl;
    private double score;

    public TripRecommendationResponse() {
    }

    public TripRecommendationResponse(String destinationId, String destinationName, String reason, double rating, double popularity, String imageUrl, double score) {
        this.destinationId = destinationId;
        this.destinationName = destinationName;
        this.reason = reason;
        this.rating = rating;
        this.popularity = popularity;
        this.imageUrl = imageUrl;
        this.score = score;
    }

    public String getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(String destinationId) {
        this.destinationId = destinationId;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
