package com.ucb.Rapidex.model;


import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String email;

    private String senha_hash;

    @Column(name = "tipo")
    private TipoUsuario tipoUsuario;

    private boolean ativo;

    private OffsetDateTime criado_em;

    private OffsetDateTime atualizado_em;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha_hash() {
        return senha_hash;
    }

    public void setSenha_hash(String senha_hash) {
        this.senha_hash = senha_hash;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public OffsetDateTime getCriado_em() {
        return criado_em;
    }

    public void setCriado_em(OffsetDateTime criado_em) {
        this.criado_em = criado_em;
    }

    public OffsetDateTime getAtualizado_em() {
        return atualizado_em;
    }

    public void setAtualizado_em(OffsetDateTime atualizado_em) {
        this.atualizado_em = atualizado_em;
    }
}
