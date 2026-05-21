package com.ucb.Rapidex.model.converter;

import com.ucb.Rapidex.model.PrioridadeChamado;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PrioridadeChamadoConverter implements AttributeConverter<PrioridadeChamado, String> {
    @Override
    public String convertToDatabaseColumn(PrioridadeChamado attribute) {
        return attribute == null ? null : attribute.name();
    }
    @Override
    public PrioridadeChamado convertToEntityAttribute(String dbData) {
        return dbData == null ? null : PrioridadeChamado.valueOf(dbData);
    }
}