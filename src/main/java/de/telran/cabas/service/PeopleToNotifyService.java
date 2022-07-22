package de.telran.cabas.service;

import de.telran.cabas.dto.response.NotificationResponseDTO;
import de.telran.cabas.entity.Person;
import de.telran.cabas.entity.types.SeverityType;

import java.util.List;

public interface PeopleToNotifyService {

    List<NotificationResponseDTO> getPeopleToNotify(String areaCode, SeverityType severityType);
}
