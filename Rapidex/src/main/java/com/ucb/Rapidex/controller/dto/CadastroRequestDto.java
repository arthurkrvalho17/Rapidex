package com.ucb.Rapidex.controller.dto;

import com.ucb.Rapidex.model.TipoUsuario;

public record CadastroRequestDto(
        String email,
        String senha,
        TipoUsuario tipoUsuario,
        String nome,
        String telefone,
        String fotoUrl) {
}
