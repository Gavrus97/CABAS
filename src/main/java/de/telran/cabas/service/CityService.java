package de.telran.cabas.service;

import de.telran.cabas.dto.request.CityRequestDTO;
import de.telran.cabas.dto.response.CityResponseDTO;

import java.util.List;

public interface CityService {

    CityResponseDTO create (CityRequestDTO cityRequestDTO);

    List<CityResponseDTO> getAll();

    CityResponseDTO getByName(String name);

    CityResponseDTO getById(Long id);
}
