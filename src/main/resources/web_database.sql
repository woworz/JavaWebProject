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
                       `category_id` BIGINT(20) DEFAULT NULL,
                       FOREIGN KEY (user_id) REFERENCES users(id),
                       CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE reminders (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           todo_id BIGINT,
                           reminder_time TIMESTAMP,
                           FOREIGN KEY (todo_id) REFERENCES todos(id)
);

CREATE TABLE categories (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(50) NOT NULL
);

ALTER TABLE reminders
    ADD CONSTRAINT fk_todo
        FOREIGN KEY (todo_id)
            REFERENCES todos(id)
            ON DELETE CASCADE;
