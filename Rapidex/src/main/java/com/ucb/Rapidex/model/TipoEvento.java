package com.ucb.Rapidex.model;

public enum TipoEvento {
    FRAUDE("Fraude", "tag-fraude"),
    PAGAMENTO("Pagamento", "tag-pagamento"),
    CANCELAMENTO("Cancelamento", "tag-cancelamento"),
    SERVICO_NAO_REALIZADO("Servico nao realizado", "tag-profissional"),
    REAGENDAMENTO("Reagendamento", "tag-reagendamento"),
    RECLAMACAO("Reclamacao", "tag-profissional"),
    DUVIDA("Duvida", "tag-outros"),
    SUGESTAO("Sugestao", "tag-outros");

    public final String label;
    public final String cssClass;

    TipoEvento(String label, String cssClass) {
        this.label = label;
        this.cssClass = cssClass;
    }
}