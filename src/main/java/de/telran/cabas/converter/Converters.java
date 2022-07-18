package de.telran.cabas.converter;

import de.telran.cabas.dto.request.AreaRequestDTO;
import de.telran.cabas.dto.response.*;
import de.telran.cabas.entity.Area;
import de.telran.cabas.entity.City;
import de.telran.cabas.entity.Person;
import de.telran.cabas.entity.types.SeverityType;

import java.util.List;
import java.util.stream.Collectors;

public class Converters {

    public static Area convertToAreaEntity(AreaRequestDTO areaRequestDto){
        return Area
                .builder()
                .areaName(areaRequestDto.getAreaName().toUpperCase())
                .areaCode(areaRequestDto.getAreaCode().toUpperCase())
                .severityType(SeverityType.GREEN)
                .build();
    }

    public static AreaResponseDTO convertAreaToResponseDTO(Area area){
        return AreaResponseDTO
                .builder()
                .areaName(area.getAreaName())
                .areaCode(area.getAreaCode())
                .id(area.getId())
                .severityType(area.getSeverityType())
                .build();
    }

    public static AreaWithCitiesResponseDTO convertAreaToAreaWithCitiesDTO(Area area, List<Long> cityIds) {
        return AreaWithCitiesResponseDTO
                .builder()
                .areaName(area.getAreaName())
                .areaId(area.getId())
                .areaCode(area.getAreaCode())
                .severityType(area.getSeverityType())
                .cityIds(cityIds)
                .build();
    }

    public static CityResponseDTO convertCityToResponseDTO(City city){
        return CityResponseDTO.builder()
                .cityId(city.getId())
                .cityName(city.getCityName())
                .areaId(city.getArea().getId())
                .severityType(city.getArea().getSeverityType())
                .build();

    }

    public static PersonResponseDTO convertPersonIntoResponseDTO(Person person, Long areaId,
                                                                 Person guardian, List<Person> children,
                                                                 Long cityId){
        return PersonResponseDTO
                .builder()
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .email(person.getEmail())
                .phoneNumber(person.getPhoneNumber())
                .guardian(convertPersonToGuardianDTO(guardian))
                .children(convertListPersonIntoListChildDTO(children))
                .cityId(cityId)
                .areaId(areaId)
                .build();
    }

    public static GuardianResponseDTO convertPersonToGuardianDTO(Person person){
        if(person == null){
            return null;
        }
        return GuardianResponseDTO
                .builder()
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .email(person.getEmail())
                .phoneNumber(person.getPhoneNumber())
                .build();
    }

    public static ChildResponseDTO convertPersonIntoChildResponseDTO(Person person){

        return ChildResponseDTO
                .builder()
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .email(person.getEmail())
                .phoneNumber(person.getPhoneNumber())
                .build();
    }

    public static List<ChildResponseDTO> convertListPersonIntoListChildDTO(List<Person> people){
        return people
                .stream()
                .map(Converters::convertPersonIntoChildResponseDTO)
                .collect(Collectors.toList());
    }
}
