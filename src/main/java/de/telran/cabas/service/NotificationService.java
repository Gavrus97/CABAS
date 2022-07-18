package de.telran.cabas.service;

import de.telran.cabas.dto.response.NotificationResponseDTO;
import de.telran.cabas.entity.types.SeverityType;

import java.util.List;

public interface NotificationService {

    List<NotificationResponseDTO> notifyPeople(String areaCode, SeverityType severityType);
}
