CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    name     VARCHAR(255)                                  NOT NULL,
    login    VARCHAR(255) UNIQUE                           NOT NULL,
    password VARCHAR(255)                                  NOT NULL
);
