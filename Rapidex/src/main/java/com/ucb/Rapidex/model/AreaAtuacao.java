package com.ucb.Rapidex.model;

public enum AreaAtuacao {

    ELETRICA("Elétrica"),
    LIMPEZA("Limpeza"),
    AULAS("Aulas"),
    DESIGN("Design"),
    HIDRAULICA("Hidráulica"),
    PET("Pet"),
    TI("TI"),
    BELEZA("Beleza");

    private final String dbValue;

    AreaAtuacao(String dbValue) {
        this.dbValue = dbValue;
    }

    public String getDbValue() {
        return dbValue;
    }

    public static AreaAtuacao fromDbValue(String value) {
        for (AreaAtuacao a : values()) {
            if (a.dbValue.equals(value)) return a;
        }
        throw new IllegalArgumentException("AreaAtuacao desconhecida: " + value);
    }
}
