package com.ucb.Rapidex.controller.dto;

import com.ucb.Rapidex.model.AreaAtuacao;
import com.ucb.Rapidex.model.StatusPrestador;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

public record PrestadorResponseDto(
        UUID id,
        UUID usuarioId,
        String nome,
        String telefone,
        String fotoUrl,
        StatusPrestador status,
        BigDecimal avaliacaoMedia,
        Integer totalAvaliacoes,
        String cidade,
        String uf,
        Set<AreaAtuacao> areasAtuacao,
        OffsetDateTime criadoEm) {
}
