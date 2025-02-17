CREATE TABLE account (
                         id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
                         balance DECIMAL(15, 2),
                         last_updated TIMESTAMP
);
