CREATE TABLE account
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR NOT NULL,
    email    VARCHAR NOT NULL UNIQUE,
    phone    VARCHAR NOT NULL UNIQUE,
    created  TIMESTAMP DEFAULT NOW(),
    modified TIMESTAMP DEFAULT NOW()
);

CREATE TABLE ticket
(
    id         SERIAL PRIMARY KEY,
    session_id INT NOT NULL,
    line       INT NOT NULL,
    place      INT NOT NULL,
    account_id INT NOT NULL REFERENCES account (id),
    created    TIMESTAMP DEFAULT NOW(),
    modified   TIMESTAMP DEFAULT NOW()
);

ALTER TABLE ticket
    ADD CONSTRAINT unique_session_id_row_cell UNIQUE (session_id, line, place);