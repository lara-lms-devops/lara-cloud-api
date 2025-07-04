CREATE TABLE IF NOT EXISTS users (
    uuid UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name TEXT NOT NULL,
    email TEXT NOT NULL,
    hashed_password TEXT NOT NULL,
    deleted BOOLEAN NOT NULL DEFAULT false,
    creation_date TIMESTAMP NOT NULL,
    update_date TIMESTAMP NOT NULL
);