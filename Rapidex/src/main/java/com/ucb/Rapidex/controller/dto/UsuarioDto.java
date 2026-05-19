package com.ucb.Rapidex.controller.dto;

import com.ucb.Rapidex.model.TipoUsuario;

public record UsuarioDto(
        String email,
        String senha,
        TipoUsuario tipoUsuario) {
}
