package de.telran.cabas.controller;

import de.telran.cabas.dto.request.PersonRequestDTO;
import de.telran.cabas.dto.response.PersonResponseDTO;
import de.telran.cabas.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PersonController {

    @Autowired
    private PersonService service;

    @PostMapping("/people")
    public PersonResponseDTO create(@RequestBody PersonRequestDTO personDTO){
        return service.create(personDTO);
    }
}
