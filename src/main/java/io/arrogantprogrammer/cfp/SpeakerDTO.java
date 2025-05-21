package io.arrogantprogrammer.cfp;

import io.arrogantprogrammer.domain.valueobjects.Email;
import io.arrogantprogrammer.domain.valueobjects.Name;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO for Speaker.
 */
public record SpeakerDTO(

    @NotBlank(message = "Name is required")
    Name name,

    @NotBlank(message = "Email is required")
    Email email,

    @Size(max = 500, message = "Bio cannot exceed 500 characters")
    String bio,

    String company,

    String title,

    String photoUrl
) {}
