CREATE TABLE IF NOT EXISTS item(
    id SERIAL PRIMARY KEY,
    name TEXT,
    description TEXT,
    created TIMESTAMP,
    done BOOLEAN
);

CREATE TABLE IF NOT EXISTS account(
    id SERIAL PRIMARY KEY,
    name TEXT,
    login TEXT,
    password TEXT
);