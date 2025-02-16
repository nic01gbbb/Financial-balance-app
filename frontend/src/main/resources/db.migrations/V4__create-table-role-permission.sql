CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE role_permission
(
    id            UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    role_id       uuid NOT NULL,
    permission_id uuid NOT NULL,
    FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (permission_id) REFERENCES permissions (id) ON DELETE CASCADE
        ON UPDATE CASCADE
);