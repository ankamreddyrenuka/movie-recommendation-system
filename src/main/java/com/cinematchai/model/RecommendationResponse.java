package com.cinematchai.model;

public class RecommendationResponse {
    private String movieId;
    private String title;
    private String reason;
    private double rating;
    private double popularity;
    private String posterPath;

    public RecommendationResponse() {
    }

    public RecommendationResponse(String movieId, String title, String reason, double rating, double popularity, String posterPath) {
        this.movieId = movieId;
        this.title = title;
        this.reason = reason;
        this.rating = rating;
        this.popularity = popularity;
        this.posterPath = posterPath;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}
