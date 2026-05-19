package com.ucb.Rapidex.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "servico_oferecido")
public class ServicoOferecido {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "prestador_id", nullable = false)
    private UUID prestadorId;

    @Column(nullable = false, length = 150)
    private String nome;

    private String descricao;

    @Column(name = "preco_base", precision = 10, scale = 2)
    private BigDecimal precoBase;

    @Column(name = "unidade_preco", length = 50)
    private String unidadePreco;

    @Column(name = "criado_em", nullable = false)
    private OffsetDateTime criadoEm;

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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPrecoBase() {
        return precoBase;
    }

    public void setPrecoBase(BigDecimal precoBase) {
        this.precoBase = precoBase;
    }

    public String getUnidadePreco() {
        return unidadePreco;
    }

    public void setUnidadePreco(String unidadePreco) {
        this.unidadePreco = unidadePreco;
    }

    public OffsetDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(OffsetDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }
}
