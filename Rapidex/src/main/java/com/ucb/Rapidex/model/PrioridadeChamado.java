package com.ucb.Rapidex.model;

public enum PrioridadeChamado {
    URGENTE("Urgente", "prio-urgente"),
    ALTA("Alta", "prio-alta"),
    MEDIA("Media", "prio-media"),
    BAIXA("Baixa", "prio-baixa");

    public final String label;
    public final String cssClass;

    PrioridadeChamado(String label, String cssClass) {
        this.label = label;
        this.cssClass = cssClass;
    }
}