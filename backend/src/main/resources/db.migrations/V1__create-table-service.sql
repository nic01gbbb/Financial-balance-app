CREATE
EXTENSION IF NOT EXISTS "pgcrypto";

       -- Create the 'services' table
CREATE TABLE services
(
    id          UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name        VARCHAR(255)   NOT NULL,
    description TEXT           NOT NULL,
    price       DECIMAL(10, 2) NOT NULL,
    available   BOOLEAN DEFAULT TRUE
);





