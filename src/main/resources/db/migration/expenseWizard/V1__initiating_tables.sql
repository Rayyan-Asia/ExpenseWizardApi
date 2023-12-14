CREATE TABLE User (
                      id BINARY(16) PRIMARY KEY,
                      full_name VARCHAR(255) NOT NULL,
                      email VARCHAR(255) NOT NULL UNIQUE,
                      birth_date DATE
);

CREATE TABLE Project (
                         id BINARY(16) PRIMARY KEY,
                         user_id VARCHAR(255) NOT NULL,
                         project_name VARCHAR(255) NOT NULL,
                         `limit` FLOAT NOT NULL,
                         CONSTRAINT fk_user_project FOREIGN KEY (user_id) REFERENCES User(id)
);

CREATE TABLE Expense (
                         id BINARY(16) PRIMARY KEY,
                         project_id BINARY(16) NOT NULL,
                         cost FLOAT NOT NULL,
                         timestamp TIMESTAMP NOT NULL,
                         category INT,
                         CONSTRAINT fk_project_expense FOREIGN KEY (project_id) REFERENCES Project(id),
);
