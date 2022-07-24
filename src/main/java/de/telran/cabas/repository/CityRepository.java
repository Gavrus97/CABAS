package de.telran.cabas.repository;

import de.telran.cabas.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {

        List<City> findAllByAreaId(Long areaId);

        Boolean existsByCityName(String name);

        Optional<City> findByCityName(String cityName);

}
