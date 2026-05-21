CREATE TYPE area_atuacao AS ENUM (
  'Elétrica',
  'Limpeza',
  'Aulas',
  'Design',
  'Hidráulica',
  'Pet',
  'TI',
  'Beleza'
);

CREATE TYPE uf_enum AS ENUM (
  'AC','AL','AP','AM','BA','CE','DF','ES','GO',
  'MA','MT','MS','MG','PA','PB','PR','PE','PI',
  'RJ','RN','RS','RO','RR','SC','SP','SE','TO'
);

Alter Table prestador
    ADD COLUMN nome_social VARCHAR(100),
    ADD COLUMN cidade VARCHAR(50) NOT NULL,
    ADD COLUMN uf uf_enum NOT NULL;

CREATE TABLE prestador_area_atuacao (
  id          SERIAL PRIMARY KEY,
  prestador_id UUID NOT NULL REFERENCES prestador(id) ON DELETE CASCADE,
  area        area_atuacao NOT NULL
);
