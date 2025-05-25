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
package io.arrogantprogrammer.cfp.domain.valueobjects;

import java.util.Objects;

/**
 * Value object that encapsulates the abstract for a conference session.
 */
public class SessionAbstract {
    private final String title;
    private final String summary;
    private final String outline;
    private final String learningObjectives;
    private final String targetAudience;
    private final String prerequisites;

    /**
     * Creates a new SessionAbstract instance after validating the input.
     * 
     * @param title the session title
     * @param summary a brief summary of the session
     * @param outline the session outline
     * @param learningObjectives what attendees will learn
     * @param targetAudience the intended audience for the session
     * @param prerequisites any prerequisites for attendees
     * @throws IllegalArgumentException if the abstract is invalid
     */
    public SessionAbstract(String title, String summary, String outline,
                           String learningObjectives, String targetAudience,
                           String prerequisites) {
        validate(title, summary);
        this.title = title;
        this.summary = summary;
        this.outline = outline;
        this.learningObjectives = learningObjectives;
        this.targetAudience = targetAudience;
        this.prerequisites = prerequisites;
    }

    /**
     * Validates that the abstract components are properly formatted.
     * 
     * @param title the title to validate
     * @param summary the summary to validate
     * @throws IllegalArgumentException if the abstract is invalid
     */
    private void validate(String title, String summary) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        
        if (summary == null || summary.isBlank()) {
            throw new IllegalArgumentException("Summary cannot be empty");
        }
        
        if (title.length() > 100) {
            throw new IllegalArgumentException("Title is too long (max 100 characters)");
        }
        
        if (summary.length() > 500) {
            throw new IllegalArgumentException("Summary is too long (max 500 characters)");
        }
    }
    
    /**
     * Returns the session title.
     * 
     * @return the title
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * Returns the session summary.
     * 
     * @return the summary
     */
    public String getSummary() {
        return summary;
    }
    
    /**
     * Returns the session outline.
     * 
     * @return the outline
     */
    public String getOutline() {
        return outline;
    }
    
    /**
     * Returns the learning objectives.
     * 
     * @return the learning objectives
     */
    public String getLearningObjectives() {
        return learningObjectives;
    }
    
    /**
     * Returns the target audience.
     * 
     * @return the target audience
     */
    public String getTargetAudience() {
        return targetAudience;
    }
    
    /**
     * Returns the prerequisites.
     * 
     * @return the prerequisites
     */
    public String getPrerequisites() {
        return prerequisites;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionAbstract that = (SessionAbstract) o;
        return Objects.equals(title, that.title) &&
               Objects.equals(summary, that.summary) &&
               Objects.equals(outline, that.outline) &&
               Objects.equals(learningObjectives, that.learningObjectives) &&
               Objects.equals(targetAudience, that.targetAudience) &&
               Objects.equals(prerequisites, that.prerequisites);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(title, summary, outline, learningObjectives, targetAudience, prerequisites);
    }
    
    @Override
    public String toString() {
        return "SessionAbstract{" +
                "title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                '}';
    }
}
