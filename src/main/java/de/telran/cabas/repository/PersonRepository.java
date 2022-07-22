package de.telran.cabas.repository;

import de.telran.cabas.entity.City;
import de.telran.cabas.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    List<Person> findAllByGuardianId(Long guardianId);

    boolean existsByFirstNameAndLastName(String firstName, String lastName);

    List<Person> findAllByIdIsIn(List<Long> ids);

    Optional<Person> findByEmail(String email);

    List<Person> findAllByCityIsIn(List<City> cities);

    @Query("from Person p where p.city.area.areaCode = :areaCode")
    List<Person> findPeopleInArea(@Param("areaCode") String areaCode);
}
