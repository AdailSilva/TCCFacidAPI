-- MySQL
CREATE TABLE usuarios (
	id BIGINT(20) PRIMARY KEY,
	nome VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	senha VARCHAR(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE permissoes (
	id BIGINT(20) PRIMARY KEY,
	descricao VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE permissao_usuario (
	id_usuario BIGINT(20) NOT NULL,
	id_permissao BIGINT(20) NOT NULL,
	PRIMARY KEY (id_usuario, id_permissao),
	FOREIGN KEY (id_usuario) REFERENCES usuarios(id),
	FOREIGN KEY (id_permissao) REFERENCES permissoes(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- -----------------------------------------------------
-- Selecionando o banco de dados `tccfacid`
-- -----------------------------------------------------
USE `tccfacidapi`;



INSERT INTO usuarios (id, nome, email, senha) values (1, 'Administrador', 'admin@adailsilva.com', '$2a$10$F6YxpPi8AGfMwV8iw8gf3eKUG/ZagxEN9D/ojXxuMkpaJz/FdXqaS');
INSERT INTO usuarios (id, nome, email, senha) values (2, 'AdailSilva', 'adailsilva@adailsilva.com', '$2a$10$F6YxpPi8AGfMwV8iw8gf3eKUG/ZagxEN9D/ojXxuMkpaJz/FdXqaS');
INSERT INTO usuarios (id, nome, email, senha) values (3, 'Usuário', 'user@adailsilva.com', '$2a$10$F6YxpPi8AGfMwV8iw8gf3eKUG/ZagxEN9D/ojXxuMkpaJz/FdXqaS');


-- ROLES
INSERT INTO permissoes (id, descricao) values (1, 'ROLE_CREATE_RAWBODY');
INSERT INTO permissoes (id, descricao) values (2, 'ROLE_READ_RAWBODY');
INSERT INTO permissoes (id, descricao) values (3, 'ROLE_UPDATE_RAWBODY');
INSERT INTO permissoes (id, descricao) values (4, 'ROLE_DELETE_RAWBODY');

INSERT INTO permissoes (id, descricao) values (5, 'ROLE_CREATE_PAYLOADFIELD');
INSERT INTO permissoes (id, descricao) values (6, 'ROLE_READ_PAYLOADFIELD');
INSERT INTO permissoes (id, descricao) values (7, 'ROLE_UPDATE_PAYLOADFIELD');
INSERT INTO permissoes (id, descricao) values (8, 'ROLE_DELETE_PAYLOADFIELD');

INSERT INTO permissoes (id, descricao) values (9, 'ROLE_CREATE_METADATA');
INSERT INTO permissoes (id, descricao) values (10, 'ROLE_READ_METADATA');
INSERT INTO permissoes (id, descricao) values (11, 'ROLE_UPDATE_METADATA');
INSERT INTO permissoes (id, descricao) values (12, 'ROLE_DELETE_METADATA');

INSERT INTO permissoes (id, descricao) values (13, 'ROLE_CREATE_GATEWAY');
INSERT INTO permissoes (id, descricao) values (14, 'ROLE_READ_GATEWAY');
INSERT INTO permissoes (id, descricao) values (15, 'ROLE_UPDATE_GATEWAY');
INSERT INTO permissoes (id, descricao) values (16, 'ROLE_DELETE_GATEWAY');

INSERT INTO permissoes (id, descricao) values (17, 'ROLE_CREATE_CATEGORIA');
INSERT INTO permissoes (id, descricao) values (18, 'ROLE_READ_CATEGORIA');
INSERT INTO permissoes (id, descricao) values (19, 'ROLE_UPDATE_CATEGORIA');
INSERT INTO permissoes (id, descricao) values (20, 'ROLE_DELETE_CATEGORIA');

INSERT INTO permissoes (id, descricao) values (21, 'ROLE_CREATE_PESSOA');
INSERT INTO permissoes (id, descricao) values (22, 'ROLE_READ_PESSOA');
INSERT INTO permissoes (id, descricao) values (23, 'ROLE_UPDATE_PESSOA');
INSERT INTO permissoes (id, descricao) values (24, 'ROLE_DELETE_PESSOA');

INSERT INTO permissoes (id, descricao) values (25, 'ROLE_CREATE_LANCAMENTO');
INSERT INTO permissoes (id, descricao) values (26, 'ROLE_READ_LANCAMENTO');
INSERT INTO permissoes (id, descricao) values (27, 'ROLE_UPDATE_LANCAMENTO');
INSERT INTO permissoes (id, descricao) values (28, 'ROLE_DELETE_LANCAMENTO');



-- USER: admin
-- RAWBODY
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (1, 1);
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (1, 2);
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (1, 3);
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (1, 4);

-- PAYLOADFIELD
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (1, 5);
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (1, 6);
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (1, 7);
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (1, 8);

-- METADATA
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (1, 9);
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (1, 10);
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (1, 11);
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (1, 12);

-- GATEWAY
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (1, 13);
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (1, 14);
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (1, 15);
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (1, 16);

-- CATEGORIA
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (1, 17);
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (1, 18);
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (1, 19);
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (1, 20);

-- PESSOA
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (1, 21);
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (1, 22);
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (1, 23);
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (1, 24);

-- LANCAMENTO
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (1, 25);
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (1, 26);
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (1, 27);
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (1, 28);



-- USER: adailsilva
-- RAWBODY
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (2, 1);
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (2, 2);
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (2, 3);
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (2, 4);

-- PAYLOADFIELD
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (2, 5);
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (2, 6);
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (2, 7);
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (2, 8);

-- METADATA
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (2, 9);
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (2, 10);
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (2, 11);
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (2, 12);

-- GATEWAY
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (2, 13);
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (2, 14);
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (2, 15);
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (2, 16);

-- CATEGORIA
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (2, 17);
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (2, 18);
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (2, 19);
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (2, 20);

-- PESSOA
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (2, 21);
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (2, 22);
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (2, 23);
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (2, 24);

-- LANCAMENTO
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (2, 25);
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (2, 26);
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (2, 27);
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (2, 28);



-- USER: user
-- RAWBODY
-- INSERT INTO permissao_usuario (id_usuario, id_permissao) values (3, 1);
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (3, 2);
-- INSERT INTO permissao_usuario (id_usuario, id_permissao) values (3, 3);
-- INSERT INTO permissao_usuario (id_usuario, id_permissao) values (3, 4);

-- PAYLOADFIELD
-- INSERT INTO permissao_usuario (id_usuario, id_permissao) values (3, 5);
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (3, 6);
-- INSERT INTO permissao_usuario (id_usuario, id_permissao) values (3, 7);
-- INSERT INTO permissao_usuario (id_usuario, id_permissao) values (3, 8);

-- METADATA
-- INSERT INTO permissao_usuario (id_usuario, id_permissao) values (3, 9);
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (3, 10);
-- INSERT INTO permissao_usuario (id_usuario, id_permissao) values (3, 11);
-- INSERT INTO permissao_usuario (id_usuario, id_permissao) values (3, 12);

-- GATEWAY
-- INSERT INTO permissao_usuario (id_usuario, id_permissao) values (3, 13);
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (3, 14);
-- INSERT INTO permissao_usuario (id_usuario, id_permissao) values (3, 15);
-- INSERT INTO permissao_usuario (id_usuario, id_permissao) values (3, 16);

-- CATEGORIA
-- INSERT INTO permissao_usuario (id_usuario, id_permissao) values (3, 17);
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (3, 18);
-- INSERT INTO permissao_usuario (id_usuario, id_permissao) values (3, 19);
-- INSERT INTO permissao_usuario (id_usuario, id_permissao) values (3, 20);

-- PESSOA
-- INSERT INTO permissao_usuario (id_usuario, id_permissao) values (3, 21);
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (3, 22);
-- INSERT INTO permissao_usuario (id_usuario, id_permissao) values (3, 23);
-- INSERT INTO permissao_usuario (id_usuario, id_permissao) values (3, 24);

-- LANCAMENTO
-- INSERT INTO permissao_usuario (id_usuario, id_permissao) values (3, 25);
INSERT INTO permissao_usuario (id_usuario, id_permissao) values (3, 26);
-- INSERT INTO permissao_usuario (id_usuario, id_permissao) values (3, 27);
-- INSERT INTO permissao_usuario (id_usuario, id_permissao) values (3, 28);
