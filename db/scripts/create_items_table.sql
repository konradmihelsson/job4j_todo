CREATE TABLE IF NOT EXISTS users
(
    id SERIAL PRIMARY KEY,
    email    TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL
);
CREATE TABLE IF NOT EXISTS items
(
    id          SERIAL PRIMARY KEY,
    description TEXT NOT NULL,
    created     TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    done        BOOLEAN            DEFAULT FALSE,
    user_id INT NOT NULL REFERENCES users(id)
);
