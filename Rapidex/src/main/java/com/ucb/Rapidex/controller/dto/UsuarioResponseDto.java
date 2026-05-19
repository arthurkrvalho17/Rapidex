package com.ucb.Rapidex.controller.dto;

import com.ucb.Rapidex.model.TipoUsuario;

import java.time.OffsetDateTime;
import java.util.UUID;

public record UsuarioResponseDto(
        UUID id,
        String email,
        TipoUsuario tipoUsuario,
        boolean ativo,
        OffsetDateTime criadoEm,
        OffsetDateTime atualizadoEm) {
}
