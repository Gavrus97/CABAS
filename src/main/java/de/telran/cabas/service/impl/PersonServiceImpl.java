package de.telran.cabas.service.impl;

import de.telran.cabas.converter.Converters;
import de.telran.cabas.dto.request.ChangeGuardianRequestDTO;
import de.telran.cabas.dto.request.PersonRequestDTO;
import de.telran.cabas.dto.request.UpdatePersonRequestDTO;
import de.telran.cabas.dto.response.PersonResponseDTO;
import de.telran.cabas.entity.City;
import de.telran.cabas.entity.Person;
import de.telran.cabas.repository.CityRepository;
import de.telran.cabas.repository.PersonRepository;
import de.telran.cabas.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.core.RepositoryCreationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.databind.type.LogicalType.Collection;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository repository;

    @Autowired
    private CityRepository cityRepository;

    @Override
    @Transactional
    public PersonResponseDTO create(PersonRequestDTO personDTO) {
        checkEmail(personDTO.getEmail());
        checkPhoneNumber(personDTO.getPhoneNumber());
        checkName(personDTO.getFirstName(), personDTO.getLastName());
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

        return Converters.convertPersonIntoResponseDTO(person, areaId, guardian, children, cityId);
    }

    @Override
    @Transactional
    public void update(Long id, UpdatePersonRequestDTO personDTO) {
        var person = getPersonByIdOrThrow(id);

        if (person == null) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Id must not be null! ");
        }

        if (!person.getEmail().equals(personDTO.getEmail())) {
            checkEmail(personDTO.getEmail());
            person.setEmail(personDTO.getEmail());
        }
        if (!person.getPhoneNumber().equals(personDTO.getPhoneNumber())) {
            checkPhoneNumber(personDTO.getPhoneNumber());
            person.setPhoneNumber(personDTO.getPhoneNumber());
        }
        if (!person.getFirstName().equals(personDTO.getFirstName()) ||
                !person.getLastName().equals(personDTO.getLastName())) {

            checkName(personDTO.getFirstName(), personDTO.getLastName());
            person.setFirstName(personDTO.getFirstName());
            person.setLastName(personDTO.getLastName());
        }


        repository.save(person);
    }

    @Override
    public PersonResponseDTO changeGuardian(Long guardianId, ChangeGuardianRequestDTO changeGuardianRequestDTO) {
        checkIfExistsById(guardianId);
        var existingChildren = getChildren(guardianId);
        var dtoChildren = changeGuardianRequestDTO.getChildrenIds();
        var toGuardian = getPersonByIdOrThrow(changeGuardianRequestDTO.getToGuardian());

        checkIfCanBeGuardian(toGuardian);
        checkIfChildrenMatch(guardianId, existingChildren, dtoChildren);

        var childrenToMove = repository.findAllByIdIsIn(dtoChildren);

        childrenToMove
                .forEach(person -> {
                    person.setGuardianId(changeGuardianRequestDTO.getToGuardian());
                    person.setCity(toGuardian.getCity());
                });

        repository.saveAll(childrenToMove);
        var areaId = getAreaId(toGuardian.getCity());
        var allChildren = getChildren(toGuardian.getId());
        var cityId = getCityId(toGuardian);

        return Converters.convertPersonIntoResponseDTO(toGuardian, areaId, null, allChildren, cityId);
    }

    private void checkIfExistsById(Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "You are looking for person with id null. ");
        }

        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("There is no person with id [%s]", id));
        }
    }

    private Long getCityId(Person person) {
        return person.getCity() == null ? null : person.getCity().getId();
    }

    private void checkName(String firstName, String lastName) {

//        if(firstName == null || lastName == null){
//            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
//                    "Name or last name cannot be null");
//        }

        if (repository.existsByFirstNameAndLastName(firstName, lastName)) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    String.format("Person with name [%s] already exists", firstName + " " + lastName));
        }
    }

    private void checkIfChildrenMatch(Long guardianId,
                                      List<Person> existingChildren,
                                      List<Long> childrenIdsToCheck) {

        if (childrenIdsToCheck.isEmpty() || childrenIdsToCheck == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "People that may be moved are not present! ");
        }

        if (existingChildren.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    String.format("The person with id [%s] has no children! ", guardianId));
        }

        List<Long> existingIds = new ArrayList<>(existingChildren)
                .stream()
                .map(Person::getId)
                .collect(Collectors.toList());

        List<Long> temp = new ArrayList<>(childrenIdsToCheck)
                .stream()
                .filter(x -> !existingIds.contains(x))
                .collect(Collectors.toList());

        if (!temp.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    String.format("People with id's %s dont belong to person with id ", temp) +
                            String.format("[%s]", guardianId));
        }


    }

    private void checkEmail(String email) {
        if (repository.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    String.format("Person with email [%s] already exists", email));
        }
    }

    private void checkPhoneNumber(String phoneNumber) {
        if (repository.existsByPhoneNumber(phoneNumber)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    String.format("Person with phone number [%s] already exists", phoneNumber));
        }
    }

    private Person getPersonByIdOrThrow(Long id) {
        if (id == null) {
            return null;
        }

        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("No person with id [%s] found", id)));
    }

    private void checkIfCanBeGuardian(Person person) {

        if (person == null) {
            return;
        }

        if (ChronoUnit.YEARS.between(person.getDateOfBirth(), LocalDate.now()) < 18) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    String.format("The person with id [%s] cannot be a guardian because of age ", person.getId()));
        }

        if (person.getGuardianId() != null) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    String.format("The person with id [%s] cannot be a guardian because it also has a guardian ",
                            person.getId()));
        }
    }

    private List<Person> getChildren(Long id) {
        return repository.findAllByGuardianId(id);
    }

    private Long getAreaId(City city) {
        if (city == null) {
            return null;
        }
        return city.getArea().getId();
    }


}
