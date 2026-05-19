package com.ucb.Rapidex.controller.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record AgendamentoRequestDto(
        UUID pedidoId,
        LocalDate data,
        LocalTime horaInicio) {
}
