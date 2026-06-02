package com.cinematchai.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class CompareRequest {

    @NotNull(message = "destinationIds cannot be null")
    @Size(min = 2, max = 4, message = "Compare requires 2 to 4 destination IDs")
    private List<String> destinationIds;

    public List<String> getDestinationIds() {
        return destinationIds;
    }

    public void setDestinationIds(List<String> destinationIds) {
        this.destinationIds = destinationIds;
    }
}
