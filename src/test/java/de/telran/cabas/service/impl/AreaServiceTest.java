package de.telran.cabas.service.impl;

import de.telran.cabas.dto.request.AreaRequestDTO;
import de.telran.cabas.dto.response.AreaResponseDTO;
import de.telran.cabas.entity.Area;
import de.telran.cabas.repository.AreaRepository;
import de.telran.cabas.repository.CityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AreaServiceTest {

    @Mock
    private AreaRepository areaRepository;

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private AreaServiceImpl service;

    @Nested
    class CreateMethodTest {

        @Test
        @DisplayName("should throw exception when such area exists")
        public void shouldThrowExceptionWhenAreaExist() {
            AreaRequestDTO request = AreaRequestDTO
                    .builder()
                    .areaName("Abc")
                    .areaCode("ab")
                    .build();

            Mockito
                    .when(areaRepository.existsByAreaName(request.getAreaName().toUpperCase()))
                    .thenReturn(true);

            Mockito
                    .verify(areaRepository,
                            Mockito.never())
                    .save(ArgumentMatchers.any());


            String expectedMessage = String.format(
                    "Area with name [%s] already exists ", request.getAreaName()
            );

            var exception = Assertions.assertThrows(
                    ResponseStatusException.class,
                    () -> service.create(request)
            );

            Assertions.assertEquals(expectedMessage, exception.getReason());

        }

        @Test
        @DisplayName("should save an area and return dto when such area doesn't exist")
        public void shouldSaveArea() {
            AreaRequestDTO request = AreaRequestDTO
                    .builder()
                    .areaName("Abc")
                    .areaCode("ab")
                    .build();

            Area area = Area
                    .builder()
                    .areaName(request.getAreaName().toUpperCase())
                    .areaCode(request.getAreaCode().toUpperCase())
                    .build();

            Mockito
                    .when(areaRepository.existsByAreaName(request.getAreaName().toUpperCase()))
                    .thenReturn(false);

            Mockito
                    .when(areaRepository.save(
                            ArgumentMatchers.argThat(savedArea ->
                                    savedArea.getAreaName().equals(area.getAreaName()) &&
                                            savedArea.getAreaCode().equals(area.getAreaCode()))
                    ))
                    .thenReturn(area);

            service.create(request);
        }


    }

    @Nested
    class GetAllMethodTest {

        @Test
        @DisplayName("should throw exception when list is empty")
        public void shouldThrowExceptionWhenListEmpty() {

            Mockito
                    .when(areaRepository.findAll())
                    .thenReturn(List.of());

            String expectedMessage = "There is no area in database yet";
            HttpStatus expectedStatus = HttpStatus.NOT_FOUND;

            var exception = Assertions.assertThrows(
                    ResponseStatusException.class,
                    () -> service.getAll()
            );

            Assertions.assertEquals(expectedMessage, exception.getReason());
            Assertions.assertEquals(expectedStatus, exception.getStatus());
        }

        @Test
        @DisplayName("should return list with areas")
        public void shouldReturnListOfArea() {

            var area1 = Area.builder().areaName("ABC").areaCode("AB").build();
            var area2 = Area.builder().areaName("BBB").areaCode("BB").build();
            var area3 = Area.builder().areaName("CCC").areaCode("CC").build();


            Mockito
                    .when(areaRepository.findAll())
                    .thenReturn(List.of(area1, area2, area3));

            int expectedSize = 3;
            Assertions.assertEquals(expectedSize, service.getAll().size());
        }

    }

    @Nested
    class GetByNameMethodTest {

        @Test
        @DisplayName("should throw exception when area was not found")
        public void shouldThrowException() {
            String name = "aaa";

            Mockito
                    .when(areaRepository.findByAreaName(name.toUpperCase()))
                    .thenReturn(Optional.empty());

            String expectedMessage = String.format("There is no area with name [%s] ", name);
            HttpStatus expectedStatus = HttpStatus.NOT_FOUND;

            var exception = Assertions.assertThrows(
                    ResponseStatusException.class,
                    () -> service.getByName(name)
            );

            Assertions.assertEquals(expectedMessage, exception.getReason());
            Assertions.assertEquals(expectedStatus, exception.getStatus());
        }

        @Test
        @DisplayName("should return Area")
        public void shouldReturnArea() {
            String name = "aaa";
            String areaCode = "aa";
            var area = Area.builder().areaName(name.toUpperCase()).areaCode(areaCode.toUpperCase()).build();

            AreaResponseDTO dto = AreaResponseDTO
                    .builder()
                    .areaName(name.toUpperCase())
                    .areaCode(areaCode.toUpperCase())
                    .build();

            Mockito
                    .when(areaRepository.findByAreaName(name.toUpperCase()))
                    .thenReturn(Optional.of(area));

            Assertions.assertEquals(dto.getAreaName(), service.getByName(name).getAreaName());
            Assertions.assertEquals(dto.getAreaCode(), service.getByName(name).getAreaCode());
        }
    }

}
