package com.ucb.Rapidex.controller.dto;

import com.ucb.Rapidex.model.StatusPagamento;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record PagamentoResponseDto(
        UUID id,
        UUID pedidoId,
        BigDecimal valor,
        StatusPagamento status,
        OffsetDateTime pagoEm,
        OffsetDateTime criadoEm) {
}
