package de.telran.cabas.service.impl;

import de.telran.cabas.converter.Converters;
import de.telran.cabas.dto.request.AreaRequestDTO;
import de.telran.cabas.dto.response.AreaResponseDTO;
import de.telran.cabas.dto.response.AreaWithCitiesResponseDTO;
import de.telran.cabas.entity.Area;
import de.telran.cabas.entity.City;
import de.telran.cabas.repository.AreaRepository;
import de.telran.cabas.repository.CityRepository;
import de.telran.cabas.service.AreaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AreaServiceImpl implements AreaService {

    private final AreaRepository repository;
    private final CityRepository cityRepository;

    @Override
    @Transactional
    public AreaResponseDTO create(AreaRequestDTO areaRequestDto) {

        if (repository.existsByAreaName(areaRequestDto.getAreaName().toUpperCase())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    String.format("Area with name [%s] already exists ", areaRequestDto.getAreaName()));
        }

        var area = Converters.convertToAreaEntity(areaRequestDto);
        repository.save(area);

        return Converters.convertAreaToResponseDTO(area);
    }

    @Override
    public List<AreaWithCitiesResponseDTO> getAll() {
        var areas = repository.findAll();

        if (areas.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "There is no area in database yet"
            );
        }

        return areas
                .stream()
                .map(area -> {
                            var cityIds = getCityIdsByAreaId(area.getId());
                            return Converters.convertAreaToAreaWithCitiesDTO(area, cityIds);
                        }
                )
                .collect(Collectors.toList());
    }

    @Override
    public AreaWithCitiesResponseDTO getByName(String name) {
        var area = findAreaByNameOrThrow(name.toUpperCase());
        return Converters.convertAreaToAreaWithCitiesDTO(area, getCityIdsByAreaId(area.getId()));
    }

    private Area findAreaByNameOrThrow(String name) {
        return repository.findByAreaName(name.toUpperCase()).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("There is no area with name [%s] ", name)
                ));
    }


    private List<Long> getCityIdsByAreaId(Long areaId) {
        return cityRepository.findAllByAreaId(areaId)
                .stream()
                .map(City::getId)
                .collect(Collectors.toList());
    }
}
