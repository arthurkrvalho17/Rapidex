package com.ucb.Rapidex.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "prestador_id", nullable = false)
    private UUID prestadorId;

    @Column(name = "contratante_id", nullable = false)
    private UUID contratanteId;

    @Column(name = "servico_id")
    private UUID servicoId;

    @Column(nullable = false)
    private StatusPedido status;

    @Column(name = "data_sugerida")
    private LocalDate dataSugerida;

    @Column(name = "hora_sugerida")
    private LocalTime horaSugerida;

    private String descricao;

    @Column(name = "criado_em", nullable = false)
    private OffsetDateTime criadoEm;

    @Column(name = "atualizado_em", nullable = false)
    private OffsetDateTime atualizadoEm;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getPrestadorId() {
        return prestadorId;
    }

    public void setPrestadorId(UUID prestadorId) {
        this.prestadorId = prestadorId;
    }

    public UUID getContratanteId() {
        return contratanteId;
    }

    public void setContratanteId(UUID contratanteId) {
        this.contratanteId = contratanteId;
    }

    public UUID getServicoId() {
        return servicoId;
    }

    public void setServicoId(UUID servicoId) {
        this.servicoId = servicoId;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    public LocalDate getDataSugerida() {
        return dataSugerida;
    }

    public void setDataSugerida(LocalDate dataSugerida) {
        this.dataSugerida = dataSugerida;
    }

    public LocalTime getHoraSugerida() {
        return horaSugerida;
    }

    public void setHoraSugerida(LocalTime horaSugerida) {
        this.horaSugerida = horaSugerida;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public OffsetDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(OffsetDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }

    public OffsetDateTime getAtualizadoEm() {
        return atualizadoEm;
    }

    public void setAtualizadoEm(OffsetDateTime atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }
}
