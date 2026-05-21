package com.ucb.Rapidex.controller.dto;

import com.ucb.Rapidex.model.TipoEvento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ChamadoRequestDto(
        @NotBlank(message = "Titulo e obrigatorio") @Size(max = 200) String titulo,
        @NotBlank(message = "Descricao e obrigatoria") String descricao,
        @NotNull(message = "Tipo de evento e obrigatorio") TipoEvento tipoEvento
) {}