package com.ucb.Rapidex.controller.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record PedidoRequestDto(
        UUID prestadorId,
        UUID contratanteId,
        UUID servicoId,
        LocalDate dataSugerida,
        LocalTime horaSugerida,
        String descricao) {
}
