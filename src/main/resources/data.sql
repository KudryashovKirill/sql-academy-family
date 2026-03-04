INSERT INTO good_types (good_type_name) VALUES
('Electronics'),
('Clothing'),
('Books'),
('Toys'),
('Sports'),
('Furniture'),
('Food'),
('Beauty'),
('Stationery'),
('Automotive');

INSERT INTO good_types (good_type_name)
SELECT 'Type ' || t.X
FROM SYSTEM_RANGE(11,100) t;

INSERT INTO goods (good_name, type) VALUES
('Laptop', 1),
('Smartphone', 1),
('T-Shirt', 2),
('Novel', 3),
('Basketball', 5),
('Desk', 6),
('Chocolate', 7),
('Lipstick', 8),
('Notebook', 9),
('Car Battery', 10);

INSERT INTO goods (good_name, type)
SELECT
    'Good ' || t.X,
    MOD(t.X, 10) + 1
FROM SYSTEM_RANGE(11,100) t;

INSERT INTO family_members (status, member_name, birthday) VALUES
('Active', 'Alice Johnson', DATE '1990-01-15'),
('Inactive', 'Bob Smith', DATE '1985-06-22'),
('Active', 'Charlie Brown', DATE '2000-09-05'),
('Active', 'Diana Prince', DATE '1995-12-12'),
('Inactive', 'Ethan Hunt', DATE '1982-03-03'),
('Active', 'Fiona Apple', DATE '1999-07-19'),
('Active', 'George Martin', DATE '1978-11-11'),
('Inactive', 'Hannah Montana', DATE '2002-05-23'),
('Active', 'Ian McKellen', DATE '1939-05-25'),
('Inactive', 'Jane Doe', DATE '1991-08-08');

INSERT INTO family_members (status, member_name, birthday)
SELECT
    CASE
        WHEN MOD(t.X, 2) = 0 THEN 'Active'
        ELSE 'Inactive'
    END,
    'Member ' || t.X,
    DATEADD('DAY', t.X, DATE '2000-01-01')
FROM SYSTEM_RANGE(11,100) t;

INSERT INTO payments (family_member, good, amount, unit_price, date)
SELECT
    CAST(RAND() * 99 + 1 AS INT),
    CAST(RAND() * 99 + 1 AS INT),
    CAST(RAND() * 10 + 1 AS INT),
    CAST(RAND() * 500 + 10 AS INT),
    DATEADD('DAY', -CAST(RAND() * 365 AS INT), CURRENT_TIMESTAMP)
FROM SYSTEM_RANGE(1,100);