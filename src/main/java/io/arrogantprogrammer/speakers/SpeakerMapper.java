package io.arrogantprogrammer.speakers;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.stream.Collectors;

@ApplicationScoped
public class SpeakerMapper {
    
    public SpeakerDTO toDTO(Speaker speaker) {
        if (speaker == null) {
            return null;
        }
        
        SpeakerDTO dto = new SpeakerDTO();
        dto.id = speaker.id;
        dto.name = speaker.name;
        dto.title = speaker.title;
        dto.company = speaker.company;
        dto.bio = speaker.bio;
        dto.headshot = speaker.headshot;
        
        if (speaker.socialMedia != null) {
            dto.socialMedia = speaker.socialMedia.stream()
                .map(sm -> {
                    SocialMediaDTO smDto = new SocialMediaDTO();
                    smDto.id = sm.id;
                    smDto.platform = sm.platform;
                    smDto.handle = sm.handle;
                    return smDto;
                })
                .collect(Collectors.toList());
        }
        
        return dto;
    }
    
    public Speaker toEntity(SpeakerDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Speaker speaker = new Speaker();
        speaker.id = dto.id;
        speaker.name = dto.name;
        speaker.title = dto.title;
        speaker.company = dto.company;
        speaker.bio = dto.bio;
        speaker.headshot = dto.headshot;
        
        return speaker;
    }
} 