package com.ucb.Rapidex.controller.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ContratanteResponseDto(
        UUID id,
        UUID usuarioId,
        String nome,
        String telefone,
        String fotoUrl,
        OffsetDateTime criadoEm) {
}
