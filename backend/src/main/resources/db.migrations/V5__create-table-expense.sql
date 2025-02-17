CREATE TABLE expenses
(
    id           UUID    DEFAULT gen_random_uuid() PRIMARY KEY,
    expense_type VARCHAR(255),
    amount       DECIMAL(10, 2),
    due_date     TIMESTAMP
);
