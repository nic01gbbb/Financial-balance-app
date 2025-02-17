CREATE TABLE transactions
(
    id               UUID      DEFAULT gen_random_uuid() PRIMARY KEY,
    account_id       UUID         NOT NULL,
    amount           DECIMAL(10, 2) NOT NULL,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    transaction_type VARCHAR(50)    NOT NULL CHECK (transaction_type IN ('INCOME', 'EXPENSE')),
    FOREIGN KEY (account_id) REFERENCES account (id)
);
