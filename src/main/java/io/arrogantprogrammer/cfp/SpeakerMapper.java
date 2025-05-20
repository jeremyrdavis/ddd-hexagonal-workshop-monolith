package io.arrogantprogrammer.cfp;

import io.arrogantprogrammer.domain.valueobjects.Email;
import io.arrogantprogrammer.domain.valueobjects.Name;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Mapper for Speaker entities and DTOs.
 */
@ApplicationScoped
public class SpeakerMapper {

    /**
     * Converts a Speaker entity to a SpeakerDTO.
     * 
     * @param speaker the entity to convert
     * @return the DTO
     */
    public SpeakerDTO toDTO(Speaker speaker) {
        if (speaker == null) {
            return null;
        }

        return new SpeakerDTO(
            speaker.getId(),
            speaker.getName().getFirstName(),
            speaker.getName().getLastName(),
            speaker.getEmail().getValue(),
            speaker.getBio(),
            speaker.getCompany(),
            speaker.getTitle(),
            speaker.getPhotoUrl()
        );
    }

    /**
     * Converts a SpeakerDTO to a Speaker entity.
     * 
     * @param dto the DTO to convert
     * @return the entity
     */
    public Speaker toEntity(SpeakerDTO dto) {
        if (dto == null) {
            return null;
        }

        return Speaker.create(
            dto.firstName,
            dto.lastName,
            dto.email,
            dto.bio,
            dto.company,
            dto.title,
            dto.photoUrl
        );
    }

    /**
     * Updates an existing Speaker entity with data from a SpeakerDTO.
     * 
     * @param speaker the entity to update
     * @param dto the DTO with the new data
     * @return the updated entity
     */
    public Speaker updateEntityFromDTO(Speaker speaker, SpeakerDTO dto) {
        if (speaker == null || dto == null) {
            return speaker;
        }

        speaker.updateName(new Name(dto.firstName, dto.lastName));
        speaker.updateEmail(new Email(dto.email));
        speaker.updateBio(dto.bio);
        speaker.updateCompany(dto.company);
        speaker.updateTitle(dto.title);
        speaker.updatePhotoUrl(dto.photoUrl);

        return speaker;
    }
}
