package com.cinematchai.model;

import jakarta.persistence.*;

@Entity
@Table(name = "saved_trips")
public class SavedTrip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "destination_id", nullable = false)
    private String destinationId;

    @Column(name = "destination_name", nullable = false)
    private String destinationName;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "region")
    private String region;

    @Column(name = "travel_dates")
    private String travelDates;

    @Column(name = "travelers")
    private int travelers;

    @Column(name = "status")
    private String status;

    @Column(name = "budget_range")
    private String budgetRange;

    @Column(name = "saved_date")
    private java.time.LocalDate savedDate;

    @Column(name = "notes", length = 2000)
    private String notes;

    public SavedTrip() {
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

    public String getTravelDates() {
        return travelDates;
    }

    public void setTravelDates(String travelDates) {
        this.travelDates = travelDates;
    }

    public int getTravelers() {
        return travelers;
    }

    public void setTravelers(int travelers) {
        this.travelers = travelers;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBudgetRange() {
        return budgetRange;
    }

    public void setBudgetRange(String budgetRange) {
        this.budgetRange = budgetRange;
    }

    public java.time.LocalDate getSavedDate() {
        return savedDate;
    }

    public void setSavedDate(java.time.LocalDate savedDate) {
        this.savedDate = savedDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
