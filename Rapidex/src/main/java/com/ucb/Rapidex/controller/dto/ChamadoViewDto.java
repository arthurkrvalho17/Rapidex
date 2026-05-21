package com.ucb.Rapidex.controller.dto;

import com.ucb.Rapidex.model.PrioridadeChamado;
import com.ucb.Rapidex.model.StatusChamado;
import com.ucb.Rapidex.model.TipoEvento;
import java.time.OffsetDateTime;
import java.util.UUID;

public record ChamadoViewDto(
        UUID id,
        String idDisplay,
        String titulo,
        String descricaoResumida,
        TipoEvento tipoEvento,
        PrioridadeChamado prioridade,
        StatusChamado status,
        String nomeUsuario,
        String iniciaisUsuario,
        String nomeAgente,
        OffsetDateTime criadoEm
) {}