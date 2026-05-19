package com.ucb.Rapidex.controller.dto;

import java.util.UUID;

public record PrestadorRequestDto(
        UUID usuarioId,
        String nome,
        String telefone,
        String fotoUrl) {
}
