package io.arrogantprogrammer.domain.valueobjects;

import java.util.regex.Pattern;

public record EmailVO(String value) {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
    );

    public EmailVO(String value) {
        validate(value);
        this.value = value;
    }

    /**
     * Validates that the email string is properly formatted.
     *
     * @param email the email address to validate
     * @throws IllegalArgumentException if the email is invalid
     */
    private void validate(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email format: " + email);
        }

        if (email.length() > 255) {
            throw new IllegalArgumentException("Email is too long (max 255 characters)");
        }
    }

}
