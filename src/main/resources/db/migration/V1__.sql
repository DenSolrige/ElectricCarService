CREATE TABLE charging_station (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   identifier VARCHAR(255),
   address VARCHAR(255),
   number INTEGER NOT NULL,
   status INTEGER,
   CONSTRAINT pk_charging_station PRIMARY KEY (id)
);