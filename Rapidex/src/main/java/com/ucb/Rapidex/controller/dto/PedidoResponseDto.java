package com.ucb.Rapidex.controller.dto;

import com.ucb.Rapidex.model.StatusPedido;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.UUID;

public record PedidoResponseDto(
        UUID id,
        UUID prestadorId,
        UUID contratanteId,
        UUID servicoId,
        StatusPedido status,
        LocalDate dataSugerida,
        LocalTime horaSugerida,
        String descricao,
        OffsetDateTime criadoEm,
        OffsetDateTime atualizadoEm) {
}
