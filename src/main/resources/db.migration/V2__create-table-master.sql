CREATE TABLE masters (
                       cpf CHAR(11) PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       password TEXT NOT NULL,
                       email VARCHAR(255) UNIQUE NOT NULL,
                       phoneNumber VARCHAR(20),
                       role VARCHAR(50) NOT NULL,
                       status VARCHAR(50) NOT NULL
);
CREATE INDEX idx_masters_email ON masters(email);
CREATE INDEX idx_masters_status ON masters(status);
CREATE INDEX idx_masters_phone ON masters(phoneNumber);