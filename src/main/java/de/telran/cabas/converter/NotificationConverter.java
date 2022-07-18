package de.telran.cabas.converter;

import de.telran.cabas.dto.response.NotificationResponseDTO;
import de.telran.cabas.entity.Person;

public class NotificationConverter {

    public static NotificationResponseDTO convertPersonIntoNotificationResponseDTO(Person person){
        return NotificationResponseDTO
                .builder()
                .personsId(person.getId())
                .personsPhoneNumber(person.getPhoneNumber())
                .personsEmail(person.getEmail())
                .build();
    }
}
