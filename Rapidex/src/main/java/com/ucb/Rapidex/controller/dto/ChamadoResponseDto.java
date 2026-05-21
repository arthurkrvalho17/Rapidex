package com.ucb.Rapidex.controller.dto;

import com.ucb.Rapidex.model.PrioridadeChamado;
import com.ucb.Rapidex.model.StatusChamado;
import com.ucb.Rapidex.model.TipoEvento;
import java.time.OffsetDateTime;
import java.util.UUID;

public record ChamadoResponseDto(
        UUID id,
        UUID usuarioId,
        String titulo,
        String descricao,
        TipoEvento tipoEvento,
        PrioridadeChamado prioridade,
        StatusChamado status,
        UUID atribuidoA,
        OffsetDateTime criadoEm,
        OffsetDateTime atualizadoEm,
        OffsetDateTime resolvidoEm
) {}