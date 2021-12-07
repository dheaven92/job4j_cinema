CREATE TABLE account
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR NOT NULL,
    email    VARCHAR NOT NULL UNIQUE,
    phone    VARCHAR NOT NULL UNIQUE,
    created  TIMESTAMP DEFAULT NOW(),
    modified TIMESTAMP DEFAULT NOW()
);

CREATE TABLE session
(
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL
);

INSERT INTO session (name) VALUES ('Дом Gucci');
INSERT INTO session (name) VALUES ('Обитель зла: Раккун-Сити');

CREATE TABLE ticket
(
    id         SERIAL PRIMARY KEY,
    session_id INT NOT NULL REFERENCES session (id),
    line       INT NOT NULL,
    cell       INT NOT NULL,
    account_id INT NOT NULL REFERENCES account (id),
    created    TIMESTAMP DEFAULT NOW(),
    modified   TIMESTAMP DEFAULT NOW()
);

ALTER TABLE ticket
    ADD CONSTRAINT unique_session_id_line_cell UNIQUE (session_id, line, cell);