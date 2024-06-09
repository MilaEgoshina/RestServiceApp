CREATE TABLE IF NOT EXISTS workers
(
    worker_id   BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    worker_firstName VARCHAR(255) NOT NULL UNIQUE
    worker_lastName  VARCHAR(255) NOT NULL,
    role_id     BIGINT REFERENCES roles (role_id)
    );

CREATE TABLE IF NOT EXISTS relations
(
    relation_id   BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    relation_name VARCHAR(255) NOT NULL UNIQUE
    );

CREATE TABLE IF NOT EXISTS computers
(
    computer_id        BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    serial_number      VARCHAR(255) NOT NULL UNIQUE,
    worker_id          BIGINT REFERENCES workers (worker_id)
    );

CREATE TABLE IF NOT EXISTS workerAndRelations
(
    worker_relations_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    worker_id           BIGINT REFERENCES workers (worker_id),
    relation_id         BIGINT REFERENCES relations (relation_id),
    CONSTRAINT unique_link UNIQUE (worker_id, relation_id)
    );

)