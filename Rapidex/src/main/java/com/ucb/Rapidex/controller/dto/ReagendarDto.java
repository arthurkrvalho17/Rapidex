package com.ucb.Rapidex.controller.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReagendarDto(
        LocalDate novaData,
        LocalTime novaHoraInicio) {
}
