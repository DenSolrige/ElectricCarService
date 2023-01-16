ALTER TABLE charging_station ADD CONSTRAINT uc_a085010f8def51cdf5612a321 UNIQUE (address, number);

ALTER TABLE charging_station ADD CONSTRAINT uc_charging_station_identifier UNIQUE (identifier);