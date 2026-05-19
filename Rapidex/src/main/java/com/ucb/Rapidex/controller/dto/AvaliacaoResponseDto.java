package com.ucb.Rapidex.controller.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record AvaliacaoResponseDto(
        UUID id,
        UUID pedidoId,
        UUID avaliadorId,
        Short nota,
        String comentario,
        OffsetDateTime criadoEm) {
}
