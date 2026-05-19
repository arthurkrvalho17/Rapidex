-- =====================================================
--  Rapidex — DDL v1
--  Banco: PostgreSQL
--  Gerado para: painel do prestador (v1)
-- =====================================================

-- Extensão para UUID
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- =====================================================
--  ENUMS
-- =====================================================

CREATE TYPE tipo_usuario AS ENUM (
  'prestador',
  'contratante',
  'ambos'
);

CREATE TYPE status_prestador AS ENUM (
  'online',
  'offline',
  'ocupado'
);

CREATE TYPE status_pedido AS ENUM (
  'pending',
  'accepted',
  'refused',
  'completed',
  'cancelled'
);

CREATE TYPE status_agendamento AS ENUM (
  'agendado',
  'confirmado',
  'reagendado',
  'cancelado',
  'concluido'
);

CREATE TYPE status_pagamento AS ENUM (
  'pendente',
  'pago',
  'estornado'
);


-- =====================================================
--  USUARIO
--  Centro da autenticação (Basic Auth).
--  Um usuário pode ter perfil de prestador, contratante
--  ou ambos simultaneamente.
-- =====================================================

CREATE TABLE usuario (
  id            UUID          PRIMARY KEY DEFAULT gen_random_uuid(),
  email         VARCHAR(255)  NOT NULL UNIQUE,
  senha_hash    VARCHAR(255)  NOT NULL,
  tipo          tipo_usuario  NOT NULL DEFAULT 'contratante',
  ativo         BOOLEAN       NOT NULL DEFAULT TRUE,
  criado_em     TIMESTAMPTZ   NOT NULL DEFAULT NOW(),
  atualizado_em TIMESTAMPTZ   NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_usuario_email ON usuario (email);


-- =====================================================
--  PRESTADOR
--  Perfil específico do prestador de serviços.
-- =====================================================

CREATE TABLE prestador (
  id               UUID             PRIMARY KEY DEFAULT gen_random_uuid(),
  usuario_id       UUID             NOT NULL UNIQUE REFERENCES usuario (id) ON DELETE CASCADE,
  nome             VARCHAR(150)     NOT NULL,
  telefone         VARCHAR(20),
  foto_url         TEXT,
  status           status_prestador NOT NULL DEFAULT 'offline',
  avaliacao_media  NUMERIC(3, 2)    NOT NULL DEFAULT 0.00,
  total_avaliacoes INTEGER          NOT NULL DEFAULT 0,
  criado_em        TIMESTAMPTZ      NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_prestador_usuario_id ON prestador (usuario_id);
CREATE INDEX idx_prestador_status     ON prestador (status);


-- =====================================================
--  CONTRATANTE
--  Perfil específico do contratante.
-- =====================================================

CREATE TABLE contratante (
  id         UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
  usuario_id UUID        NOT NULL UNIQUE REFERENCES usuario (id) ON DELETE CASCADE,
  nome       VARCHAR(150) NOT NULL,
  telefone   VARCHAR(20),
  foto_url   TEXT,
  criado_em  TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_contratante_usuario_id ON contratante (usuario_id);


-- =====================================================
--  SERVICO_OFERECIDO
--  Serviços cadastrados pelo prestador.
-- =====================================================

CREATE TABLE servico_oferecido (
  id            UUID          PRIMARY KEY DEFAULT gen_random_uuid(),
  prestador_id  UUID          NOT NULL REFERENCES prestador (id) ON DELETE CASCADE,
  nome          VARCHAR(150)  NOT NULL,
  descricao     TEXT,
  preco_base    NUMERIC(10,2),
  unidade_preco VARCHAR(50),  -- ex: "por hora", "por visita", "por m²"
  criado_em     TIMESTAMPTZ   NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_servico_prestador_id ON servico_oferecido (prestador_id);


-- =====================================================
--  PEDIDO
--  Solicitação de serviço feita pelo contratante
--  ao prestador.
-- =====================================================

CREATE TABLE pedido (
  id             UUID          PRIMARY KEY DEFAULT gen_random_uuid(),
  prestador_id   UUID          NOT NULL REFERENCES prestador (id),
  contratante_id UUID          NOT NULL REFERENCES contratante (id),
  servico_id     UUID          REFERENCES servico_oferecido (id) ON DELETE SET NULL,
  status         status_pedido NOT NULL DEFAULT 'pending',
  data_sugerida  DATE,
  hora_sugerida  TIME,
  descricao      TEXT,
  criado_em      TIMESTAMPTZ   NOT NULL DEFAULT NOW(),
  atualizado_em  TIMESTAMPTZ   NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_pedido_prestador_id   ON pedido (prestador_id);
CREATE INDEX idx_pedido_contratante_id ON pedido (contratante_id);
CREATE INDEX idx_pedido_status         ON pedido (status);
CREATE INDEX idx_pedido_data_sugerida  ON pedido (data_sugerida);


-- =====================================================
--  AGENDAMENTO
--  Criado quando um pedido é aceito.
--  Alimenta o calendário e os próximos compromissos.
-- =====================================================

CREATE TABLE agendamento (
  id                   UUID               PRIMARY KEY DEFAULT gen_random_uuid(),
  pedido_id            UUID               NOT NULL UNIQUE REFERENCES pedido (id) ON DELETE CASCADE,
  data                 DATE               NOT NULL,
  hora_inicio          TIME               NOT NULL,
  confirmado_prestador BOOLEAN            NOT NULL DEFAULT FALSE,
  status               status_agendamento NOT NULL DEFAULT 'agendado',
  criado_em            TIMESTAMPTZ        NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_agendamento_pedido_id ON agendamento (pedido_id);
CREATE INDEX idx_agendamento_data      ON agendamento (data);


-- =====================================================
--  PAGAMENTO
--  Gerado após o serviço ser concluído.
--  Alimenta o histórico de ganhos e as métricas.
-- =====================================================

CREATE TABLE pagamento (
  id        UUID             PRIMARY KEY DEFAULT gen_random_uuid(),
  pedido_id UUID             NOT NULL UNIQUE REFERENCES pedido (id) ON DELETE CASCADE,
  valor     NUMERIC(10, 2)   NOT NULL,
  status    status_pagamento NOT NULL DEFAULT 'pendente',
  pago_em   TIMESTAMPTZ,
  criado_em TIMESTAMPTZ      NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_pagamento_pedido_id ON pagamento (pedido_id);
CREATE INDEX idx_pagamento_status    ON pagamento (status);
CREATE INDEX idx_pagamento_pago_em   ON pagamento (pago_em);


-- =====================================================
--  AVALIACAO
--  Nota e comentário após o serviço concluído.
--  O campo avaliacao_media em prestador é atualizado
--  via trigger abaixo.
-- =====================================================

CREATE TABLE avaliacao (
  id           UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
  pedido_id    UUID        NOT NULL UNIQUE REFERENCES pedido (id) ON DELETE CASCADE,
  avaliador_id UUID        NOT NULL REFERENCES usuario (id),
  nota         SMALLINT    NOT NULL CHECK (nota BETWEEN 1 AND 5),
  comentario   TEXT,
  criado_em    TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_avaliacao_pedido_id    ON avaliacao (pedido_id);
CREATE INDEX idx_avaliacao_avaliador_id ON avaliacao (avaliador_id);


-- =====================================================
--  TRIGGER — atualiza avaliacao_media do prestador
--  automaticamente após cada nova avaliação inserida.
-- =====================================================

CREATE OR REPLACE FUNCTION atualizar_avaliacao_prestador()
RETURNS TRIGGER AS $$
DECLARE
  v_prestador_id UUID;
BEGIN
  SELECT p.prestador_id
    INTO v_prestador_id
    FROM pedido p
   WHERE p.id = NEW.pedido_id;

  UPDATE prestador
     SET avaliacao_media  = (
           SELECT ROUND(AVG(a.nota)::NUMERIC, 2)
             FROM avaliacao a
             JOIN pedido p ON p.id = a.pedido_id
            WHERE p.prestador_id = v_prestador_id
         ),
         total_avaliacoes = (
           SELECT COUNT(*)
             FROM avaliacao a
             JOIN pedido p ON p.id = a.pedido_id
            WHERE p.prestador_id = v_prestador_id
         )
   WHERE id = v_prestador_id;

  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_avaliacao_inserida
AFTER INSERT ON avaliacao
FOR EACH ROW
EXECUTE FUNCTION atualizar_avaliacao_prestador();


-- =====================================================
--  TRIGGER — atualiza atualizado_em automaticamente
--  nas tabelas usuario e pedido.
-- =====================================================

CREATE OR REPLACE FUNCTION set_atualizado_em()
RETURNS TRIGGER AS $$
BEGIN
  NEW.atualizado_em = NOW();
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_usuario_atualizado_em
BEFORE UPDATE ON usuario
FOR EACH ROW
EXECUTE FUNCTION set_atualizado_em();

CREATE TRIGGER trg_pedido_atualizado_em
BEFORE UPDATE ON pedido
FOR EACH ROW
EXECUTE FUNCTION set_atualizado_em();