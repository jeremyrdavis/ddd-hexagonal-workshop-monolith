CREATE TABLE locations (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(20) NOT NULL,
    address VARCHAR(255) NOT NULL,
    description TEXT,
    map_url VARCHAR(255)
); 