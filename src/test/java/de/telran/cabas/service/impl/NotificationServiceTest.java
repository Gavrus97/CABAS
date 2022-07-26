package de.telran.cabas.service.impl;

import de.telran.cabas.entity.Area;
import de.telran.cabas.entity.Person;
import de.telran.cabas.entity.types.GenderType;
import de.telran.cabas.entity.types.LanguageType;
import de.telran.cabas.entity.types.SeverityType;
import de.telran.cabas.repository.NotificationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {

    @Mock
    private MessageSource messageSource;

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationServiceImpl service;

    @Test
    public void shouldReturnMessageForFemaleRus() {

        Person person = Person
                .builder()
                .firstName("Masha")
                .lastName("Pupkina")
                .gender(GenderType.FEMALE)
                .language(LanguageType.RUSSIAN)
                .build();

        Area area = Area
                .builder()
                .areaName("AB")
                .areaCode("AB")
                .severityType(SeverityType.RED)
                .build();

        String expectedMessage = String.format(
                "Уважаемая, %s %s. Ковидный статус в вашем городе Красный", person.getFirstName(), person.getLastName());

        Assertions.assertEquals(expectedMessage, service.getNotificationMessage(person,area));


    }
}
