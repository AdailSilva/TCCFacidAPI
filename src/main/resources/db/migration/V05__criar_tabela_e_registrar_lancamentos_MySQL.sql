-- MySQL
CREATE TABLE lancamentos (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	descricao VARCHAR(50) NOT NULL,
	data_vencimento DATE NOT NULL,
	data_pagamento DATE,
	valor DECIMAL(10,2) NOT NULL,
	observacao VARCHAR(100),
	tipo VARCHAR(20) NOT NULL,
	id_categoria BIGINT(20) NOT NULL,
	id_pessoa BIGINT(20) NOT NULL,
	FOREIGN KEY (id_categoria) REFERENCES categorias(id),
	FOREIGN KEY (id_pessoa) REFERENCES pessoas(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- -----------------------------------------------------
-- Selecionando o banco de dados `tccfacid`
-- -----------------------------------------------------
USE `tccfacidapi`;



INSERT INTO lancamentos (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, id_categoria, id_pessoa) values ('Salário Mensal', '2011-01-10', null, 6500.00, 'Distribuição de lucros', 'RECEITA', 1, 1);
INSERT INTO lancamentos (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, id_categoria, id_pessoa) values ('Viajem Sobral', '2010-02-10', '2019-02-10', 100.32, null, 'DESPESA', 2, 5);
INSERT INTO lancamentos (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, id_categoria, id_pessoa) values ('Gateway LoRaWAN', '2009-03-10', null, 120, null, 'RECEITA', 3, 1);
INSERT INTO lancamentos (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, id_categoria, id_pessoa) values ('DAE ICMS', '2000-02-10', '2019-02-10', 110.44, 'Geração', 'RECEITA', 3, 4);
INSERT INTO lancamentos (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, id_categoria, id_pessoa) values ('DAE IPVA', '2001-03-11', null, 200.30, null, 'DESPESA', 3, 7);
INSERT INTO lancamentos (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, id_categoria, id_pessoa) values ('Peças Extra', '2007-03-10', '2019-03-10', 1010.32, null, 'RECEITA', 4, 6);
INSERT INTO lancamentos (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, id_categoria, id_pessoa) values ('Compra Fios', '2010-01-10', null, 500, null, 'RECEITA', 1, 7);
INSERT INTO lancamentos (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, id_categoria, id_pessoa) values ('Uber', '2009-03-10', '2019-03-10', 400.32, null, 'DESPESA', 4, 3);
INSERT INTO lancamentos (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, id_categoria, id_pessoa) values ('Despachante', '2008-02-10', null, 123.64, 'Multas', 'DESPESA', 3, 4);
INSERT INTO lancamentos (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, id_categoria, id_pessoa) values ('Frete Peças', '2013-01-10', '2019-02-10', 665.33, null, 'RECEITA', 5, 7);
INSERT INTO lancamentos (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, id_categoria, id_pessoa) values ('Compra Relés', '1999-02-10', null, 8.32, null, 'DESPESA', 1, 5);
INSERT INTO lancamentos (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, id_categoria, id_pessoa) values ('Eletrônicos', '1980-02-10', '2019-02-10', 2100.32, null, 'DESPESA', 5, 4);
INSERT INTO lancamentos (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, id_categoria, id_pessoa) values ('Instrumentos', '2000-01-10', null, 1040.32, null, 'DESPESA', 2, 3);
INSERT INTO lancamentos (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, id_categoria, id_pessoa) values ('Almoço', '2019-01-10', '2019-01-10', 4.32, null, 'DESPESA', 4, 2);
INSERT INTO lancamentos (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, id_categoria, id_pessoa) values ('Lanche', '2019-02-10', null, 10.20, null, 'DESPESA', 5, 1);
