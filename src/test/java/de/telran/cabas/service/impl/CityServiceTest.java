package de.telran.cabas.service.impl;

import de.telran.cabas.dto.request.CityRequestDTO;
import de.telran.cabas.entity.Area;
import de.telran.cabas.entity.City;
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
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.util.Assert;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test of CityService class")
public class CityServiceTest {

    @Mock
    private CityRepository cityRepository;

    @Mock
    private AreaRepository areaRepository;

    @InjectMocks
    private CityServiceImpl service;

    @Nested
    @DisplayName("Test of create method")
    class CreateMethodTest {

        @Test
        @DisplayName("Should throw 409 exception if such city already exists")
        public void shouldThrow409WhenCityExists() {

            var request = CityRequestDTO
                    .builder()
                    .cityName("aaa")
                    .areaId(1L)
                    .build();

            var expectedStatus = HttpStatus.CONFLICT;
            var expectedErrorMessage = String.format(
                    "The city [%s] already exists", request.getCityName());

            Mockito
                    .when(cityRepository.existsByCityName(request.getCityName().toUpperCase()))
                    .thenReturn(true);

            var exception = Assertions.assertThrows(
                    ResponseStatusException.class,
                    () -> service.create(request)
            );

            Assertions.assertEquals(expectedStatus, exception.getStatus());
            Assertions.assertEquals(expectedErrorMessage, exception.getReason());
        }

        @Test
        @DisplayName("Should throw 404 exception if such an Area doesn't exist")
        public void shouldThrowExceptionIfNoArea() {

            var request = CityRequestDTO
                    .builder()
                    .cityName("aaa")
                    .areaId(1L)
                    .build();

            var expectedStatus = HttpStatus.NOT_FOUND;
            var expectedErrorMessage = String.format(
                    "There is no area with id [%s]", request.getAreaId());

            Mockito
                    .when(areaRepository.findById(request.getAreaId()))
                    .thenReturn(Optional.empty());

            var exception = Assertions.assertThrows(
                    ResponseStatusException.class,
                    () -> service.create(request)
            );

            Assertions.assertEquals(expectedStatus, exception.getStatus());
            Assertions.assertEquals(expectedErrorMessage, exception.getReason());
        }

        @Test
        @DisplayName("Should save a city")
        public void shouldSaveCity() {

            var request = CityRequestDTO
                    .builder()
                    .cityName("aaa")
                    .areaId(1L)
                    .build();

            var area = Area
                    .builder()
                    .areaName("AREA")
                    .areaCode("AR")
                    .id(request.getAreaId())
                    .build();

            var city = City
                    .builder()
                    .cityName(request.getCityName().toUpperCase())
                    .area(area)
                    .build();

            Mockito
                    .when(cityRepository.existsByCityName(request.getCityName().toUpperCase()))
                    .thenReturn(false);
            Mockito
                    .when(areaRepository.findById(request.getAreaId()))
                    .thenReturn(Optional.of(area));
            Mockito
                    .when(cityRepository.save(
                                    ArgumentMatchers.argThat(
                                            savedCity -> savedCity.getCityName().equals(city.getCityName())
                                                    && savedCity.getArea().getAreaName().equals(city.getArea().getAreaName())
                                                    && savedCity.getArea().getAreaCode().equals(city.getArea().getAreaCode())
                                    )
                            )
                    )
                    .thenReturn(city);

            service.create(request);
        }

    }

    @Nested
    @DisplayName("Test of getAll method")
    class GetAllMethodTest {

        @Test
        @DisplayName("Should return empty list if no cities found")
        public void shouldReturnEmptyList() {

            var expectedSize = 0;

            Mockito
                    .when(cityRepository.findAll())
                    .thenReturn(List.of());

            Assertions.assertEquals(expectedSize, service.getAll().size());
        }

        @Test
        @DisplayName("Should return list with cities when they exist")
        public void shouldReturnListWithCities() {

            var expectedSize = 3;

            var area = Area.builder().areaName("AREA").areaCode("AR").build();

            var city1 = City.builder().cityName("AAA").area(area).build();
            var city2 = City.builder().cityName("BBB").area(area).build();
            var city3 = City.builder().cityName("CCC").area(area).build();

            List<City> cities = List.of(city1, city2, city3);

            Mockito
                    .when(cityRepository.findAll())
                    .thenReturn(cities);

            Assertions.assertEquals(expectedSize, service.getAll().size());
        }
    }

    @Nested
    @DisplayName("Test of getByName method")
    class GetByNameTest {

        @Test
        @DisplayName("Should throw 404 NOT_FOUND if city doesn't exist")
        public void shouldThrow404IfNoCity() {

            String name = "aaa";

            var expectedStats = HttpStatus.NOT_FOUND;
            var expectedMessage = String.format("No city [%s] found", name);

            Mockito
                    .when(cityRepository.findByCityName(name.toUpperCase()))
                    .thenReturn(Optional.empty());
            var exception = Assertions.assertThrows(
                    ResponseStatusException.class,
                    () -> service.getByName(name));

            Assertions.assertEquals(expectedMessage, exception.getReason());
            Assertions.assertEquals(expectedStats, exception.getStatus());
        }

        @Test
        @DisplayName("Should return city")
        public void shouldReturnCity() {

            String name = "aaa";

            var city = City.builder().cityName(name.toUpperCase()).build();

            Mockito
                    .when(cityRepository.findByCityName(name.toUpperCase()))
                    .thenReturn(Optional.of(city));


            Assertions.assertEquals(city.getCityName(), service.getByName(name).getCityName());
        }
    }

    @Nested
    @DisplayName("Test of getByName method")
    class GetByIdTest {

        @Test
        @DisplayName("Should throw 404 when city not found")
        public void shouldThrowException() {

            Long id = 1L;

            var expectedStatus = HttpStatus.NOT_FOUND;
            var expectedMessage = String.format("City with id [%s] doesn't exist! ", id);

            Mockito
                    .when(cityRepository.findById(id))
                    .thenReturn(Optional.empty());

            var exception = Assertions.assertThrows(
                    ResponseStatusException.class,
                    () -> service.getById(id)
            );

            Assertions.assertEquals(expectedMessage, exception.getReason());
            Assertions.assertEquals(expectedStatus, exception.getStatus());
        }
    }

    @Test
    @DisplayName("Should return a city when it's found")
    public void shouldReturnCity() {

        Long id = 1L;

        City city = City.builder().cityName("AAA").id(id).build();

        Mockito
                .when(cityRepository.findById(id))
                .thenReturn(Optional.of(city));

        var result = service.getById(id);

        Assertions.assertEquals(city.getCityName(), result.getCityName());
        Assertions.assertEquals(id, result.getCityId());
    }


}
