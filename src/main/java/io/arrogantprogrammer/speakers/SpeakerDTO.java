package io.arrogantprogrammer.speakers;

import java.util.List;

public class SpeakerDTO {
    public Long id;
    public String name;
    public String title;
    public String company;
    public String bio;
    public String headshot;
    public List<SocialMediaDTO> socialMedia;

    public SpeakerDTO() {
    }

    public SpeakerDTO(SpeakerEntity speakerEntity) {
        this.id = speakerEntity.id;
        this.name = speakerEntity.name;
        this.title = speakerEntity.title;
        this.company = speakerEntity.company;
        this.bio = speakerEntity.bio;
        this.headshot = speakerEntity.headshot;
        this.socialMedia = speakerEntity.socialMedia.stream()
                .map(SocialMediaDTO::new)
                .toList();
    }
} 