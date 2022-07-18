package de.telran.cabas.service.impl;

import de.telran.cabas.converter.NotificationConverter;
import de.telran.cabas.dto.response.NotificationResponseDTO;
import de.telran.cabas.entity.Area;
import de.telran.cabas.entity.City;
import de.telran.cabas.entity.Notification;
import de.telran.cabas.entity.Person;
import de.telran.cabas.entity.types.SeverityType;
import de.telran.cabas.repository.AreaRepository;
import de.telran.cabas.repository.CityRepository;
import de.telran.cabas.repository.NotificationRepository;
import de.telran.cabas.repository.PersonRepository;
import de.telran.cabas.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final AreaRepository areaRepository;
    private final CityRepository cityRepository;
    private final PersonRepository personRepository;
    private final NotificationRepository notificationRepository;

    @Override
    public List<NotificationResponseDTO> notifyPeople(String areaCode, SeverityType severityType) {

        Area area = areaRepository.findByAreaCode(areaCode.toUpperCase())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("No area with areaCode {%s} found", areaCode)
                ));
        area.setSeverityType(severityType);

        areaRepository.save(area);

        List<City> cities = cityRepository.findAllByAreaId(area.getId());

        if(cities.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("There are no cities in area with areaCode {%s}", areaCode)
            );
        }

        List<Person> people = personRepository.findAllByCityIsIn(cities);

        Notification notification = Notification
                .builder()
                .areaId(area.getId())
                .severityType(severityType)
                .people(people.stream().map(Person::getId).collect(Collectors.toList()))
                .build();

        notificationRepository.save(notification);

        return people
                .stream()
                .map(NotificationConverter::convertPersonIntoNotificationResponseDTO)
                .collect(Collectors.toList());
    }
}
