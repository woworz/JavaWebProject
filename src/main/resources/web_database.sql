USE web_database;
CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       password VARCHAR(100) NOT NULL
);

CREATE TABLE todos (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       title VARCHAR(100) NOT NULL,
                       description TEXT,
                       completed BOOLEAN NOT NULL,
                       user_id BIGINT,
                       FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE reminders (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           todo_id BIGINT,
                           reminder_time TIMESTAMP,
                           FOREIGN KEY (todo_id) REFERENCES todos(id)
);

