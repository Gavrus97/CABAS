package de.telran.cabas.entity.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum LanguageType {

    ENGLISH(1, "en"),
    RUSSIAN(2, "ru"),
    DEUTSCH(3, "de");

    private final Integer languageId;
    private final String languageExternalId;

    public static LanguageType findLanguageById(Integer id) {
        if (id == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "languageId cannot be null"
            );
        }

        return Arrays.stream(LanguageType.values())
                .filter(type -> type.getLanguageId().equals(id))
                .findFirst()
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                String.format("No language with id {%s} found", id)
                        )
                );
    }

    @JsonCreator
    public static LanguageType findLanguageByExternalId(String externalId) {
        if (externalId == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "externalId cannot be null"
            );
        }

        return Arrays.stream(LanguageType.values())
                .filter(type -> type.getLanguageExternalId().equals(externalId))
                .findFirst()
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                String.format("No language with externalId {%s} found", externalId)
                        )
                );

    }
}
