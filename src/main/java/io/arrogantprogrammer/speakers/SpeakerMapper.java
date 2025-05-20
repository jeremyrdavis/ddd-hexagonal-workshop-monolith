package io.arrogantprogrammer.speakers;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.stream.Collectors;

@ApplicationScoped
public class SpeakerMapper {
    
    public SpeakerDTO toDTO(SpeakerEntity speakerEntity) {
        if (speakerEntity == null) {
            return null;
        }
        
        SpeakerDTO dto = new SpeakerDTO();
        dto.id = speakerEntity.id;
        dto.name = speakerEntity.name;
        dto.title = speakerEntity.title;
        dto.company = speakerEntity.company;
        dto.bio = speakerEntity.bio;
        dto.headshot = speakerEntity.headshot;
        
        if (speakerEntity.socialMedia != null) {
            dto.socialMedia = speakerEntity.socialMedia.stream()
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
    
    public SpeakerEntity toEntity(SpeakerDTO dto) {
        if (dto == null) {
            return null;
        }
        
        SpeakerEntity speakerEntity = new SpeakerEntity();
        speakerEntity.id = dto.id;
        speakerEntity.name = dto.name;
        speakerEntity.title = dto.title;
        speakerEntity.company = dto.company;
        speakerEntity.bio = dto.bio;
        speakerEntity.headshot = dto.headshot;
        
        return speakerEntity;
    }
} 