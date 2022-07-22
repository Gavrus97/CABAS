package de.telran.cabas.entity.types;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class GenderTypeConverter implements AttributeConverter<GenderType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(GenderType genderType) {
        return genderType == null ? null : genderType.getId();
    }

    @Override
    public GenderType convertToEntityAttribute(Integer id) {
        return id == null ? null : GenderType.findGenderById(id);
    }
}
