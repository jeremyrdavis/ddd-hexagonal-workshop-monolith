package io.arrogantprogrammer.speakers;

public class SocialMediaDTO {
    public Long id;
    public String platform;
    public String handle;

    public SocialMediaDTO() {
    }

    public SocialMediaDTO(SocialMedia socialMedia) {
        this.id = socialMedia.id;
        this.platform = socialMedia.platform;
        this.handle = socialMedia.handle;
    }
} 