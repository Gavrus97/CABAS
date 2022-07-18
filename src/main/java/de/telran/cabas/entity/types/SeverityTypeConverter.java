package de.telran.cabas.entity.types;


import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class SeverityTypeConverter implements AttributeConverter<SeverityType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(SeverityType severityType) {
        return severityType == null ? null : severityType.getTypeId() ;
    }

    @Override
    public SeverityType convertToEntityAttribute(Integer id) {
        return id == null ? null : SeverityType.findByTypeId(id);
    }
}
