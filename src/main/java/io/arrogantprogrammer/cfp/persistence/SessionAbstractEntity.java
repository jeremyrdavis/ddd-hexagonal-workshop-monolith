package io.arrogantprogrammer.cfp.persistence;

import jakarta.persistence.*;

import java.util.Objects;

/**
 * SessionAbstract value object that encapsulates the abstract for a conference session.
 * This is a Domain-Driven Design value object.
 */
@Entity
public class SessionAbstractEntity {

    @Id @GeneratedValue
    private Long id;
    private String title;
    private String summary;
    private String outline;
    private String learningObjectives;
    private String targetAudience;
    private String prerequisites;

    @ManyToOne
    @JoinColumn(name = "speaker_id", nullable = false)
    private SpeakerEntity speaker;

    
    /**
     * Required by JPA
     */
    protected SessionAbstractEntity() {
    }
    
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
    public SessionAbstractEntity(String title, String summary, String outline,
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
        SessionAbstractEntity that = (SessionAbstractEntity) o;
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