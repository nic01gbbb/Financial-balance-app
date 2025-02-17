CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE users
(
    id         UUID      DEFAULT gen_random_uuid() PRIMARY KEY,
    name       VARCHAR(100)                        NOT NULL,
    email      VARCHAR(100) UNIQUE                 NOT NULL,
    password   VARCHAR(255)                        NOT NULL,
    role_id    uuid                                NOT NULL, -- Chave estrangeira para a tabela roles
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
        ON UPDATE CASCADE                                    -- Relacionamento com roles
);

CREATE OR REPLACE FUNCTION update_timestamp()
    RETURNS TRIGGER AS
$$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_update_timestamp
    BEFORE UPDATE
    ON users
    FOR EACH ROW
EXECUTE FUNCTION update_timestamp();
