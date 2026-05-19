package com.ucb.Rapidex.controller.dto;

import java.util.UUID;

public record ContratanteRequestDto(
        UUID usuarioId,
        String nome,
        String telefone,
        String fotoUrl) {
}
