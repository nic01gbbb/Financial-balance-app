CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE roles
(

    id            UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name          VARCHAR(20) NOT NULL UNIQUE,
    permission_id UUID        NOT NULL,
    FOREIGN KEY (permission_id) REFERENCES permissions (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);
