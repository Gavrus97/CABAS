package de.telran.cabas.entity.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum GenderType {

    MALE(1, "m"),
    FEMALE(2, "f");

    private final Integer id;
    private final String externalId;

    public static GenderType findGenderById(Integer id) {
        if (id == null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "GenderType id cannot be null"
            );
        }

        return Arrays.stream(GenderType.values())
                .filter(type -> type.getId().equals(id))
                .findFirst()
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                String.format("No GenderType with id {%s} found", id)
                        )
                );
    }

    @JsonCreator
    public static GenderType findGenderByExternalId(String externalId){
        if(externalId== null){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "GenderType external id cannot be null"
            );
        }

        return Arrays.stream(GenderType.values())
                .filter(genderType -> genderType.getExternalId().equals(externalId))
                .findFirst()
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                String.format("No GenderType with externalId {%s} found", externalId)
                        )
                );
    }


}
