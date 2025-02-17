CREATE TABLE roles (
                       id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
                       name VARCHAR(20) NOT NULL UNIQUE CHECK (name IN ('ADMIN', 'USER'))
);
