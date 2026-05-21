package com.ucb.Rapidex.model;

import com.ucb.Rapidex.model.converter.PrioridadeChamadoConverter;
import com.ucb.Rapidex.model.converter.StatusChamadoConverter;
import com.ucb.Rapidex.model.converter.TipoEventoConverter;
import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "chamado")
public class Chamado {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "usuario_id", nullable = false)
    private UUID usuarioId;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "tipo_evento", nullable = false, columnDefinition = "tipo_evento")
    @Convert(converter = TipoEventoConverter.class)
    private TipoEvento tipoEvento;

    @Column(nullable = false, columnDefinition = "prioridade_chamado")
    @Convert(converter = PrioridadeChamadoConverter.class)
    private PrioridadeChamado prioridade;

    @Column(nullable = false, columnDefinition = "status_chamado")
    @Convert(converter = StatusChamadoConverter.class)
    private StatusChamado status;

    @Column(name = "atribuido_a")
    private UUID atribuidoA;

    @Column(name = "criado_em", nullable = false)
    private OffsetDateTime criadoEm;

    @Column(name = "atualizado_em", nullable = false)
    private OffsetDateTime atualizadoEm;

    @Column(name = "resolvido_em")
    private OffsetDateTime resolvidoEm;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getUsuarioId() { return usuarioId; }
    public void setUsuarioId(UUID usuarioId) { this.usuarioId = usuarioId; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public TipoEvento getTipoEvento() { return tipoEvento; }
    public void setTipoEvento(TipoEvento tipoEvento) { this.tipoEvento = tipoEvento; }
    public PrioridadeChamado getPrioridade() { return prioridade; }
    public void setPrioridade(PrioridadeChamado prioridade) { this.prioridade = prioridade; }
    public StatusChamado getStatus() { return status; }
    public void setStatus(StatusChamado status) { this.status = status; }
    public UUID getAtribuidoA() { return atribuidoA; }
    public void setAtribuidoA(UUID atribuidoA) { this.atribuidoA = atribuidoA; }
    public OffsetDateTime getCriadoEm() { return criadoEm; }
    public void setCriadoEm(OffsetDateTime criadoEm) { this.criadoEm = criadoEm; }
    public OffsetDateTime getAtualizadoEm() { return atualizadoEm; }
    public void setAtualizadoEm(OffsetDateTime atualizadoEm) { this.atualizadoEm = atualizadoEm; }
    public OffsetDateTime getResolvidoEm() { return resolvidoEm; }
    public void setResolvidoEm(OffsetDateTime resolvidoEm) { this.resolvidoEm = resolvidoEm; }
}