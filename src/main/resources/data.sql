-- Datos NivelCriticidad

INSERT INTO nivel_criticidad (nombre, descripcion) VALUES
('Baja', 'Nivel bajo'),
('Media', 'Nivel medio'),
('Alta', 'Nivel alto'),
('Crítica', 'Nivel crítico');


-- Datos Advertencia

INSERT INTO advertencia (titulo, descripcion, fecha_envio, es_emergencia, nivel_criticidad_id)
VALUES
('Intentos de acceso no autorizados', 'Se han detectado múltiples intentos fallidos de inicio de sesión desde direcciones IP externas.', NOW(), TRUE, 4),
('Posible ataque de fuerza bruta', 'Se ha identificado un patrón de ataque por fuerza bruta contra el panel de administración.', NOW(), FALSE, 2),
('Correo de phishing detectado', 'Varios usuarios han recibido correos electrónicos fraudulentos simulando ser del departamento de IT.', NOW(), FALSE, 3);


INSERT INTO roles (id, name, display_name) VALUES
(1, 'ROLE_USER', 'Usuario'),
(2, 'ROLE_MANAGER', 'Gestor'),
(3, 'ROLE_ADMIN', 'Administrador');


-- 🔐 USERS (con los nuevos campos añadidos)
INSERT INTO users (
    id,
    email,
    password_hash,
    active,
    account_non_locked,
    last_password_change,
    password_expires_at,
    must_change_password,
    failed_login_attempts
) VALUES
(1, 'user@app.local',    '$2a$12$eZg4hp941jhx0KUiFeE/N.IQjFl1S7xMtyRodqhU.a6OZ4kSYTQ1u', true, true, NOW(), NULL, false, 0),
(2, 'manager@app.local', '$2a$12$eZg4hp941jhx0KUiFeE/N.IQjFl1S7xMtyRodqhU.a6OZ4kSYTQ1u', true, true, NOW(), NULL, false, 0),
(3, 'admin@app.local',   '$2a$12$eZg4hp941jhx0KUiFeE/N.IQjFl1S7xMtyRodqhU.a6OZ4kSYTQ1u', true, true, NOW(), NULL, false, 0);


INSERT INTO user_roles (user_id, role_id) VALUES
(1, 1),
(2, 2),
(3, 3);
