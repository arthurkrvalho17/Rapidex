package com.ucb.Rapidex.model.converter;

import com.ucb.Rapidex.model.AreaAtuacao;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class AreaAtuacaoConverter implements AttributeConverter<AreaAtuacao, String> {

    @Override
    public String convertToDatabaseColumn(AreaAtuacao attribute) {
        return attribute == null ? null : attribute.getDbValue();
    }

    @Override
    public AreaAtuacao convertToEntityAttribute(String dbData) {
        return dbData == null ? null : AreaAtuacao.fromDbValue(dbData);
    }
}
