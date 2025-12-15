
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
