-- Tabla 'users'
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    user_type VARCHAR(20) NOT NULL
);

-- Tabla 'tickets'
CREATE TABLE tickets (
    id SERIAL PRIMARY KEY,
    subject VARCHAR(100) NOT NULL,
    description VARCHAR(500) NOT NULL,
    creation_date DATE NOT NULL,
    update_date DATE NOT NULL,
    status INT NOT NULL,
    priority INT NOT NULL DEFAULT 0,
    owner_id INT NOT NULL,
    assignee_id INT,
    CONSTRAINT fk_owner FOREIGN KEY (owner_id) REFERENCES users(id),
    CONSTRAINT fk_assignee FOREIGN KEY (assignee_id) REFERENCES users(id)
);