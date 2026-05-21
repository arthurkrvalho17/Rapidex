package com.ucb.Rapidex.model.converter;

import com.ucb.Rapidex.model.TipoEvento;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TipoEventoConverter implements AttributeConverter<TipoEvento, String> {
    @Override
    public String convertToDatabaseColumn(TipoEvento attribute) {
        return attribute == null ? null : attribute.name();
    }
    @Override
    public TipoEvento convertToEntityAttribute(String dbData) {
        return dbData == null ? null : TipoEvento.valueOf(dbData);
    }
}