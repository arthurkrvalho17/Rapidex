package com.ucb.Rapidex.controller.dto;

import com.ucb.Rapidex.model.StatusChamado;
import jakarta.validation.constraints.NotNull;

public record StatusUpdateDto(@NotNull StatusChamado status) {}