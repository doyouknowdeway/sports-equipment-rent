ALTER TABLE sportsequipmentrent.profiles
    DROP COLUMN password;

ALTER TABLE sportsequipmentrent.profiles
    ADD COLUMN password VARCHAR(60) NOT NULL DEFAULT '';
