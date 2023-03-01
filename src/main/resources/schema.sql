DROP TABLE IF EXISTS USUARIOS;
DROP TABLE IF EXISTS DEPARTAMENTO;
DROP TABLE IF EXISTS EMPLEADO;

-- USERS
CREATE TABLE IF NOT EXISTS USUARIOS
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    email    TEXT NOT NULL,
    nombre   TEXT NOT NULL,
    password TEXT NOT NULL,
    rol      TEXT NOT NULL

);

CREATE TABLE IF NOT EXISTS DEPARTAMENTO
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre     TEXT      NOT NULL,
    presupuesto FLOAT NOT NULL
);

CREATE TABLE IF NOT EXISTS EMPLEADO
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre            TEXT      NOT NULL,
    email            TEXT      NOT NULL,
    avatar            TEXT      NOT NULL,
    idDep   BIGINT NOT NULL
);

-- pepe1234
INSERT INTO USUARIOS(email, nombre, password, rol)
VALUES ('prueba@prueba.com',
        'alex','$2a$12$249dkPGBT6dH46f4Dbu7ouEuO8eZ7joonzWGefPJbHH8eDpJy0oCq','ADMIN');

INSERT INTO USUARIOS(email, nombre, password, rol)
VALUES ('Staxx@prueba.com',
        'Staxx','Staxx1234','USER');

INSERT INTO USUARIOS(email, nombre, password, rol)
VALUES ('Teco@Teco.com',
        'Teco','Teco1234','ADMIN');

INSERT INTO DEPARTAMENTO (id,nombre, presupuesto) VALUES (1,'Finanzas',1300.25);
INSERT INTO DEPARTAMENTO (id,nombre, presupuesto) VALUES (2,'Tecnologia',84555.25);
INSERT INTO DEPARTAMENTO (id,nombre, presupuesto) VALUES (3,'Desarrollo',13078890.25);


INSERT INTO EMPLEADO(nombre, email, avatar, idDep) VALUES ('Julian','julian@julian.com','foto',1);
INSERT INTO EMPLEADO(nombre, email, avatar, idDep) VALUES ('Dream','Dream@Dream.com','foto',2);
INSERT INTO EMPLEADO(nombre, email, avatar, idDep) VALUES ('Ekix','Ekix@Ekix.com','foto',3);

