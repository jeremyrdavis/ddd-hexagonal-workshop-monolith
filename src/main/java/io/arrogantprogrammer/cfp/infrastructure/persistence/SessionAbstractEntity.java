package io.arrogantprogrammer.cfp.infrastructure.persistence;

import jakarta.persistence.Embeddable;

@Embeddable
public class SessionAbstractEntity {

    String title;
    String summary;
    String outline;
    String learningObjectives;
    String targetAudience;
    String prerequisites;

    protected SessionAbstractEntity() {
    }

    protected SessionAbstractEntity(String title, String summary, String outline, String learningObjectives, String targetAudience, String prerequisites) {
        this.title = title;
        this.summary = summary;
        this.outline = outline;
        this.learningObjectives = learningObjectives;
        this.targetAudience = targetAudience;
        this.prerequisites = prerequisites;
    }
}
