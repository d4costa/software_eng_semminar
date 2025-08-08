INSERT INTO "usuario" (nombre, apellido, email, student_id, password, user_id)
VALUES 
('Juan', 'Pérez', 'juan.perez@example.com', 202310001, '$2a$12$p9nXrYSbZAgpCJOW5725ceGS2x1JGQ3cxa59fNC9IY5qTWccTucHq', 1),
('María', 'Gómez', 'maria.gomez@example.com', 202310002, '$2a$12$Cx/beWLtxTiMCqUlcdp/lOX8ZEnqkaYgoHJgYNSX/xZ2MJKvVpZpO', 2),
('Carlos', 'Rodríguez', 'carlos.rod@example.com', 202310003, '$2a$12$mjziI9IfaLnNW1ULitvDJ.QUr77yAny5/spWDRA1q89Lia/sTRala', 3);

-- Insertar parqueadero de Ingeniería
INSERT INTO parking (parking_id, parking_name, parking_location,available_capacity, max_capacity)
VALUES (1, 'Ingeniería', 'Edificio Principal - Piso 1',200,200);

-- Insertar 3 bicicletas (una por usuario)
INSERT INTO "bicycle" (bike_id, color, description, brand, chasis_code, user_id)
VALUES 
(101, 'Rojo', 'Bicicleta de montaña', 'Trek', 'CH-ROJO-101', 1),
(102, 'Azul', 'Bicicleta de carreras', 'Specialized', 'CH-AZUL-102', 2),
(103, 'Verde', 'Bicicleta urbana', 'Giant', 'CH-VERDE-103', 3);