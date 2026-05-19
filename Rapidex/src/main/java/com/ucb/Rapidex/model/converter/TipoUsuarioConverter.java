package com.ucb.Rapidex.model.converter;

import com.ucb.Rapidex.model.TipoUsuario;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TipoUsuarioConverter implements AttributeConverter<TipoUsuario, String> {

    @Override
    public String convertToDatabaseColumn(TipoUsuario attribute) {
        return attribute == null ? null : attribute.name().toLowerCase();
    }

    @Override
    public TipoUsuario convertToEntityAttribute(String dbData) {
        return dbData == null ? null : TipoUsuario.valueOf(dbData.toUpperCase());
    }
}
