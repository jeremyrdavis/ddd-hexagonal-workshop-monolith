package io.arrogantprogrammer.cfp.domain.valueobjects;

import jakarta.persistence.Embeddable;

/**
 * SessionAbstract value object that encapsulates session abstract validation and behavior.
 * This is an example of a Domain-Driven Design value object.
 */
public record SessionAbstract(
    String title,
    String summary,
    String outline,
    String learningObjectives,
    String targetAudience,
    String prerequisites
) {
    /**
     * Creates a new SessionAbstract instance after validating the input.
     *
     * @throws IllegalArgumentException if any field is invalid
     */
    public SessionAbstract {
        validate(title, summary, outline, learningObjectives, targetAudience, prerequisites);
    }

    /**
     * Validates that all components of the session abstract are properly formatted.
     */
    private static void validate(String title, String summary, String outline,
            String learningObjectives, String targetAudience, String prerequisites) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        if (title.length() > 200) {
            throw new IllegalArgumentException("Title is too long (max 200 characters)");
        }
        if (summary == null || summary.isBlank()) {
            throw new IllegalArgumentException("Summary cannot be empty");
        }
        if (summary.length() > 2000) {
            throw new IllegalArgumentException("Summary is too long (max 2000 characters)");
        }
        if (outline == null || outline.isBlank()) {
            throw new IllegalArgumentException("Outline cannot be empty");
        }
        if (learningObjectives == null || learningObjectives.isBlank()) {
            throw new IllegalArgumentException("Learning objectives cannot be empty");
        }
        if (targetAudience == null || targetAudience.isBlank()) {
            throw new IllegalArgumentException("Target audience cannot be empty");
        }
        if (prerequisites == null) {
            throw new IllegalArgumentException("Prerequisites cannot be null");
        }
    }

    /**
     * Returns a brief preview of the session, including title and truncated summary.
     */
    public String getPreview() {
        int previewLength = Math.min(summary.length(), 100);
        String truncatedSummary = summary.substring(0, previewLength);
        if (previewLength < summary.length()) {
            truncatedSummary += "...";
        }
        return String.format("%s - %s", title, truncatedSummary);
    }

    /**
     * Checks if the session is suitable for beginners based on prerequisites.
     */
    public boolean isBeginnerFriendly() {
        return prerequisites.isBlank() ||
               prerequisites.toLowerCase().contains("none") ||
               prerequisites.toLowerCase().contains("no prerequisite");
    }

    @Override
    public String toString() {
        return getPreview();
    }
}
