package de.telran.cabas.repository;

import de.telran.cabas.entity.City;
import de.telran.cabas.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    List<Person> findAllByGuardianId(Long guardianId);

    boolean existsByFirstNameAndLastName(String firstName, String lastName);

    // see a blue circle from the left?
    // this means, you're overriding spring's default method
    //boolean existsById(Long id);

    List<Person> findAllByIdIsIn(List<Long> ids);

    Optional<Person> findByEmail(String email);

    List<Person> findAllByCityIsIn(List<City> cities);
}
