package com.ucb.Rapidex.model.converter;

import com.ucb.Rapidex.model.StatusAgendamento;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatusAgendamentoConverter implements AttributeConverter<StatusAgendamento, String> {

    @Override
    public String convertToDatabaseColumn(StatusAgendamento attribute) {
        return attribute == null ? null : attribute.name().toLowerCase();
    }

    @Override
    public StatusAgendamento convertToEntityAttribute(String dbData) {
        return dbData == null ? null : StatusAgendamento.valueOf(dbData.toUpperCase());
    }
}
