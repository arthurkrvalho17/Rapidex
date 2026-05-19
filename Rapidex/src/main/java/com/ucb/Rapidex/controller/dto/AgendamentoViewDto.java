package com.ucb.Rapidex.controller.dto;

public record AgendamentoViewDto(
        String id,
        String pedidoId,
        String client,
        String service,
        String data,
        String dataFormatada,
        String horaInicio,
        String status) {
}
