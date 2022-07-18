package de.telran.cabas.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.List;

@Converter(autoApply = true)
public class ListConverter implements AttributeConverter<List<Long>, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @SneakyThrows
    public String convertToDatabaseColumn(List<Long> list) {
        return objectMapper.writeValueAsString(list);
    }

    @Override
    @SneakyThrows
    public List<Long> convertToEntityAttribute(String s) {
        return objectMapper.readValue(s, new TypeReference<List<Long>>() {});
    }
}
