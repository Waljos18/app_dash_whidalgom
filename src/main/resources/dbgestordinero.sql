CREATE DATABASE smartpocket;
USE smartpocket;
CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    contrasena VARCHAR(100) NOT NULL
);
INSERT INTO usuarios (email, contrasena) VALUES ('admin', '1234');
CREATE TABLE transacciones (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(10) NOT NULL, -- 'Ingreso' o 'Egreso'
    monto DOUBLE NOT NULL,
    categoria VARCHAR(50),
    fecha DATE NOT NULL,
    nombre VARCHAR(100),
    nota TEXT,
    usuario_id INT,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);
CREATE TABLE metas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    monto_objetivo DOUBLE NOT NULL,
    usuario_id INT,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);