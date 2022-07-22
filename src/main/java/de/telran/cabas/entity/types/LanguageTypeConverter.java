package de.telran.cabas.entity.types;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LanguageTypeConverter implements AttributeConverter<LanguageType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(LanguageType languageType) {
        return languageType == null ? null : languageType.getLanguageId();
    }

    @Override
    public LanguageType convertToEntityAttribute(Integer id) {
        return id == null ? null : LanguageType.findLanguageById(id);
    }
}
