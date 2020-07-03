-- MySQL
CREATE TABLE pessoas (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL,
	logradouro VARCHAR(50),
	numero VARCHAR(6),
	complemento VARCHAR(30),
	bairro VARCHAR(30),
	cep VARCHAR(30),
	cidade VARCHAR(30),
	estado VARCHAR(30),
	ativo BOOLEAN NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- -----------------------------------------------------
-- Selecionando o banco de dados `tccfacid`
-- -----------------------------------------------------
USE `tccfacidapi`;



INSERT INTO pessoas (nome, logradouro, numero, complemento, bairro, cep, cidade, estado, ativo) values ('Adail dos Santos Silva', 'Rua do Abacaxi', '101', null, 'Brasil', '62.500-000', 'Itapipoca', 'CE', true);
INSERT INTO pessoas (nome, logradouro, numero, complemento, bairro, cep, cidade, estado, ativo) values ('João Rodrigues da Silva', 'Rua do Sabiá', '110', 'Apto 101', 'Colina', '11.400-120', 'Ribeirão Preto', 'SP', true);
INSERT INTO pessoas (nome, logradouro, numero, complemento, bairro, cep, cidade, estado, ativo) values ('Teresa Cristina dos Santos Silva', 'Rua Professor Osvaldo Nogueira Lima', '505', null, 'Morumbi', '62.320-000', 'Tianguá', 'CE', true);
INSERT INTO pessoas (nome, logradouro, numero, complemento, bairro, cep, cidade, estado, ativo) values ('Abigail dos Santos Silva', 'Rua do Motorista', '123', 'Apto 302', 'Aparecida', '38.400-112', 'Salvador', 'BA', true);
INSERT INTO pessoas (nome, logradouro, numero, complemento, bairro, cep, cidade, estado, ativo) values ('Alice dos Santos Silva', 'Av Rio Branco', '321', null, 'Jardins', '56.400-121', 'Natal', 'RN', true);
INSERT INTO pessoas (nome, logradouro, numero, complemento, bairro, cep, cidade, estado, ativo) values ('Avila dos Santos Silva', 'Av Brasil', '100', null, 'Tubalina', '77.400-122', 'Porto Alegre', 'RS', true);
INSERT INTO pessoas (nome, logradouro, numero, complemento, bairro, cep, cidade, estado, ativo) values ('Henrique Medeiros', 'Rua do Sapo', '1120', 'Apto 201', 'Centro', '12.400-212', 'Rio de Janeiro', 'RJ', true);
INSERT INTO pessoas (nome, logradouro, numero, complemento, bairro, cep, cidade, estado, ativo) values ('Carlos Santana', 'Rua da Manga', '433', null, 'Centro', '31.400-122', 'Belo Horizonte', 'MG', true);
INSERT INTO pessoas (nome, logradouro, numero, complemento, bairro, cep, cidade, estado, ativo) values ('Leonardo Oliveira', 'Rua do Músico', '566', null, 'Segismundo Pereira', '38.400-100', 'Uberlândia', 'MG', true);
INSERT INTO pessoas (nome, logradouro, numero, complemento, bairro, cep, cidade, estado, ativo) values ('Isabela Martins', 'Rua da Terra', '1233', 'Apto 10', 'Vigilato', '99.400-122', 'Manaus', 'AM', true);
INSERT INTO pessoas (nome, logradouro, numero, complemento, bairro, cep, cidade, estado, ativo) values ('Josué Teixeira dos Santos', 'Rua do Meio', '344', null, 'Brasil', '62.500-000', 'Tianguá', 'CE', true);
INSERT INTO pessoas (nome, logradouro, numero, complemento, bairro, cep, cidade, estado, ativo) values ('Adriano de Sousa Rodrigues', 'Rua 12 de Agosto', '76', 'Apto 404', 'Caeira', '11.400-120', 'Ubajara', 'CE', true);
INSERT INTO pessoas (nome, logradouro, numero, complemento, bairro, cep, cidade, estado, ativo) values ('Fernando Cristiano Santos', 'Rua Professor Osvaldo Nogueira Lima', '1005', null, 'Laurão', '62.320-000', 'Tianguá', 'CE', true);
INSERT INTO pessoas (nome, logradouro, numero, complemento, bairro, cep, cidade, estado, ativo) values ('Clébio Silva', 'Rua do Motorista', '123', 'Apto 302', 'Aparecida', '38.400-112', 'Salvador', 'BA', true);
INSERT INTO pessoas (nome, logradouro, numero, complemento, bairro, cep, cidade, estado, ativo) values ('Antônio Medeiros', 'Av Rio Branco', '321', null, 'Jardins', '56.400-121', 'Natal', 'RN', true);
INSERT INTO pessoas (nome, logradouro, numero, complemento, bairro, cep, cidade, estado, ativo) values ('José Nicolau', 'Av Brasil', '100', null, 'Tubalina', '77.400-122', 'Porto Alegre', 'RS', true);
INSERT INTO pessoas (nome, logradouro, numero, complemento, bairro, cep, cidade, estado, ativo) values ('Bruna Marinho', 'Rua do Sapo', '1120', 'Apto 201', 'Centro', '12.400-212', 'Rio de Janeiro', 'RJ', true);
INSERT INTO pessoas (nome, logradouro, numero, complemento, bairro, cep, cidade, estado, ativo) values ('Felipe Santos', 'Rua da Manga', '433', null, 'Centro', '31.400-122', 'Belo Horizonte', 'MG', true);
INSERT INTO pessoas (nome, logradouro, numero, complemento, bairro, cep, cidade, estado, ativo) values ('José Carlos Siqueira', 'Rua do Músico', '566', null, 'Segismundo Pereira', '38.400-100', 'Uberlândia', 'MG', true);
INSERT INTO pessoas (nome, logradouro, numero, complemento, bairro, cep, cidade, estado, ativo) values ('Raimunda Braz', 'Rua da Terra', '1233', 'Apto 10', 'Vigilato', '99.400-122', 'Manaus', 'AM', true);
