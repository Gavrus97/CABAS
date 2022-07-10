package de.telran.cabas.service;

import de.telran.cabas.dto.request.ChangeCityRequestDTO;
import de.telran.cabas.dto.request.ChangeGuardianRequestDTO;
import de.telran.cabas.dto.request.PersonRequestDTO;
import de.telran.cabas.dto.request.UpdatePersonRequestDTO;
import de.telran.cabas.dto.response.PersonResponseDTO;

public interface PersonService {

    PersonResponseDTO create(PersonRequestDTO personDTO);

    void update (Long id, UpdatePersonRequestDTO personDTO);

    PersonResponseDTO changeGuardian(ChangeGuardianRequestDTO changeGuardianRequestDTO);

    PersonResponseDTO getPersonById(Long id);

    PersonResponseDTO getPersonByEmail(String email);

    PersonResponseDTO moveToAnotherCity(ChangeCityRequestDTO cityDTO);
}
