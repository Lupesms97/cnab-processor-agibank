CREATE TABLE IF NOT EXISTS transaction_cnab (
   id SERIAL PRIMARY KEY,
   type INT,
   date DATE,
   amount NUMERIC(10, 2),
    cpf BIGINT,
    card VARCHAR(255),
    hour TIME,
    store_owner VARCHAR(255),
    store_name VARCHAR(255)
    );
