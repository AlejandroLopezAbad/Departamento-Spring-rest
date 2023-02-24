DROP TABLE IF EXISTS USUARIOS;

-- USERS
CREATE TABLE IF NOT EXISTS USUARIOS
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    email    TEXT NOT NULL,
    nombre   TEXT NOT NULL,
    password TEXT NOT NULL,
    rol      TEXT NOT NULL

);


INSERT INTO USUARIOS(email, nombre, password, rol)
VALUES ('prueba@prueba.com',
        'alex','alex1234','ADMIN');

INSERT INTO USUARIOS(email, nombre, password, rol)
VALUES ('Staxx@prueba.com',
        'Staxx','Staxx1234','USER');

INSERT INTO USUARIOS(email, nombre, password, rol)
VALUES ('Teco@Teco.com',
        'Teco','Teco1234','ADMIN');