package de.telran.cabas.service.impl;

import de.telran.cabas.converter.NotificationConverter;
import de.telran.cabas.dto.response.NotificationResponseDTO;
import de.telran.cabas.entity.Area;
import de.telran.cabas.entity.Notification;
import de.telran.cabas.entity.Person;
import de.telran.cabas.repository.NotificationRepository;
import de.telran.cabas.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final MessageSource messageSource;
    private final NotificationRepository notificationRepository;


    @Override
    public List<NotificationResponseDTO> notifyPeople(List<Person> people, Area area) {
        if (people.isEmpty()) {
            return List.of();
        }

        saveNotification(people, area);

        return people
                .stream()
                .map(person -> NotificationConverter
                        .makeNotificationResponseDTO(getNotificationMessage(person, area)))
                .collect(Collectors.toList());
    }


    private String getNotificationMessage(Person person, Area area) {
        String statusSuffix = area.getSeverityType().getExternalId();
        String language = person.getLanguage().getLanguageExternalId();
        String gender = person.getGender().getExternalId();

        String statusMessage = messageSource
                .getMessage(
                        String.format("status.%s", statusSuffix),
                        null,
                        new Locale(language)
                );

        String greeting = messageSource.getMessage(
                String.format("gender.%s", gender),
                null,
                new Locale(language)
        );

        Object[] args = {greeting, person.getFirstName(), person.getLastName(), statusMessage};

        return messageSource.getMessage(
                "message.base",
                args,
                new Locale(language)
        );
    }

    private void saveNotification(List<Person> people, Area area) {
        Notification notification = Notification.builder()
                .area(area)
                .severityType(area.getSeverityType())
                .people(people.stream().map(Person::getId).collect(Collectors.toList()))
                .build();

        notificationRepository.save(notification);
    }


}
