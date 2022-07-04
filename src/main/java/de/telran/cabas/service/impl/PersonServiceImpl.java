package de.telran.cabas.service.impl;

import de.telran.cabas.converter.Converters;
import de.telran.cabas.dto.request.PersonRequestDTO;
import de.telran.cabas.dto.request.UpdatePersonRequestDTO;
import de.telran.cabas.dto.response.PersonResponseDTO;
import de.telran.cabas.entity.City;
import de.telran.cabas.entity.Person;
import de.telran.cabas.repository.CityRepository;
import de.telran.cabas.repository.PersonRepository;
import de.telran.cabas.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository repository;

    @Autowired
    private CityRepository cityRepository;

    @Override
    public PersonResponseDTO create(PersonRequestDTO personDTO) {
        checkEmail(personDTO.getEmail());
        checkPhoneNumber(personDTO.getPhoneNumber());
        var guardian = getPersonByIdOrThrow(personDTO.getGuardianId());

        checkIfCanBeGuardian(guardian);

        Long guardianId = guardian == null ? null : guardian.getId();

        Person person =
                Person
                        .builder()
                        .firstName(personDTO.getFirstName())
                        .lastName(personDTO.getLastName())
                        .dateOfBirth(personDTO.getDateOfBirth())
                        .email(personDTO.getEmail())
                        .phoneNumber(personDTO.getPhoneNumber())
                        .guardianId(guardianId)
                        .city(null)
                        .build();

        repository.save(person);

        var children = getChildren(person.getId());
        Long areaId = getAreaId(person.getCity());
        Long cityId = null;

        return Converters.convertPersonIntoResponseDTO(person,areaId,guardian,children, cityId);
    }

    @Override
    public void update(Long id, UpdatePersonRequestDTO personDTO) {

    }



    private void checkEmail(String email) {
        if (repository.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    String.format("Person with email [%s] already exists", email));
        }
    }

    private void checkPhoneNumber(String phoneNumber) {
        if(repository.existsByPhoneNumber(phoneNumber)){
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    String.format("Person with phone number [%s] already exists", phoneNumber));
        }
    }

    private Person getPersonByIdOrThrow(Long id){
        if(id == null){
            return null;
        }

        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("No person with id [%s] found", id)));
    }

    private void checkIfCanBeGuardian(Person person){

        if(person == null){
            return;
        }

        if(ChronoUnit.YEARS.between(person.getDateOfBirth(), LocalDate.now()) < 18 ){
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    String.format("The person with id [%s] cannot be a guardian because of age ", person.getId()));
        }

        if(person.getGuardianId() != null){
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    String.format("The person with id [%s] cannot be a guardian because it also has a guardian ",
                            person.getId()));
        }
    }

    private List<Person> getChildren(Long id){
        return repository.findAllByGuardianId(id);
    }

    private Long getAreaId(City city){
        if(city == null){
            return null;
        }
        return city.getArea().getId();
    }


}
