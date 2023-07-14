CREATE TABLE car
(
    id    SERIAL PRIMARY KEY,
    brand VARCHAR(20),
    model VARCHAR(20),
    price MONEY
);

CREATE TABLE person
(
    id              SERIAL PRIMARY KEY,
    name            VARCHAR(20),
    age             INTEGER,
    driving_ability BOOLEAN,
    car_id          INTEGER REFERENCES car (id)
)