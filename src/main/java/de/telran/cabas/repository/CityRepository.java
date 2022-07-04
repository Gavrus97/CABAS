package de.telran.cabas.repository;

import de.telran.cabas.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {

        List<City> findAllByAreaId(Long areaId);

        Boolean existsByCityName(String name);

        City findByCityName(String cityName);

}
