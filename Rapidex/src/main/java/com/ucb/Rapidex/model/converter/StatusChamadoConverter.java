package com.ucb.Rapidex.model.converter;

import com.ucb.Rapidex.model.StatusChamado;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatusChamadoConverter implements AttributeConverter<StatusChamado, String> {
    @Override
    public String convertToDatabaseColumn(StatusChamado attribute) {
        return attribute == null ? null : attribute.name();
    }
    @Override
    public StatusChamado convertToEntityAttribute(String dbData) {
        return dbData == null ? null : StatusChamado.valueOf(dbData);
    }
}