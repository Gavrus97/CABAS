package de.telran.cabas.service.impl;

import de.telran.cabas.converter.Converters;
import de.telran.cabas.dto.request.CityRequestDTO;
import de.telran.cabas.dto.response.CityResponseDTO;
import de.telran.cabas.entity.Area;
import de.telran.cabas.entity.City;
import de.telran.cabas.repository.AreaRepository;
import de.telran.cabas.repository.CityRepository;
import de.telran.cabas.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityRepository repository;

    @Autowired
    private AreaRepository areaRepository;

    @Override
    public CityResponseDTO create(CityRequestDTO cityRequestDTO) {
        if (repository.existsByCityName(cityRequestDTO.getCityName().toUpperCase())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    String.format("The city [%s] already exists", cityRequestDTO.getCityName()));
        }

        var area = findAreaOrThrow(cityRequestDTO.getAreaId());
        var city = City.builder()
                .cityName(cityRequestDTO.getCityName().toUpperCase())
                .area(area)
                .build();
        repository.save(city);
        return Converters.convertCityToResponseDTO(city);

    }

    @Override
    public List<CityResponseDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(Converters::convertCityToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CityResponseDTO getByName(String name) {
        return Converters.convertCityToResponseDTO(findCityByNameOrThrow(name.toUpperCase()));
    }

    @Override
    public CityResponseDTO getById(Long id) {
        var city = repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("City with id [%s] doesn't exist! ", id)));
        return Converters.convertCityToResponseDTO(city);
    }

    private Area findAreaOrThrow(Long id) {
        return areaRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("There is no area with id [%s]", id))
        );
    }

    private City findCityByNameOrThrow(String name) {
        var city = repository.findByCityName(name.toUpperCase());

        if (city == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("No city [%s] found", name));
        }
        return city;
    }
}
