CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE permissions (
                       id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
                       name VARCHAR(100) NOT NULL,
                       description VARCHAR(100) UNIQUE NOT NULL

);
