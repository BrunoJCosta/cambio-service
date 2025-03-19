CREATE TABLE cambio (
  id bigserial PRIMARY KEY,
  from_currency varchar(3) NOT NULL,
  to_currency varchar(3) NOT NULL,
  conversion_factor decimal(65,2) NOT NULL
);
