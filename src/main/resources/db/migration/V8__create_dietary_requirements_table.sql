CREATE TABLE dietary_requirements (
    id BIGSERIAL PRIMARY KEY,
    attendee_id BIGINT NOT NULL,
    preference VARCHAR(50) NOT NULL,
    special_requests TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (attendee_id) REFERENCES attendees(id)
); 