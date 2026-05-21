package com.ucb.Rapidex.model;

public enum StatusChamado {
    ABERTO("Aguardando", "s-aguardando"),
    EM_ATENDIMENTO("Em Andamento", "s-andamento"),
    AGUARDANDO_USUARIO("Em Revisao", "s-revisao"),
    RESOLVIDO("Resolvido", "s-resolvido"),
    FECHADO("Fechado", "s-resolvido");

    public final String label;
    public final String cssClass;

    StatusChamado(String label, String cssClass) {
        this.label = label;
        this.cssClass = cssClass;
    }
}