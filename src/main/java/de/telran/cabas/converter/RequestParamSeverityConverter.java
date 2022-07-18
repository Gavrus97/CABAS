package de.telran.cabas.converter;

import de.telran.cabas.entity.types.SeverityType;
import org.springframework.core.convert.converter.Converter;

public class RequestParamSeverityConverter implements Converter<String, SeverityType> {

    @Override
    public SeverityType convert(String s) {
        return SeverityType.findByExternalTypeId(s);
    }
}
