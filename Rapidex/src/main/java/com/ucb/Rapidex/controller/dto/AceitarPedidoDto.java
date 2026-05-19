package com.ucb.Rapidex.controller.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record AceitarPedidoDto(
        LocalDate data,
        LocalTime horaInicio) {
}
