-- ENUMs
CREATE TYPE tipo_evento AS ENUM (
    'FRAUDE',
    'PAGAMENTO',
    'CANCELAMENTO',
    'SERVICO_NAO_REALIZADO',
    'REAGENDAMENTO',
    'RECLAMACAO',
    'DUVIDA',
    'SUGESTAO'
);

CREATE TYPE prioridade_chamado AS ENUM (
    'URGENTE',
    'ALTA',
    'MEDIA',
    'BAIXA'
);

CREATE TYPE status_chamado AS ENUM (
    'ABERTO',
    'EM_ATENDIMENTO',
    'AGUARDANDO_USUARIO',
    'RESOLVIDO',
    'FECHADO'
);

-- Tabela de agentes de suporte
CREATE TABLE suporte_usuario (
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome       VARCHAR(150) NOT NULL,
    email      VARCHAR(255) NOT NULL UNIQUE,
    senha_hash VARCHAR(255) NOT NULL,
    ativo      BOOLEAN      NOT NULL DEFAULT TRUE,
    criado_em  TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);

-- Tabela de chamados
CREATE TABLE chamado (
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    usuario_id    UUID               NOT NULL REFERENCES usuario(id),
    titulo        VARCHAR(200)       NOT NULL,
    descricao     TEXT               NOT NULL,
    tipo_evento   tipo_evento        NOT NULL,
    prioridade    prioridade_chamado NOT NULL,
    status        status_chamado     NOT NULL DEFAULT 'ABERTO',
    atribuido_a   UUID               REFERENCES suporte_usuario(id),
    criado_em     TIMESTAMPTZ        NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMPTZ        NOT NULL DEFAULT NOW(),
    resolvido_em  TIMESTAMPTZ
);

CREATE INDEX idx_chamado_usuario_id  ON chamado(usuario_id);
CREATE INDEX idx_chamado_status      ON chamado(status);
CREATE INDEX idx_chamado_prioridade  ON chamado(prioridade);
CREATE INDEX idx_chamado_atribuido_a ON chamado(atribuido_a);
CREATE INDEX idx_chamado_tipo_evento ON chamado(tipo_evento);

-- Trigger: define prioridade automaticamente com base no tipo_evento
CREATE OR REPLACE FUNCTION definir_prioridade_chamado()
RETURNS TRIGGER AS $$
BEGIN
    NEW.prioridade := CASE NEW.tipo_evento
        WHEN 'FRAUDE'                THEN 'URGENTE'::prioridade_chamado
        WHEN 'PAGAMENTO'             THEN 'ALTA'::prioridade_chamado
        WHEN 'CANCELAMENTO'          THEN 'ALTA'::prioridade_chamado
        WHEN 'SERVICO_NAO_REALIZADO' THEN 'ALTA'::prioridade_chamado
        WHEN 'REAGENDAMENTO'         THEN 'MEDIA'::prioridade_chamado
        WHEN 'RECLAMACAO'            THEN 'MEDIA'::prioridade_chamado
        WHEN 'DUVIDA'                THEN 'BAIXA'::prioridade_chamado
        WHEN 'SUGESTAO'              THEN 'BAIXA'::prioridade_chamado
    END;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_definir_prioridade_chamado
    BEFORE INSERT ON chamado
    FOR EACH ROW EXECUTE FUNCTION definir_prioridade_chamado();

-- Trigger: atualiza atualizado_em automaticamente (reutiliza funcao do V1)
CREATE TRIGGER trg_chamado_atualizado_em
    BEFORE UPDATE ON chamado
    FOR EACH ROW EXECUTE FUNCTION set_atualizado_em();
