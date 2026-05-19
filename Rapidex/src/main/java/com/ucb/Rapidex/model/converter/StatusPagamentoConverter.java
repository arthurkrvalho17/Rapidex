package com.ucb.Rapidex.model.converter;

import com.ucb.Rapidex.model.StatusPagamento;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatusPagamentoConverter implements AttributeConverter<StatusPagamento, String> {

    @Override
    public String convertToDatabaseColumn(StatusPagamento attribute) {
        return attribute == null ? null : attribute.name().toLowerCase();
    }

    @Override
    public StatusPagamento convertToEntityAttribute(String dbData) {
        return dbData == null ? null : StatusPagamento.valueOf(dbData.toUpperCase());
    }
}
