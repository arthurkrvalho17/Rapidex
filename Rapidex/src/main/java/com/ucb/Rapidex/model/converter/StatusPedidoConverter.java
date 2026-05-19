package com.ucb.Rapidex.model.converter;

import com.ucb.Rapidex.model.StatusPedido;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatusPedidoConverter implements AttributeConverter<StatusPedido, String> {

    @Override
    public String convertToDatabaseColumn(StatusPedido attribute) {
        return attribute == null ? null : attribute.name().toLowerCase();
    }

    @Override
    public StatusPedido convertToEntityAttribute(String dbData) {
        return dbData == null ? null : StatusPedido.valueOf(dbData.toUpperCase());
    }
}
