-- MySQL
CREATE TABLE categorias (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- -----------------------------------------------------
-- Selecionando o banco de dados `tccfacid`
-- -----------------------------------------------------
USE `tccfacidapi`;



INSERT INTO categorias (nome) VALUES ('Alimentação');
INSERT INTO categorias (nome) VALUES ('Farmácia');
INSERT INTO categorias (nome) VALUES ('Lazer');
INSERT INTO categorias (nome) VALUES ('Outras Despesas');
INSERT INTO categorias (nome) VALUES ('Supermercado');
