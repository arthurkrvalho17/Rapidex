package com.ucb.Rapidex.controller.dto;

public record ChamadoStatsDto(
        long total,
        long aguardando,
        long resolvidosHoje,
        long altaPrioridade
) {}