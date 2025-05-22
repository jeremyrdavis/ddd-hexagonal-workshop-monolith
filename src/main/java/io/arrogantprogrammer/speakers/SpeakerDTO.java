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

    public SpeakerDTO(Speaker speaker) {
        this.id = speaker.id;
        this.name = speaker.name;
        this.title = speaker.title;
        this.company = speaker.company;
        this.bio = speaker.bio;
        this.headshot = speaker.headshot;
        this.socialMedia = speaker.socialMedia.stream()
                .map(SocialMediaDTO::new)
                .toList();
    }
} 