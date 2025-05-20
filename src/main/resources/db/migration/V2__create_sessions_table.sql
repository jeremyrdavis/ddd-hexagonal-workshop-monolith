-- Create sessions table
CREATE TABLE sessions (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    room VARCHAR(255)
);

-- Create junction table for sessions and speakerEntities
CREATE TABLE session_speakers (
    session_id BIGINT NOT NULL,
    speaker_id BIGINT NOT NULL,
    PRIMARY KEY (session_id, speaker_id),
    CONSTRAINT fk_session FOREIGN KEY (session_id) REFERENCES sessions(id) ON DELETE CASCADE,
    CONSTRAINT fk_speaker FOREIGN KEY (speaker_id) REFERENCES speakerEntities(id) ON DELETE CASCADE
);

-- Indexes for performance
CREATE INDEX idx_session_speakers_session_id ON session_speakers(session_id);
CREATE INDEX idx_session_speakers_speaker_id ON session_speakers(speaker_id); 