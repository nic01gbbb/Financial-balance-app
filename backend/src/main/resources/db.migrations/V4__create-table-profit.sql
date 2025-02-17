CREATE TABLE profits
(
    id         UUID      DEFAULT gen_random_uuid() PRIMARY KEY,
    user_id    UUID           NOT NULL,
    service_id UUID           NOT NULL,
    amount     DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (service_id) REFERENCES services (id)
);
