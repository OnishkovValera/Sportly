-- changeset sardor:init-enums

CREATE TYPE ground_type AS ENUM ('OUTDOOR', 'INDOOR');
CREATE TYPE surface_type AS ENUM ('NATURAL', 'ARTIFICIAL', 'PARQUET', 'LINOLEUM');
CREATE TYPE user_role AS ENUM ('USER', 'ADMIN');
CREATE TYPE session_status AS ENUM ('ACTIVE', 'BOOKED','CLOSED');
CREATE TYPE event_status as ENUM ('OPEN', 'FILLED', 'FINISHED');

-- changeset sardor:init-tables
CREATE TABLE users
(
    id         SERIAL PRIMARY KEY,
    firstname  VARCHAR(32)  NOT NULL,
    lastname   VARCHAR(32)  NOT NULL,
    birth_date DATE         NOT NULL,
    balance    INTEGER      NOT NULL CHECK (balance >= 0) DEFAULT 0,
    username   VARCHAR(128) NOT NULL UNIQUE,
    password   VARCHAR(128) NOT NULL,
    role       user_role    NOT NULL                      DEFAULT 'USER'
);


CREATE TABLE cities
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(32) UNIQUE NOT NULL
);


CREATE TABLE stations
(
    id      SERIAL PRIMARY KEY,
    name    VARCHAR(32) UNIQUE NOT NULL,
    city_id INTEGER            NOT NULL REFERENCES cities (id)
);

CREATE TABLE playgrounds
(
    id                  SERIAL PRIMARY KEY,
    name                VARCHAR(32)  NOT NULL,
    city_id             INTEGER      NOT NULL REFERENCES cities (id),
    address             VARCHAR(128) NOT NULL,
    ground_type         ground_type  NOT NULL,
    game_type           VARCHAR(64)  NOT NULL,
    surface_type        surface_type NOT NULL,
    max_players         INTEGER      NOT NULL CHECK (max_players > 0) DEFAULT 20,
    description         TEXT,
    length              FLOAT        NOT NULL CHECK (length > 0 ),
    width               FLOAT        NOT NULL CHECK (width > 0),
    height              FLOAT CHECK (height > 0),
    locker_room         BOOLEAN      NOT NULL,
    stands              BOOLEAN      NOT NULL,
    shower              BOOLEAN      NOT NULL,
    lighting            BOOLEAN      NOT NULL,
    parking_space       BOOLEAN      NOT NULL,
    additional_features TEXT
);

CREATE TABLE events
(
    id               SERIAL PRIMARY KEY,
    title            VARCHAR(64)              NOT NULL,
    city_id          INTEGER                  NOT NULL REFERENCES cities (id),
    description      TEXT                     NOT NULL,
    address          VARCHAR(128)             NOT NULL,
    status           event_status             NOT NULL                              DEFAULT 'OPEN',
    starts_at        TIMESTAMP WITH TIME ZONE NOT NULL,
    max_participants INTEGER                  NOT NULL CHECK (max_participants > 0) DEFAULT 20
);

CREATE TABLE participations
(
    id         SERIAL PRIMARY KEY,
    user_id    INTEGER                  NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    event_id   INTEGER                  NOT NULL REFERENCES events (id),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now()
);

CREATE TABLE playgrounds_stations
(
    id            SERIAL PRIMARY KEY,
    playground_id INTEGER REFERENCES playgrounds (id) ON DELETE CASCADE,
    station_id    INTEGER REFERENCES stations (id) ON DELETE CASCADE
);

CREATE TABLE events_stations
(
    id         SERIAL PRIMARY KEY,
    event_id   INTEGER REFERENCES events (id) ON DELETE CASCADE,
    station_id INTEGER REFERENCES stations (id) ON DELETE CASCADE
);

CREATE TABLE sessions
(
    id            SERIAL PRIMARY KEY,
    starts_at     TIMESTAMP WITH TIME ZONE NOT NULL,
    status        session_status           NOT NULL DEFAULT 'ACTIVE',
    booking_price INTEGER                  NOT NULL CHECK (booking_price > 0),
    playground_id INTEGER                  NOT NULL REFERENCES playgrounds (id) ON DELETE RESTRICT
);

CREATE TABLE bookings
(
    id         SERIAL PRIMARY KEY,
    user_id    INTEGER                  NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    session_id INTEGER                  NOT NULL UNIQUE REFERENCES sessions (id),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now()
);

CREATE TABLE feedbacks
(
    id            SERIAL PRIMARY KEY,
    user_id       INTEGER                  REFERENCES users (id) ON DELETE SET NULL,
    playground_id INTEGER                  NOT NULL REFERENCES playgrounds (id) ON DELETE CASCADE,
    message       TEXT                     NOT NULL,
    rating        INTEGER                  NOT NULL CHECK (rating > 0 AND rating < 6) DEFAULT 5,
    created_at    TIMESTAMP WITH TIME ZONE NOT NULL                                   DEFAULT now(),
    UNIQUE (user_id, playground_id)
);

CREATE TABLE playground_likes
(
    id            SERIAL PRIMARY KEY,
    user_id       INTEGER                  REFERENCES users (id) ON DELETE SET NULL,
    playground_id INTEGER                  NOT NULL REFERENCES playgrounds (id) ON DELETE CASCADE,
    created_at    TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now()
);

CREATE TABLE event_likes
(
    id         SERIAL PRIMARY KEY,
    user_id    INTEGER                  REFERENCES users (id) ON DELETE SET NULL,
    event_id   INTEGER                  NOT NULL REFERENCES events (id) ON DELETE CASCADE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now()
);

COMMIT;