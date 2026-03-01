-- ============================
-- LIMPIEZA ORDENADA (por FK)
-- ============================

DROP TABLE IF EXISTS password_reset_tokens;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS advertencia;
DROP TABLE IF EXISTS nivel_criticidad;
DROP TABLE IF EXISTS users;

-- ============================
-- TABLA NIVEL_CRITICIDAD
-- ============================

CREATE TABLE nivel_criticidad (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    descripcion VARCHAR(255)
);

-- ============================
-- TABLA ADVERTENCIA
-- ============================

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
-- TABLA USERS (CORREGIDA)
-- ============================

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(500) NOT NULL,

    active BOOLEAN NOT NULL DEFAULT TRUE,
    account_non_locked BOOLEAN NOT NULL DEFAULT TRUE,

    -- 🔐 Columnas que tu data.sql está usando
    last_password_change DATETIME NULL,
    password_expires_at DATETIME NULL,
    must_change_password BOOLEAN NOT NULL DEFAULT FALSE,
    failed_login_attempts INT NOT NULL DEFAULT 0
);

-- ============================
-- TABLA ROLES
-- ============================

CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    display_name VARCHAR(100)
);

-- ============================
-- TABLA USER_ROLES (N:M)
-- ============================

CREATE TABLE user_roles (
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

-- ============================
-- TABLA PASSWORD_RESET_TOKENS
-- ============================

CREATE TABLE password_reset_tokens (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    token_hash VARCHAR(64) NOT NULL,
    expires_at DATETIME NOT NULL,
    used_at DATETIME NULL,
    created_at DATETIME NOT NULL,
    request_ip VARCHAR(45) NULL,
    user_agent VARCHAR(255) NULL,

    CONSTRAINT fk_prt_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,

    INDEX idx_prt_user_id (user_id),
    INDEX idx_prt_token_hash (token_hash),
    INDEX idx_prt_expires_at (expires_at)
);