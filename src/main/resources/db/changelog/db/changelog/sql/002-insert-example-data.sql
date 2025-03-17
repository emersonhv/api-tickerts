-- Datos de usuario
INSERT INTO users (name, email, user_type) VALUES
('Juan Pérez', 'juan.perez@example.com', 'OWNER'),
('Ana Gómez', 'ana.gomez@example.com', 'ASSIGNEE'),
('Carlos Ruiz', 'carlos.ruiz@example.com', 'ASSIGNEE');

-- Insertar datos de tickets
INSERT INTO tickets (subject, description, creation_date, update_date, status, priority, owner_id, assignee_id) VALUES
('Problema con el servidor', 'El servidor no responde desde hace 2 horas.', '2023-10-01', '2023-10-01', 1, 3, 1, 2),
('Error en la aplicación', 'La aplicación no carga correctamente.', '2023-10-02', '2023-10-02', 1, 2, 1, 3),
('Solicitud de nueva funcionalidad', 'Necesitamos agregar un nuevo módulo.', '2023-10-03', '2023-10-03', 0, 1, 2, NULL);