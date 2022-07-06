package de.telran.cabas.controller;

import de.telran.cabas.dto.request.ChangeCityRequestDTO;
import de.telran.cabas.dto.request.ChangeGuardianRequestDTO;
import de.telran.cabas.dto.request.PersonRequestDTO;
import de.telran.cabas.dto.request.UpdatePersonRequestDTO;
import de.telran.cabas.dto.response.PersonResponseDTO;
import de.telran.cabas.dto.response.ResponseExceptionDTO;
import de.telran.cabas.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PersonController {

    @Autowired
    private PersonService service;

    @PostMapping("/people")
    public PersonResponseDTO create(@RequestBody PersonRequestDTO personDTO){
        return service.create(personDTO);
    }

    @PutMapping("/people/{id}")
    public void update(@PathVariable("id") Long id,
                       @RequestBody UpdatePersonRequestDTO personDTO){
        service.update(id, personDTO);
    }

    @PatchMapping("/people/guardians/{fromGuardianId}")
    public PersonResponseDTO changeGuardian(@PathVariable("fromGuardianId") Long guardianId,
                                            @RequestBody ChangeGuardianRequestDTO changeGuardianRequestDTO){
        return service.changeGuardian(guardianId, changeGuardianRequestDTO);
    }

    @GetMapping("/people/{id}")
    public PersonResponseDTO getById(@PathVariable("id") Long id){
        return service.getPersonById(id);
    }

    @GetMapping("/people")
    public PersonResponseDTO getByEmail(@RequestParam("email") String email){
        return service.getPersonByEmail(email);
    }

    @PostMapping("/people/move")
    public PersonResponseDTO moveToAnotherCity(@RequestBody ChangeCityRequestDTO changeDTO){
        return service.moveToAnotherCity(changeDTO);
    }
}
