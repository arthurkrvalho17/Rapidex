# Rapidex

Plataforma de marketplace de serviços que conecta **contratantes** a **prestadores**. O sistema conta com cadastro e autenticação de usuários, painel unificado por tipo de conta, gestão de pedidos, agendamentos, pagamentos, avaliações e um módulo completo de suporte com kanban de chamados.

---

## Tecnologias

| Camada | Stack |
|--------|-------|
| Backend | Java 21 · Spring Boot 4.0.6 · Spring Security 6 · Spring Data JPA |
| Banco de dados | PostgreSQL 16 · Flyway (migrações) · Hibernate 7 |
| Frontend | Thymeleaf · HTML/CSS/JS |
| Infraestrutura | Docker · Docker Compose |
| Build | Maven Wrapper (`mvnw`) |

---

## Pré-requisitos

- [Java 21](https://adoptium.net/) (JDK)
- [Docker](https://www.docker.com/products/docker-desktop) + Docker Compose
- Git

> O Maven **não** precisa estar instalado globalmente — o `mvnw` (Maven Wrapper) já está incluído no repositório.

---

## Como rodar localmente

### 1. Clone o repositório

```bash
git clone <url-do-repositorio>
cd Rapidex
```

### 2. Suba o banco de dados

```bash
docker compose up -d
```

Isso inicia:
- **PostgreSQL 16** em `localhost:5433`
- **pgAdmin 4** em `http://localhost:15432` (login: `admin@rapidex.com` / `admin`)

Aguarde o healthcheck do Postgres ficar `healthy` antes de prosseguir (normalmente 5–10 segundos).

```bash
# Opcional: acompanhe o status
docker compose ps
```

### 3. Inicie a aplicação

**Linux / macOS:**
```bash
./mvnw spring-boot:run
```

**Windows (PowerShell):**
```powershell
.\mvnw spring-boot:run
```

Na primeira execução o Flyway aplica automaticamente as 3 migrações de banco (V1, V2, V3). Aguarde a mensagem:

```
Started RapidexApplication in X.XXX seconds
```

A aplicação estará disponível em `http://localhost:8080`.

### 4. Acesse o sistema

| Portal | URL | Como acessar |
|--------|-----|--------------|
| Login usuário | `http://localhost:8080/login` | Cadastre primeiro via `/cadastro` |
| Login suporte | `http://localhost:8080/suporte/login` | Veja seção [Dados de Teste](#dados-de-teste) |
| pgAdmin | `http://localhost:15432` | `admin@rapidex.com` / `admin` |

---

## Configuração

Todas as propriedades estão em `src/main/resources/application.yaml`. Os valores padrão já funcionam com o `docker-compose.yml` incluído:

| Propriedade | Valor padrão |
|-------------|-------------|
| `spring.datasource.url` | `jdbc:postgresql://localhost:5433/rapidexdb?stringtype=unspecified` |
| `spring.datasource.username` | `rapidex` |
| `spring.datasource.password` | `rapidex` |

Para sobrescrever sem editar o arquivo:

```bash
./mvnw spring-boot:run \
  -Dspring-boot.run.arguments="--spring.datasource.password=outrasenha"
```

---

## Dados de Teste

### Inserir usuário de suporte

O agente de suporte precisa ser inserido manualmente no banco. Execute no **pgAdmin** (`http://localhost:15432`) ou via `docker exec`:

```bash
docker exec -i rapidex-postgres psql -U rapidex -d rapidexdb -c "
INSERT INTO suporte_usuario (id, nome, email, senha_hash, ativo, criado_em)
VALUES (
  gen_random_uuid(),
  'Admin Suporte',
  'admin@rapidex.com',
  crypt('suporte123', gen_salt('bf', 10)),
  true,
  NOW()
);"
```

Credenciais resultantes: `admin@rapidex.com` / `suporte123`

> A extensão `pgcrypto` (usada pela função `crypt`) é habilitada automaticamente pela migração V1.

### Cadastrar usuário comum (via API)

```bash
curl -X POST http://localhost:8080/usuarios \
  -H "Content-Type: application/json" \
  -d '{
    "email": "usuario@email.com",
    "senha": "senha123",
    "tipoUsuario": "CONTRATANTE",
    "nome": "Nome Teste"
  }'
```

Tipos válidos: `PRESTADOR`, `CONTRATANTE`, `AMBOS`.

---

## O que foi implementado

### Autenticação e segurança
- Cadastro de usuários com tipos `PRESTADOR`, `CONTRATANTE` ou `AMBOS`
- Login via formulário com sessão (Spring Security form login)
- HTTP Basic Auth para chamadas REST diretas
- Duas `SecurityFilterChain` independentes: uma para usuários do marketplace (`@Order(2)`) e outra para agentes de suporte (`@Order(1)`, rota `/suporte/**`)
- Respostas 401 JSON para clientes de API e redirecionamento para login para navegadores (detecção pelo header `Accept`)
- BCrypt custo 10 para hash de senhas

### Painel do usuário (`/conta`)
- Renderizado com Thymeleaf após login
- Comportamento condicional por tipo de conta:
  - `PRESTADOR` → somente módulo prestador visível, switcher oculto
  - `CONTRATANTE` → somente módulo contratante visível, switcher oculto
  - `AMBOS` → switcher habilitado, alterna entre os dois painéis

### Cadastro de prestadores
- Campos: nome, telefone, foto, cidade, UF, áreas de atuação
- Áreas disponíveis: `ELETRICA`, `LIMPEZA`, `AULAS`, `DESIGN`, `HIDRAULICA`, `PET`, `TI`, `BELEZA`
- Status: `ONLINE`, `OFFLINE`, `OCUPADO`
- Validação com Bean Validation (`@Valid`): campos obrigatórios por tipo de conta
- Avaliação média recalculada automaticamente por trigger no PostgreSQL

### Pedidos e agendamentos
- Contratante solicita serviço a um prestador com data e horário sugeridos
- Prestador aceita, recusa ou propõe reagendamento
- Agendamento criado automaticamente ao aceitar

### Pagamentos
- Registro de pagamento vinculado ao pedido
- Status: `PENDENTE`, `PAGO`, `ESTORNADO`

### Avaliações
- Nota de 1 a 5 após conclusão do serviço
- Média e total de avaliações do prestador atualizados automaticamente por trigger

### Módulo de suporte (`/suporte/**`)
- **Login isolado** para agentes de suporte via `suporte_usuario`, independente dos usuários do marketplace
- **Kanban de chamados** com 4 colunas: Aguardando · Em Andamento · Em Revisão · Resolvido
- Dados carregados do banco em tempo real via Thymeleaf
- **Drag-and-drop** entre colunas com persistência imediata (`PATCH /suporte/chamados/{id}/status`)
- **Painel lateral** de detalhes carregado via `fetch` ao clicar no chamado
- **Prioridade automática** por trigger no banco conforme tipo de evento:

  | Tipo de evento | Prioridade |
  |----------------|-----------|
  | FRAUDE | Urgente |
  | PAGAMENTO · CANCELAMENTO · SERVICO_NAO_REALIZADO | Alta |
  | REAGENDAMENTO · RECLAMACAO | Média |
  | DUVIDA · SUGESTAO | Baixa |

- **Stats em tempo real**: total, aguardando, resolvidos hoje, alta prioridade
- Usuários comuns abrem chamados via `POST /chamados` e consultam os seus em `GET /chamados/meus`

---

## Endpoints da API

> Todos os endpoints REST exigem autenticação HTTP Basic, exceto `POST /usuarios/**` e as rotas de login.

### Usuários
| Método | Rota | Descrição |
|--------|------|-----------|
| `POST` | `/usuarios` | Cadastra novo usuário |

### Prestadores
| Método | Rota | Descrição |
|--------|------|-----------|
| `GET` | `/prestadores` | Lista todos os prestadores |
| `GET` | `/prestadores/{id}` | Busca prestador por ID |

### Pedidos
| Método | Rota | Descrição |
|--------|------|-----------|
| `POST` | `/pedidos` | Cria novo pedido |
| `GET` | `/pedidos/{id}` | Busca pedido por ID |
| `PATCH` | `/pedidos/{id}/aceitar` | Prestador aceita |
| `PATCH` | `/pedidos/{id}/recusar` | Prestador recusa |
| `PATCH` | `/pedidos/{id}/reagendar` | Propõe reagendamento |

### Agendamentos
| Método | Rota | Descrição |
|--------|------|-----------|
| `GET` | `/agendamentos/{id}` | Busca agendamento |

### Pagamentos
| Método | Rota | Descrição |
|--------|------|-----------|
| `POST` | `/pagamentos` | Registra pagamento |
| `GET` | `/pagamentos/{id}` | Busca pagamento |

### Avaliações
| Método | Rota | Descrição |
|--------|------|-----------|
| `POST` | `/avaliacoes` | Cria avaliação |
| `GET` | `/avaliacoes/{id}` | Busca avaliação |

### Chamados — usuário
| Método | Rota | Descrição |
|--------|------|-----------|
| `POST` | `/chamados` | Abre novo chamado |
| `GET` | `/chamados/meus` | Lista chamados do usuário autenticado |

### Chamados — suporte (requer sessão de agente)
| Método | Rota | Descrição |
|--------|------|-----------|
| `GET` | `/suporte/chamados/{id}` | Detalhes de um chamado |
| `PATCH` | `/suporte/chamados/{id}/status` | Atualiza status |

---

## Estrutura do banco

```
usuario
 ├── prestador          (1:1)
 │    └── servico_oferecido  (1:N)
 │    └── pedido             (N prestador)
 └── contratante        (1:1)
      └── pedido             (N contratante)

pedido
 ├── agendamento        (1:1)
 ├── pagamento          (1:1)
 └── avaliacao          (1:1)

suporte_usuario
 └── chamado            (1:N via atribuido_a)

usuario → chamado       (1:N via usuario_id)
```

### Migrações Flyway

| Versão | Arquivo | Conteúdo |
|--------|---------|----------|
| V1 | `V1__Create_initial_tables.sql` | Schema completo do marketplace: ENUMs, tabelas, triggers de avaliação e `atualizado_em` |
| V2 | `V2__Alter_Table_Prestadores.sql` | Adiciona `cidade`, `uf` (enum) e tabela `prestador_area_atuacao` |
| V3 | `V3__Create_Support_Module.sql` | Módulo de suporte: `suporte_usuario`, `chamado`, trigger de prioridade automática |

---

## Parar o ambiente

```bash
# Para os containers mantendo os dados
docker compose stop

# Para e remove os containers (dados persistem no volume)
docker compose down

# Para, remove containers E apaga todos os dados do banco
docker compose down -v
```
