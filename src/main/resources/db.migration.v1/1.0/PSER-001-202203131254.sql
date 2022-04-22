ALTER TABLE sportsequipmentrent.orders
    ALTER COLUMN order_datetime DROP NOT NULL,
    ALTER COLUMN destination_datetime DROP NOT NULL,
    ALTER COLUMN expire_datetime DROP NOT NULL,
    ALTER COLUMN total_price DROP NOT NULL,
    ALTER COLUMN total_price SET DEFAULT 0.0;

INSERT INTO sportsequipmentrent.roles
VALUES (1, 'ADMIN'),
       (2, 'USER');

ALTER TABLE sportsequipmentrent.users
    ALTER COLUMN role_id SET DEFAULT 2;

INSERT INTO sportsequipmentrent.order_statuses
VALUES (1, 'IN FORMING'),
       (2, 'NEW'),
       (3, 'IN PROCESSING'),
       (4, 'READY FOR DELIVERY'),
       (5, 'ACCEPTED BY CUSTOMER'),
       (6, 'COMPLETED'),
       (7, 'CANCELED');

ALTER TABLE sportsequipmentrent.orders
    ALTER COLUMN status_id SET DEFAULT 1;

INSERT INTO sportsequipmentrent.seasons
VALUES (1, 'WINTER'),
       (2, 'SUMMER');

INSERT INTO sportsequipmentrent.ages
VALUES (1, 'UNDER 15'),
       (2, 'OVER 15'),
       (3, 'OVER 18'),
       (4, 'OVER 70');
