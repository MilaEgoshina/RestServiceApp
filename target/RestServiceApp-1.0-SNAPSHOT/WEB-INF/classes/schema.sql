CREATE TABLE IF NOT EXISTS workers
(
    worker_id   BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    worker_firstName VARCHAR(255) NOT NULL UNIQUE
    worker_lastName  VARCHAR(255) NOT NULL,
    role_id     BIGINT,
                FOREIGN KEY(role_id) REFERENCES roles (role_id)
);

CREATE TABLE IF NOT EXISTS roles
(
    role_id   BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    role_name VARCHAR(255) NOT NULL UNIQUE
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
    worker_id          BIGINT,
                       FOREIGN KEY(worker_id) REFERENCES workers (worker_id)
);

CREATE TABLE IF NOT EXISTS workerAndRelations
(
    worker_relations_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    worker_id           BIGINT REFERENCES workers (worker_id),
    relation_id         BIGINT REFERENCES relations (relation_id),
    CONSTRAINT unique_link UNIQUE (worker_id, relation_id),
    FOREIGN KEY (worker_id) REFERENCES workers (worker_id),
    FOREIGN KEY (relation_id) REFERENCES relations (relation_id)
);


INSERT INTO workers (worker_firstName, worker_lastName, role_id)
VALUES
  ('John', 'Doe', 1),
  ('Jane', 'Doe', 2),
  ('Peter', 'Pan', 3),
  ('Alice', 'Wonderland', 1),
  ('Bob', 'Builder', 2);

-- Insert data into roles table
INSERT INTO roles (role_id, role_name)
VALUES
    (1, 'Software Engineer'),
    (2, 'Data Analyst'),
    (3, 'Project Manager');

-- Insert data into relations table
INSERT INTO relations (relation_name)
VALUES
    ('Manager'),
    ('Team Lead'),
    ('Colleague'),
    ('Friend');

-- Insert data into computers table
INSERT INTO computers (serial_number, worker_id)
VALUES
    ('ABC12345', 1),
    ('DEF67890', 2),
    ('GHI01234', 3),
    ('JKL56789', 4),
    ('MNO90123', 5);

-- Insert data into workerAndRelations table
INSERT INTO workerAndRelations (worker_id, relation_id)
VALUES
    (1, 1),
    (1, 3),
    (2, 3),
    (3, 1),
    (3, 2),
    (4, 3),
    (5, 3);