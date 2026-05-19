package com.ucb.Rapidex.controller.dto;

import com.ucb.Rapidex.model.TipoUsuario;

import java.util.UUID;

public record CadastroResponseDto(
        UUID usuarioId,
        String email,
        TipoUsuario tipoUsuario,
        UUID prestadorId,
        UUID contratanteId) {
}
