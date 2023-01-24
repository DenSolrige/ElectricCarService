ALTER TABLE electric_rate DROP COLUMN timestamp;

ALTER TABLE electric_rate ADD timestamp TIMESTAMP with time zone;