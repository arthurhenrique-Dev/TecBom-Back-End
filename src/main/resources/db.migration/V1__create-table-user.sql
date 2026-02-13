CREATE TABLE users (
                       cpf CHAR(11) PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       password TEXT NOT NULL,
                       email VARCHAR(255) UNIQUE NOT NULL,
                       cep CHAR(8),
                       rua VARCHAR(255),
                       bairro VARCHAR(100),
                       cidade VARCHAR(100),
                       estado CHAR(19),
                       complemento VARCHAR(255),
                       numero VARCHAR(20),
                       phoneNumber VARCHAR(20),
                       role VARCHAR(50) NOT NULL,
                       status VARCHAR(50) NOT NULL,
                       token_email VARCHAR(255),
                       validated BOOLEAN DEFAULT FALSE,
                       token_password VARCHAR(255),
                       expiration_date TIMESTAMP
);

CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_status ON users(status);
CREATE INDEX idx_users_phone ON users(phoneNumber);