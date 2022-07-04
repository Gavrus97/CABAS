package de.telran.cabas.service;

import de.telran.cabas.dto.request.PersonRequestDTO;
import de.telran.cabas.dto.request.UpdatePersonRequestDTO;
import de.telran.cabas.dto.response.PersonResponseDTO;

public interface PersonService {

    PersonResponseDTO create(PersonRequestDTO personDTO);

    void update (Long id, UpdatePersonRequestDTO personDTO);


}
