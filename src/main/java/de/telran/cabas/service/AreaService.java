package de.telran.cabas.service;

import de.telran.cabas.dto.request.AreaRequestDTO;
import de.telran.cabas.dto.response.AreaResponseDTO;
import de.telran.cabas.dto.response.AreaWithCitiesResponseDTO;

import java.util.List;

public interface AreaService {

    AreaResponseDTO create(AreaRequestDTO areaRequestDto);

    List<AreaWithCitiesResponseDTO> getAll();

    AreaWithCitiesResponseDTO getByName(String name);
}
