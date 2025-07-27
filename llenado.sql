INSERT INTO "usuario" (nombre, apellido, email, student_id, password, user_id)
VALUES 
('Juan', 'Pérez', 'juan.perez@example.com', 202310001, 'pass123', 1),
('María', 'Gómez', 'maria.gomez@example.com', 202310002, 'securepass', 2),
('Carlos', 'Rodríguez', 'carlos.rod@example.com', 202310003, 'mypassword', 3);

-- Insertar parqueadero de Ingeniería
INSERT INTO parking (parking_id, parking_name, parking_location)
VALUES (1, 'Ingeniería', 'Edificio Principal - Piso 1');

-- Insertar 3 bicicletas (una por usuario)
INSERT INTO "bicycle" (bike_id, color, description, brand, chasis_code, user_id)
VALUES 
(101, 'Rojo', 'Bicicleta de montaña', 'Trek', 'CH-ROJO-101', 1),
(102, 'Azul', 'Bicicleta de carreras', 'Specialized', 'CH-AZUL-102', 2),
(103, 'Verde', 'Bicicleta urbana', 'Giant', 'CH-VERDE-103', 3);