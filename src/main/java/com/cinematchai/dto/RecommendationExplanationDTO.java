package com.cinematchai.dto;

public class RecommendationExplanationDTO {
    private String sourceTitle;
    private String candidateTitle;
    private String reason;

    public RecommendationExplanationDTO() {
    }

    public RecommendationExplanationDTO(String sourceTitle, String candidateTitle, String reason) {
        this.sourceTitle = sourceTitle;
        this.candidateTitle = candidateTitle;
        this.reason = reason;
    }

    public String getSourceTitle() {
        return sourceTitle;
    }

    public void setSourceTitle(String sourceTitle) {
        this.sourceTitle = sourceTitle;
    }

    public String getCandidateTitle() {
        return candidateTitle;
    }

    public void setCandidateTitle(String candidateTitle) {
        this.candidateTitle = candidateTitle;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
