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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AreaServiceImpl implements AreaService {

    // can be less new lines
    private final AreaRepository repository;
    private final CityRepository cityRepository;

    @Override
    @Transactional
    public AreaResponseDTO create(AreaRequestDTO areaRequestDto) {

        // a common approach - is to lowercase instead
        if (repository.existsByAreaName(areaRequestDto.getAreaName().toUpperCase())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    String.format("Area with name [%s] already exists ", areaRequestDto.getAreaName()));
        }

        // Converters - is a HUGE class, containing all conversions
        // it would be better to split it onto smaller pieces (CityConverter, AreaConverter, etc)
        var area = Converters.convertToAreaEntity(areaRequestDto);
        repository.save(area);

        return Converters.convertAreaToResponseDTO(area);
    }

    @Override
    public List<AreaWithCitiesResponseDTO> getAll() {
        var areas = repository.findAll();

        // Can't see any mentions of 404 in docs
        // This is a rare case, when list returns anything, but list ([], [1,2,3])
        // This does not apply to single entities (null, 404)
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
        // same here about lowercase
        var area = repository.findByAreaName(name.toUpperCase());

        if (area == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("There is no area with name [%s] ", name)
            );
        }
        return area;
    }


    private List<Long> getCityIdsByAreaId(Long id) {
        return cityRepository.findAllByAreaId(id)
                .stream()
                .map(City::getId)
                .collect(Collectors.toList());
    }
}
