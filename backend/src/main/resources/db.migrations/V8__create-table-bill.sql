CREATE TABLE bills (
                       id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
                       description VARCHAR(255),
                       amount DECIMAL(10,2),
                       due_date DATE
);