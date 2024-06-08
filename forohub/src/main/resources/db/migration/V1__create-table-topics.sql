

CREATE TABLE topics (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(50) NOT NULL,
    message VARCHAR(255) NOT NULL,
    created_at DATETIME DEFAULT (CURRENT_TIMESTAMP),
    status VARCHAR(20),
    author VARCHAR(50) NOT NULL,
    course VARCHAR(30) NOT NULL,
    responses VARCHAR(255)

);
