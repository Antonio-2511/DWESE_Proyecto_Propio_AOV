
DROP TABLE IF EXISTS advertencia;
DROP TABLE IF EXISTS nivel_criticidad;

CREATE TABLE nivel_criticidad (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    descripcion VARCHAR(255)
);

CREATE TABLE advertencia (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    descripcion TEXT,
    fecha_envio DATETIME,
    es_emergencia BOOLEAN NOT NULL,
    nivel_criticidad_id BIGINT NOT NULL,
    CONSTRAINT fk_advertencia_nivel
        FOREIGN KEY (nivel_criticidad_id)
        REFERENCES nivel_criticidad(id)
);
