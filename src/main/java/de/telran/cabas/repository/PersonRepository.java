package de.telran.cabas.repository;

import de.telran.cabas.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    List<Person> findAllByGuardianId(Long guardianId);

    boolean existsByFirstNameAndLastName(String firstName, String lastName);

    boolean existsById(Long id);

    List<Person> findAllByIdIsIn(List<Long> ids);

    Person findByEmail(String email);
}
