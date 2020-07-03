-- MySQL
CREATE TABLE custom_headers (
	id BIGINT(20) PRIMARY KEY,
	custom_header VARCHAR(50) NOT NULL,
	value_custom_header VARCHAR(50) NOT NULL,
	login VARCHAR(50) NOT NULL,
	password VARCHAR(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- -----------------------------------------------------
-- Selecionando o banco de dados `tccfacid`
-- -----------------------------------------------------
USE `tccfacidapi`;



INSERT INTO custom_headers (id, custom_header, value_custom_header, login, password) values (1,'Facid','162100594','adail101@hotmail.com','$2a$10$PX.wzIc/GrFiWgnR0rx6k.sI5JNMAuquPysv97cLuoLEpnCCSppRC');
