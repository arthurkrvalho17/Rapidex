package com.ucb.Rapidex.controller.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ServicoOferecidoRequestDto(
        UUID prestadorId,
        String nome,
        String descricao,
        BigDecimal precoBase,
        String unidadePreco) {
}
