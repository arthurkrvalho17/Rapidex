package com.ucb.Rapidex.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "prestador")
public class Prestador {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "usuario_id", nullable = false, unique = true)
    private UUID usuarioId;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(length = 20)
    private String telefone;

    @Column(name = "foto_url")
    private String fotoUrl;

    @Column(nullable = false)
    private StatusPrestador status;

    @Column(name = "avaliacao_media", nullable = false, precision = 3, scale = 2)
    private BigDecimal avaliacaoMedia;

    @Column(name = "total_avaliacoes", nullable = false)
    private Integer totalAvaliacoes;

    @Column(nullable = false, length = 50)
    private String cidade;

    @Column(name = "uf", nullable = false, columnDefinition = "uf_enum")
    private String uf;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "prestador_area_atuacao",
            joinColumns = @JoinColumn(name = "prestador_id")
    )
    @Column(name = "area", columnDefinition = "area_atuacao")
    @Convert(converter = com.ucb.Rapidex.model.converter.AreaAtuacaoConverter.class)
    private Set<AreaAtuacao> areasAtuacao = new HashSet<>();

    @Column(name = "criado_em", nullable = false)
    private OffsetDateTime criadoEm;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(UUID usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public StatusPrestador getStatus() {
        return status;
    }

    public void setStatus(StatusPrestador status) {
        this.status = status;
    }

    public BigDecimal getAvaliacaoMedia() {
        return avaliacaoMedia;
    }

    public void setAvaliacaoMedia(BigDecimal avaliacaoMedia) {
        this.avaliacaoMedia = avaliacaoMedia;
    }

    public Integer getTotalAvaliacoes() {
        return totalAvaliacoes;
    }

    public void setTotalAvaliacoes(Integer totalAvaliacoes) {
        this.totalAvaliacoes = totalAvaliacoes;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Set<AreaAtuacao> getAreasAtuacao() {
        return areasAtuacao;
    }

    public void setAreasAtuacao(Set<AreaAtuacao> areasAtuacao) {
        this.areasAtuacao = areasAtuacao;
    }

    public OffsetDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(OffsetDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }
}
