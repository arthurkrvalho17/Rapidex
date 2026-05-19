package com.ucb.Rapidex.controller.dto;

import com.ucb.Rapidex.model.StatusAgendamento;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.UUID;

public record AgendamentoResponseDto(
        UUID id,
        UUID pedidoId,
        LocalDate data,
        LocalTime horaInicio,
        boolean confirmadoPrestador,
        StatusAgendamento status,
        OffsetDateTime criadoEm) {
}
