CREATE TABLE sponsor (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    tier VARCHAR(20) NOT NULL,
    logo VARCHAR(255),
    website VARCHAR(255),
    description TEXT
); 