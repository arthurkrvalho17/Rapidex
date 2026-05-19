package com.ucb.Rapidex.model.converter;

import com.ucb.Rapidex.model.StatusPrestador;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatusPrestadorConverter implements AttributeConverter<StatusPrestador, String> {

    @Override
    public String convertToDatabaseColumn(StatusPrestador attribute) {
        return attribute == null ? null : attribute.name().toLowerCase();
    }

    @Override
    public StatusPrestador convertToEntityAttribute(String dbData) {
        return dbData == null ? null : StatusPrestador.valueOf(dbData.toUpperCase());
    }
}
