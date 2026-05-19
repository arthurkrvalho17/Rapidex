package com.ucb.Rapidex.controller.dto;

public record PedidoViewDto(
        String id,
        String client,
        String service,
        String date,
        String time,
        String status) {
}
