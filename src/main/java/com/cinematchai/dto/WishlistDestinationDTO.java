package com.cinematchai.dto;

import jakarta.validation.constraints.NotBlank;

public class WishlistDestinationDTO {
    private Long id;

    @NotBlank(message = "destinationId is required")
    private String destinationId;

    @NotBlank(message = "destinationName is required")
    private String destinationName;

    private String imageUrl;
    private String region;
    private String preferredSeason;
    private String budgetCategory;
    private String desiredActivities;
    private String notes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPreferredSeason() {
        return preferredSeason;
    }

    public void setPreferredSeason(String preferredSeason) {
        this.preferredSeason = preferredSeason;
    }

    public String getBudgetCategory() {
        return budgetCategory;
    }

    public void setBudgetCategory(String budgetCategory) {
        this.budgetCategory = budgetCategory;
    }

    public String getDesiredActivities() {
        return desiredActivities;
    }

    public void setDesiredActivities(String desiredActivities) {
        this.desiredActivities = desiredActivities;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
