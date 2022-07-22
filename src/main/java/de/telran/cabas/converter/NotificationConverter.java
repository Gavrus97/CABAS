package de.telran.cabas.converter;

import de.telran.cabas.dto.response.NotificationResponseDTO;

public class NotificationConverter {

    public static NotificationResponseDTO makeNotificationResponseDTO(String s){
        return NotificationResponseDTO
                .builder()
                .notification(s)
                .build();
    }
}
