package de.telran.cabas.service.impl;

import de.telran.cabas.dto.response.NotificationResponseDTO;
import de.telran.cabas.entity.Area;
import de.telran.cabas.entity.Person;
import de.telran.cabas.entity.types.SeverityType;
import de.telran.cabas.repository.AreaRepository;
import de.telran.cabas.repository.PersonRepository;
import de.telran.cabas.service.NotificationService;
import de.telran.cabas.service.PeopleToNotifyService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class PeopleToNotifyServiceImpl implements PeopleToNotifyService {

    private final NotificationService notificationService;
    private final AreaRepository areaRepository;
    private final PersonRepository personRepository;

    @Override
    public List<NotificationResponseDTO> getPeopleToNotify(String areaCode, SeverityType severityType) {
        Area area = updateArea(areaCode, severityType);

        List<Person> people = personRepository.findPeopleInArea(areaCode);

        return notificationService.notifyPeople(people, area);
    }

    private Area updateArea(String areaCode, SeverityType severityType) {
        var area = areaRepository.findByAreaCode(areaCode.toUpperCase())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("No area with areaCode {%s} found", areaCode)
                ));

        area.setSeverityType(severityType);
        return areaRepository.save(area);
    }
}
