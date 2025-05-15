CREATE TABLE attendees (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    company VARCHAR(255),
    t_shirt_size VARCHAR(10),
    dietary_restrictions VARCHAR(255),
    social_media_handle VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
); 