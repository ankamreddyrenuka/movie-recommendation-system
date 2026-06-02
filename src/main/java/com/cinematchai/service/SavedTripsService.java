package com.cinematchai.service;

import com.cinematchai.dto.SavedTripDTO;
import com.cinematchai.exception.ResourceNotFoundException;
import com.cinematchai.model.SavedTrip;
import com.cinematchai.repository.SavedTripRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class SavedTripsService {

    private final SavedTripRepository savedTripRepository;

    public SavedTripsService(SavedTripRepository savedTripRepository) {
        this.savedTripRepository = savedTripRepository;
    }

    public SavedTrip createSavedTrip(SavedTripDTO dto) {
        SavedTrip trip = new SavedTrip();
        trip.setDestinationId(dto.getDestinationId());
        trip.setDestinationName(dto.getDestinationName());
        trip.setImageUrl(dto.getImageUrl());
        trip.setRegion(dto.getRegion());
        trip.setTravelDates(dto.getTravelDates());
        trip.setTravelers(dto.getTravelers());
        trip.setNotes(dto.getNotes());
        trip.setStatus(dto.getStatus() == null || dto.getStatus().isBlank() ? "Planned" : dto.getStatus());
        trip.setBudgetRange(dto.getBudgetRange());
        trip.setSavedDate(parseSavedDate(dto.getSavedDate()));
        return savedTripRepository.save(trip);
    }

    public List<SavedTrip> getSavedTrips() {
        return savedTripRepository.findAll();
    }

    public SavedTrip updateSavedTrip(Long id, SavedTripDTO dto) {
        SavedTrip trip = savedTripRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Saved trip not found with id " + id));
        trip.setDestinationId(dto.getDestinationId());
        trip.setDestinationName(dto.getDestinationName());
        trip.setImageUrl(dto.getImageUrl());
        trip.setRegion(dto.getRegion());
        trip.setTravelDates(dto.getTravelDates());
        trip.setTravelers(dto.getTravelers());
        trip.setNotes(dto.getNotes());
        trip.setStatus(dto.getStatus() == null || dto.getStatus().isBlank() ? "Planned" : dto.getStatus());
        trip.setBudgetRange(dto.getBudgetRange());
        trip.setSavedDate(parseSavedDate(dto.getSavedDate()));
        return savedTripRepository.save(trip);
    }

    private LocalDate parseSavedDate(String savedDate) {
        if (savedDate == null || savedDate.isBlank()) {
            return LocalDate.now();
        }
        try {
            return LocalDate.parse(savedDate);
        } catch (DateTimeParseException ex) {
            return LocalDate.now();
        }
    }

    public void deleteSavedTrip(Long id) {
        SavedTrip trip = savedTripRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Saved trip not found with id " + id));
        savedTripRepository.delete(trip);
    }
}
