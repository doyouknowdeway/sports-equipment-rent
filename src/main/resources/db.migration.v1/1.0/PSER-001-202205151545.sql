ALTER TABLE sportsequipmentrent.users
    DROP COLUMN registration_datetime;

ALTER TABLE sportsequipmentrent.users
    ADD COLUMN provider              VARCHAR(30) DEFAULT 'LOCAL',
    ADD COLUMN registration_datetime TIMESTAMPTZ DEFAULT '2022-05-15T00:00:00Z' NOT NULL;

ALTER TABLE sportsequipmentrent.profiles
    ALTER COLUMN first_name DROP NOT NULL,
    ALTER COLUMN second_name DROP NOT NULL,
    ALTER COLUMN last_name DROP NOT NULL,
    DROP COLUMN login,
    DROP COLUMN email;

ALTER TABLE sportsequipmentrent.profiles
    ADD COLUMN login VARCHAR(50),
    ADD COLUMN email VARCHAR(100) UNIQUE NOT NULL DEFAULT 'katseliv@yandex.ru';
