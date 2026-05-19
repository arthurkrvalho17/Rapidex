package com.ucb.Rapidex.controller.dto;

import java.util.UUID;

public record AvaliacaoRequestDto(
        UUID pedidoId,
        UUID avaliadorId,
        Short nota,
        String comentario) {
}
