CREATE SCHEMA sportsequipmentrent;

CREATE TABLE sportsequipmentrent.roles
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(30) UNIQUE
);

CREATE TABLE sportsequipmentrent.users
(
    id                    SERIAL PRIMARY KEY,
    role_id               INTEGER REFERENCES sportsequipmentrent.roles (id) ON DELETE CASCADE NOT NULL,
    registration_datetime TIMESTAMPTZ                                                         NOT NULL
);

CREATE TABLE sportsequipmentrent.profiles
(
    id          INTEGER PRIMARY KEY REFERENCES sportsequipmentrent.users (id) ON DELETE CASCADE,
    first_name  VARCHAR(50)        NOT NULL,
    second_name VARCHAR(50)        NOT NULL,
    last_name   VARCHAR(50)        NOT NULL,
    login       VARCHAR(50) UNIQUE NOT NULL,
    email       VARCHAR(100)       NOT NULL,
    password    VARCHAR(60)        NOT NULL
);

CREATE TABLE sportsequipmentrent.order_statuses
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(30) UNIQUE
);

CREATE TABLE sportsequipmentrent.orders
(
    id                   SERIAL PRIMARY KEY,
    status_id            INTEGER REFERENCES sportsequipmentrent.order_statuses (id)             NOT NULL,
    profile_id           INTEGER REFERENCES sportsequipmentrent.profiles (id) ON DELETE CASCADE NOT NULL,
    order_datetime       TIMESTAMPTZ                                                            NOT NULL,
    destination_datetime TIMESTAMPTZ                                                            NOT NULL,
    expire_datetime      TIMESTAMPTZ                                                            NOT NULL,
    total_price          DECIMAL                                                                NOT NULL
);

CREATE TABLE sportsequipmentrent.ages
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(30) UNIQUE
);

CREATE TABLE sportsequipmentrent.seasons
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(30) UNIQUE
);

CREATE TABLE sportsequipmentrent.items
(
    id            SERIAL PRIMARY KEY,
    name          VARCHAR(50)                                                           NOT NULL,
    description   VARCHAR(500)                                                          NOT NULL,
    cost_per_hour DECIMAL                                                               NOT NULL,
    age_id        INTEGER REFERENCES sportsequipmentrent.ages (id) ON DELETE CASCADE    NOT NULL,
    season_id     INTEGER REFERENCES sportsequipmentrent.seasons (id) ON DELETE CASCADE NOT NULL,
    count         INTEGER                                                               NOT NULL
);

CREATE TABLE sportsequipmentrent.order_items
(
    id       SERIAL PRIMARY KEY,
    item_id  INTEGER REFERENCES sportsequipmentrent.items (id) ON DELETE CASCADE  NOT NULL,
    order_id INTEGER REFERENCES sportsequipmentrent.orders (id) ON DELETE CASCADE NOT NULL
);
