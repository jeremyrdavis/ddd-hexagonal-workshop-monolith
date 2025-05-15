CREATE TABLE social_posts (
    id BIGSERIAL PRIMARY KEY,
    content TEXT NOT NULL,
    author VARCHAR(255) NOT NULL,
    platform VARCHAR(50) NOT NULL,
    attendee_id BIGINT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (attendee_id) REFERENCES attendees(id)
); 