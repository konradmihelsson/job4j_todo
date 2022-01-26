CREATE TABLE IF NOT EXISTS items
(
    id          SERIAL PRIMARY KEY,
    description TEXT NOT NULL,
    created     TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    done        BOOLEAN            DEFAULT FALSE
);
