CREATE TABLE sportsequipmentrent.jwt_tokens
(
    id         SERIAL PRIMARY KEY,
    token      VARCHAR(500)                                         NOT NULL,
    type       VARCHAR(50)                                          NOT NULL,
    profile_id INTEGER REFERENCES sportsequipmentrent.profiles (id) NOT NULL
);