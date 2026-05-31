package com.cinematchai.model;

import jakarta.persistence.*;

@Entity
@Table(name = "wishlist_destinations")
public class WishlistDestination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String destinationId;
    private String destinationName;
    private String imageUrl;
    private String region;
    private String preferredSeason;
    private String budgetCategory;
    @Column(length = 1000)
    private String desiredActivities;
    @Column(length = 2000)
    private String notes;

    public WishlistDestination() {
    }

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
