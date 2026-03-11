CREATE DATABASE despensa;
USE despensa;

CREATE TABLE itens (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    categoria ENUM('armario', 'geladeira') NOT NULL,
    quantidade INT NOT NULL,
    validade DATE
);
