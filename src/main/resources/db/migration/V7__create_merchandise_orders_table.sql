CREATE TABLE merchandise_orders (
    id BIGSERIAL PRIMARY KEY,
    attendee_id BIGINT NOT NULL,
    t_shirt_size VARCHAR(10) NOT NULL,
    order_status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (attendee_id) REFERENCES attendees(id)
); 