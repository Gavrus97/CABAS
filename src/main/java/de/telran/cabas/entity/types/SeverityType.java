package de.telran.cabas.entity.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum SeverityType {

    GREEN(1, "green"),
    YELLOW(2, "yellow"),
    ORANGE(3, "orange"),
    RED(4, "red");

    private final Integer typeId;
    private final String externalId;


    public static SeverityType findByTypeId(Integer typeId) {
        if(typeId == null){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "TypeId cannot be null"
            );
        }

        return Arrays.stream(SeverityType.values())
                .filter(type -> type.getTypeId().equals(typeId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                String.format("No SeverityType with typeId {%s} found", typeId)
                        )
                );
    }

    @JsonCreator
    public static SeverityType findByExternalTypeId(String externalId) {
        if(externalId == null){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "ExternalId cannot be null"
            );
        }
        return Arrays.stream(SeverityType.values())
                .filter(type -> type.getExternalId().equals(externalId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                String.format("No SeverityType with externalId {%s} found", externalId)
                        )
                );
    }
}
