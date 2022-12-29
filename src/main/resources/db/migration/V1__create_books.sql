create table if not exists books
(
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    name TEXT NOT NULL,
    status VARCHAR(10) NOT NULL,
    exemplary INT NOT NULL,
    edition VARCHAR(11) NOT NULL,
    authors JSON NOT NULL,
    publisher VARCHAR(255) NOT NULL,
    origin VARCHAR(30) NOT NULL,
    year INT,
    createdAt DATETIME(6) NOT NULL,
    updatedAt DATETIME(6) NOT NULL,
    deletedAt DATETIME(6)
);