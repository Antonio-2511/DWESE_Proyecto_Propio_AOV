
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


-- ============================
-- TABLA USERS
-- ============================
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(500) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    account_non_locked BOOLEAN NOT NULL DEFAULT TRUE
);

-- ============================
-- TABLA ROLES
-- ============================
CREATE TABLE IF NOT EXISTS roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    display_name VARCHAR(100)
);

-- ============================
-- TABLA USER_ROLES (N:M)
-- ============================
CREATE TABLE IF NOT EXISTS user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_roles_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_user_roles_role
        FOREIGN KEY (role_id)
        REFERENCES roles(id)
        ON DELETE CASCADE
);
