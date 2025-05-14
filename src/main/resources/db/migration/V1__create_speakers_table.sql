CREATE TABLE speakers (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    title VARCHAR(255),
    company VARCHAR(255),
    bio VARCHAR(500),
    headshot VARCHAR(255)
);

CREATE TABLE social_media (
    id BIGSERIAL PRIMARY KEY,
    speaker_id BIGINT NOT NULL,
    platform VARCHAR(50) NOT NULL,
    handle VARCHAR(255) NOT NULL,
    FOREIGN KEY (speaker_id) REFERENCES speakers(id) ON DELETE CASCADE
);

CREATE INDEX idx_speaker_name ON speakers(name);
CREATE INDEX idx_social_media_speaker ON social_media(speaker_id);
CREATE INDEX idx_social_media_platform ON social_media(platform); 